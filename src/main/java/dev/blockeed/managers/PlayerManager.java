package dev.blockeed.managers;

import dev.blockeed.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    private static List<Player> spectators = new ArrayList<>();
    private static Map<Player, Integer> kills = new HashMap<>();

    public static void killPlayer(Player player, Damage damage) {
        setSpectator(player);
        Component text;
        if (damage != null) {
            if (damage.getType() == DamageType.PLAYER_ATTACK && damage.getAttacker() != null && damage.getAttacker() instanceof Player damager) {
                text = Constants.PREFIX.append(
                        Component
                                .text(player.getUsername())
                                .color(TextColor.fromHexString("#95afc0"))
                                .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                                .append(Component
                                        .text(" has been killed by ")
                                        .color(TextColor.fromHexString("#dff9fb"))
                                        .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                                ).append(Component
                                        .text(damager.getUsername())
                                        .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                                        .color(TextColor.fromHexString("#95afc0"))
                                )
                );
                PlayerManager.incrementKill(damager);
            } else {
                text = Constants.PREFIX.append(
                        Component
                                .text(player.getUsername())
                                .color(TextColor.fromHexString("#95afc0"))
                                .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                                .append(Component
                                        .text(" has died.")
                                        .color(TextColor.fromHexString("#dff9fb"))
                                        .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                                )
                );
            }
        } else {
            text = Constants.PREFIX.append(
                    Component
                            .text(player.getUsername())
                            .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                            .color(TextColor.fromHexString("#95afc0"))
                            .append(Component
                                    .text(" has died.")
                                    .color(TextColor.fromHexString("#dff9fb"))
                                    .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                            )
            );
        }

        GameManager.getPlayers().forEach(otherPlayer -> {
            otherPlayer.sendMessage(text);
        });
        Component title = Component.text("You died").color(TextColor.fromHexString("#22a6b3"));
        Component subtitle = Component.text("You are a spectator from now on!").color(TextColor.fromHexString("#dff9fb"));
        player.sendTitlePart(TitlePart.TITLE, title);
        player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
        player.sendMessage(Constants.PREFIX.append(
                Component.text("You died! ").decorate(TextDecoration.BOLD).color(TextColor.fromHexString("#22a6b3"))
        ).append(Component.text("You can follow the gameplay as a spectator.").color(TextColor.fromHexString("#dff9fb"))).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE));

        GameManager.checkWinner();
    }

    public static void setSpectator(Player player) {
        spectators.add(player);
        GameManager.getPlayers().forEach(otherPlayer -> {
            if (otherPlayer != player && !isSpectator(otherPlayer)) {
                hidePlayer(otherPlayer, player);
            } else {
                showPlayer(otherPlayer, player);
            }
        });
        player.getInventory().clear();
        player.setHealth(20);
        player.setFood(20);
        player.teleport(GameManager.getMap().getSpectatorLocation());
        player.setAllowFlying(true);
        player.setFlying(true);
        player.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 1, 100));
        player.setGameMode(GameMode.ADVENTURE);
    }

    private static void hidePlayer(Player viewer, Player target) {
        target.removeViewer(viewer);
    }

    private static void showPlayer(Player viewer, Player target) {
        target.addViewer(viewer);
    }

    public static boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    public static int getKills(Player player) {
        if (!kills.containsKey(player)) return 0;
        return kills.get(player);
    }

    public static void addKill(Player player, int amount) {
        if (!kills.containsKey(player)) {
            kills.put(player, amount);
        } else {
            kills.replace(player, kills.get(player) + amount);
        }
    }

    public static void incrementKill(Player player) {
        addKill(player, 1);
    }

}
