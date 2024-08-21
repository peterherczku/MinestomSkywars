package dev.blockeed;

import dev.blockeed.commands.GamemodeCommand;
import dev.blockeed.commands.SkywarsCommand;
import dev.blockeed.events.GeneralEventHandler;
import dev.blockeed.managers.GameManager;
import dev.blockeed.managers.SidebarManager;
import dev.blockeed.managers.WorldManager;
import io.github.togar2.pvp.MinestomPvP;
import net.minestom.server.MinecraftServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        WorldManager.init();
        GeneralEventHandler.init();
        GameManager.init();
        MinestomPvP.init();
        SidebarManager.init();

        registerCommands();

        minecraftServer.start("0.0.0.0", 25565);
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new SkywarsCommand());
        MinecraftServer.getCommandManager().register(new GamemodeCommand());
    }

}