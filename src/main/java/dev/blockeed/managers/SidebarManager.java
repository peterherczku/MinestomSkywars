package dev.blockeed.managers;

import dev.blockeed.entities.state.impl.RunningState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.BelowNameTag;
import net.minestom.server.scoreboard.Sidebar;
import net.minestom.server.timer.TaskSchedule;

import java.util.HashMap;
import java.util.Map;

public class SidebarManager {

    private static Map<Player, Sidebar> sidebars = new HashMap<>();
    public static void init() {
        updateTask();
    }

    public static void updateTask() {
        MinecraftServer.getSchedulerManager().buildTask(SidebarManager::setRunningLines).repeat(TaskSchedule.tick(20)).schedule();
    }

    public static void setRunningLines() {
        sidebars.forEach(((player, sidebar) -> {
            sidebar.setTitle(Component.text("SkyWars").color(TextColor.fromHexString("#7ed6df")).decorate(TextDecoration.BOLD));
            sidebar.updateLineContent("6", Component.text("Kills: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text("0").color(TextColor.fromHexString("#7ed6df"))));
            sidebar.updateLineContent("8", Component.text("Players left: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text(GameManager.getAlivePlayers().size()).color(TextColor.fromHexString("#7ed6df"))));
        }));
    }

    public static void addPlayer(Player player) {
        player.setBelowNameTag(new BelowNameTag("test", Component.text("LvL ").color(TextColor.fromHexString("#95a5a6")).append(Component.text("50").color(TextColor.fromHexString("#ecf0f1")))));
        Sidebar sidebar = new Sidebar(Component.text("SkyWars").color(TextColor.fromHexString("#7ed6df")).decorate(TextDecoration.BOLD));
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
                Component.text("Kills: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text(PlayerManager.getKills(player)).color(TextColor.fromHexString("#7ed6df"))),
                6
        );
        Sidebar.ScoreboardLine line7 = new Sidebar.ScoreboardLine(
                "7",
                Component.text(""),
                7
        );
        Sidebar.ScoreboardLine line8 = new Sidebar.ScoreboardLine(
                "8",
                Component.text("Players left: ").color(TextColor.fromHexString("#dff9fb")).append(Component.text(GameManager.getAlivePlayers().size()).color(TextColor.fromHexString("#7ed6df"))),
                8
        );
        Sidebar.ScoreboardLine line9 = new Sidebar.ScoreboardLine(
                "9",
                Component.text(""),
                9
        );
        sidebar.createLine(line1);
        sidebar.createLine(line2);
        sidebar.createLine(line3);
        sidebar.createLine(line4);
        sidebar.createLine(line5);
        sidebar.createLine(line6);
        sidebar.createLine(line7);
        sidebar.createLine(line8);
        sidebar.createLine(line9);
        sidebar.addViewer(player);
        sidebars.put(player, sidebar);
    }

    public static void removePlayer(Player player) {
        if (sidebars.containsKey(player)) {
            sidebars.get(player).removeViewer(player);
            sidebars.remove(player);
        }
    }

}
