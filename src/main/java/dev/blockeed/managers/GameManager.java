package dev.blockeed.managers;

import dev.blockeed.entities.Map;
import dev.blockeed.entities.state.GameState;
import dev.blockeed.entities.state.impl.EndingState;
import dev.blockeed.entities.state.impl.LobbyState;
import lombok.Getter;
import net.minestom.server.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    @Getter
    private static Map map;
    @Getter
    private static GameState currentState;
    @Getter
    private static List<Player> players = new ArrayList<>();

    public static void init() {
        map = MapManager.loadMap();
        if (map == null) return;

        setGameState(new LobbyState());
    }

    public static void setGameState(GameState gameState) {
        if (currentState != null)  currentState.onDisable();
        currentState = gameState;
        currentState.onEnable();
    }

    public static void addPlayer(Player player) {
        players.add(player);
        SidebarManager.addPlayer(player);
    }

    public static void removePlayer(Player player) {
        players.remove(player);
        SidebarManager.removePlayer(player);
    }

    public static boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public static boolean isRunning() {
        return currentState != null;
    }

    public static List<Player> getAlivePlayers() {
        return players.stream().filter(player -> !PlayerManager.isSpectator(player)).toList();
    }

    public static void checkWinner() {
        if (getAlivePlayers().size() > 1) return;
        if (getAlivePlayers().size() == 1) {
            GameManager.setGameState(new EndingState(getAlivePlayers().get(0)));
            return;
        }

        GameManager.setGameState(new EndingState(null));
    }

}
