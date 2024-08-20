package dev.blockeed.managers;

import dev.blockeed.entities.state.impl.RunningState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;
import net.minestom.server.timer.TaskSchedule;

public class SidebarManager {

    private static Sidebar sidebar;

    public static void init() {
        sidebar = new Sidebar(Component.text("SkyWars").color(TextColor.fromHexString("#7ed6df")).decorate(TextDecoration.BOLD));
        updateTask();
    }

    public static void updateTask() {
        MinecraftServer.getSchedulerManager().buildTask(SidebarManager::setupRunningLines).repeat(TaskSchedule.tick(20)).schedule();
    }

    public static void setupRunningLines() {
        Sidebar.ScoreboardLine line1 = new Sidebar.ScoreboardLine(
                "1",
                Component.text("  www.blockeed.dev").color(TextColor.fromHexString("#7ed6df")),
                1
        );
        Sidebar.ScoreboardLine line2 = new Sidebar.ScoreboardLine(
                "2",
                Component.text(""),
                2
        );
        Sidebar.ScoreboardLine line3 = new Sidebar.ScoreboardLine(
                "3",
                Component.text("Mode: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text("Normal").color(TextColor.fromHexString("#22a6b3"))),
                3
        );
        Sidebar.ScoreboardLine line4 = new Sidebar.ScoreboardLine(
                "4",
                Component.text("Map: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text(GameManager.getMap().getMapName()).color(TextColor.fromHexString("#7ed6df"))),
                4
        );
        Sidebar.ScoreboardLine line5 = new Sidebar.ScoreboardLine(
                "5",
                Component.text(""),
                5
        );
        Sidebar.ScoreboardLine line6 = new Sidebar.ScoreboardLine(
                "6",
                Component.text("Kills: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text("0").color(TextColor.fromHexString("#7ed6df"))),
                6
        );
        Sidebar.ScoreboardLine line7 = new Sidebar.ScoreboardLine(
                "7",
                Component.text(""),
                7
        );
        Sidebar.ScoreboardLine line8 = new Sidebar.ScoreboardLine(
                "8",
                Component.text("Players left: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text(GameManager.getAlivePlayers().size() - 1).color(TextColor.fromHexString("#7ed6df"))),
                8
        );
        Sidebar.ScoreboardLine line9 = new Sidebar.ScoreboardLine(
                "9",
                Component.text(""),
                9
        );

        if (sidebar.getLine("1") != null) sidebar.removeLine("1");
        if (sidebar.getLine("2") != null) sidebar.removeLine("2");
        if (sidebar.getLine("3") != null) sidebar.removeLine("3");
        if (sidebar.getLine("4") != null) sidebar.removeLine("4");
        if (sidebar.getLine("5") != null) sidebar.removeLine("5");
        if (sidebar.getLine("6") != null) sidebar.removeLine("6");
        if (sidebar.getLine("7") != null) sidebar.removeLine("7");
        if (sidebar.getLine("8") != null) sidebar.removeLine("8");
        if (sidebar.getLine("9") != null) sidebar.removeLine("9");
        sidebar.createLine(line1);
        sidebar.createLine(line2);
        sidebar.createLine(line3);
        sidebar.createLine(line4);
        sidebar.createLine(line5);
        sidebar.createLine(line6);
        sidebar.createLine(line7);
        sidebar.createLine(line8);
        sidebar.createLine(line9);
    }

    public static void addPlayer(Player player) {
        sidebar.addViewer(player);
    }

    public static void removePlayer(Player player) {
        sidebar.removeViewer(player);
    }

}
