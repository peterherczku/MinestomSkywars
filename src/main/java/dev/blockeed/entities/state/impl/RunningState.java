package dev.blockeed.entities.state.impl;

import dev.blockeed.entities.state.GameState;
import dev.blockeed.managers.ChestManager;
import dev.blockeed.managers.GameManager;
import dev.blockeed.managers.PlayerManager;
import io.github.togar2.pvp.feature.CombatFeatureSet;
import io.github.togar2.pvp.feature.CombatFeatures;
import io.github.togar2.pvp.utils.CombatVersion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.*;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.time.Duration;

public class RunningState extends GameState {
    public RunningState() {
        super(600);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ChestManager.setupChests();
        GameManager.getPlayers().forEach(player -> {
            Component title = Component.text("SkyWars").color(TextColor.fromHexString("#7ed6df")).decorate(TextDecoration.BOLD);
            Component subTitle = Component.text("Kill the enemies").color(TextColor.fromHexString("#95afc0"));
            player.sendTitlePart(TitlePart.TITLE, title);
            player.sendTitlePart(TitlePart.SUBTITLE, subTitle);
        });
    }

    @Override
    public void onEnd() {
        GameManager.setGameState(new EndingState(null));
    }

    @Override
    public EventNode<Event> events() {
        EventNode<Event> node = EventNode.all("running-state");
        CombatFeatureSet featureSet = CombatFeatures.empty()
                .version(CombatVersion.LEGACY)
                .add(CombatFeatures.VANILLA_FALL)
                .add(CombatFeatures.VANILLA_PLAYER_STATE)
                .add(CombatFeatures.VANILLA_ATTACK)
                .add(CombatFeatures.VANILLA_KNOCKBACK)
                .add(CombatFeatures.VANILLA_ARMOR)
                .add(CombatFeatures.VANILLA_CRITICAL)
                .add(CombatFeatures.VANILLA_DAMAGE)
                .add(CombatFeatures.VANILLA_ITEM_DAMAGE)
                .add(CombatFeatures.VANILLA_ENCHANTMENT)
                .build();
        node.addChild(featureSet.createNode());
        node.addListener(PlayerBlockBreakEvent.class, event -> {
            if (PlayerManager.isSpectator(event.getPlayer())) {
                event.setCancelled(true);
                return;
            }
            var material = event.getBlock().registry().material();
            if (material != null) {
                ItemStack itemStack = ItemStack.builder(material).build();
                ItemEntity item = new ItemEntity(itemStack);
                item.setInstance(event.getInstance(), event.getBlockPosition().add(0.5, 0.5, 0.5));
                item.setPickupDelay(Duration.ofMillis(500));
                item.setInvisible(false);
            }
        });
        node.addListener(PlayerStopFlyingEvent.class, event -> {
            if (PlayerManager.isSpectator(event.getPlayer())) {
                event.getPlayer().setFlying(true);
            }
        });
        node.addListener(PlayerDisconnectEvent.class, event -> {
           Player player = event.getPlayer();
           if (PlayerManager.isSpectator(player)) return;
           PlayerManager.killPlayer(player, null);
        });
        node.addListener(PlayerBlockPlaceEvent.class, event -> {
            if (PlayerManager.isSpectator(event.getPlayer())) {
                event.setCancelled(true);
                return;
            }
        });
        node.addListener(PlayerBlockInteractEvent.class, event -> {
            var material = event.getBlock().registry().material();
            if (material != Material.CHEST) return;
            Vec chestPos = event.getBlockPosition().asVec();
            if (!ChestManager.isChest(chestPos)) {
                System.out.println(chestPos.toString());
                System.out.println("This chest is not registered");
                return;
            }
            Inventory inventory = ChestManager.getChest(chestPos);
            event.getPlayer().openInventory(inventory);
        });
        node.addListener(ItemDropEvent.class, event -> {
            if (PlayerManager.isSpectator(event.getPlayer())) {
                event.setCancelled(true);
                return;
            }
            ItemEntity itemEntity = new ItemEntity(event.getItemStack());
            itemEntity.setInstance(event.getInstance(), event.getPlayer().getPosition());
            itemEntity.setVelocity(event.getPlayer().getPosition().direction().mul(6));
            itemEntity.setPickupDelay(Duration.ofMillis(500));
            itemEntity.setInvisible(false);
        });
        node.addListener(PickupItemEvent.class, event -> {
            if (event.getEntity() instanceof Player player) {
                if (PlayerManager.isSpectator(player)) {
                    event.setCancelled(true);
                    return;
                }
                player.getInventory().addItemStack(event.getItemStack());
            }
        });
        node.addListener(EntityDamageEvent.class, event -> {
            if ((!(event.getEntity() instanceof Player player))) return;
            if (!PlayerManager.isSpectator(player)) {
                if (player.getHealth() - event.getDamage().getAmount() > 0) return;
                event.setCancelled(true);
                PlayerManager.killPlayer(player, event.getDamage());
                return;
            }
            event.setCancelled(true);
        });
        node.addListener(PlayerMoveEvent.class, event -> {
            Player player = event.getPlayer();
            if (player.getPosition().y() < 0 && !PlayerManager.isSpectator(player)) {
                PlayerManager.killPlayer(player, null);
            }
        });
        return node;
    }
}
