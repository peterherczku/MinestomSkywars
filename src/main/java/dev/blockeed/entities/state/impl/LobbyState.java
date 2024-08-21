package dev.blockeed.entities.state.impl;

import dev.blockeed.entities.state.GameState;
import dev.blockeed.managers.GameManager;
import dev.blockeed.managers.WorldManager;
import dev.blockeed.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class LobbyState extends GameState {
    public LobbyState() {
        super(15);
    }

    @Override
    public void onEnd() {
        GameManager.setGameState(new WaitingState());
    }

    @Override
    public void run() {
        super.run();

        GameManager.getPlayers().forEach(player -> {
            Component title = Component.text(getTime()).color(TextColor.fromHexString("#22a6b3"));
            player.sendTitlePart(TitlePart.TITLE, title);
        });
    }

    @Override
    public EventNode<Event> events() {
        EventNode<Event> node = EventNode.all("lobby-state");
        node.addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();
            player.sendMessage(Constants.PREFIX.append(player.getName()
                    .color(TextColor.fromHexString("#7ed6df"))
                            .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                    .append(
                            Component
                                    .text(" joined the server.")
                                    .color(TextColor.fromHexString("#dff9fb")))
            ));
        });
        node.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(WorldManager.getInstanceContainer());
            player.setRespawnPoint(GameManager.getMap().getLobbyLocation());
        });
        return node;
    }
}
