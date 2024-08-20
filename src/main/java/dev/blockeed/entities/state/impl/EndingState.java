package dev.blockeed.entities.state.impl;

import dev.blockeed.entities.state.GameState;
import dev.blockeed.managers.GameManager;
import dev.blockeed.managers.PlayerManager;
import dev.blockeed.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;

public class EndingState extends GameState {

    private Player winner;

    public EndingState(Player winner) {
        super(30);

        this.winner = winner;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        GameManager.getPlayers().forEach(player -> {
            player.teleport(GameManager.getMap().getSpectatorLocation());
            if (!PlayerManager.isSpectator(player)) PlayerManager.setSpectator(player);
            if (winner != null) {
                player.sendMessage(Constants.PREFIX.append(
                        Component.text(player.getUsername()).color(TextColor.fromHexString("#7ed6df")).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                ).append(Component.text(" has won the game!").color(TextColor.fromHexString("#dff9fb")).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)));
                Component title = Component.text(player.getUsername()).decorate(TextDecoration.BOLD).color(TextColor.fromHexString("#7ed6df"));
                Component subtitle = Component.text("has won the game").color(TextColor.fromHexString("#95afc0"));
                player.sendTitlePart(TitlePart.TITLE, title);
                player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            } else {
                player.sendMessage(Constants.PREFIX.append(
                        Component.text("Draw!").color(TextColor.fromHexString("#7ed6df")).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)
                ).append(Component.text(" The game ended with a draw.").color(TextColor.fromHexString("#dff9fb")).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)));
                Component title = Component.text("Draw").decorate(TextDecoration.BOLD).color(TextColor.fromHexString("#7ed6df"));
                Component subtitle = Component.text("The game ended with a draw.").color(TextColor.fromHexString("#95afc0"));
                player.sendTitlePart(TitlePart.TITLE, title);
                player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            }
        });
    }

    @Override
    public void onEnd() {

    }

    @Override
    public EventNode<Event> events() {
        EventNode<Event> node = EventNode.all("ending-state");
        return node;
    }
}
