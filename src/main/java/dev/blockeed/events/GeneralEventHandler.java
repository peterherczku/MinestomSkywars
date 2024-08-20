package dev.blockeed.events;

import dev.blockeed.managers.GameManager;
import dev.blockeed.managers.WorldManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.time.Duration;

public class GeneralEventHandler {

    public static void init() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            if (!GameManager.isRunning()){
                event.setSpawningInstance(WorldManager.getInstanceContainer());
                player.setRespawnPoint(new Pos(0, 42, 0));
            }
        });
        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            final Player player = event.getPlayer();
            if (GameManager.isRunning()) GameManager.addPlayer(player);
        });
        globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            if (GameManager.containsPlayer(event.getPlayer())) GameManager.removePlayer(event.getPlayer());
        });
    }

}
