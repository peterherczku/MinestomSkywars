package dev.blockeed.entities.state.impl;

import dev.blockeed.entities.state.GameState;
import dev.blockeed.managers.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;

public class WaitingState extends GameState {
    public WaitingState() {
        super(10);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        for (int i = 0; i < GameManager.getPlayers().size(); i++) {
            Player player = GameManager.getPlayers().get(i);
            Pos pos = GameManager.getMap().getSpawnPoints().get(i);
            Pos spawnPoint = new Pos(pos.blockX(), pos.blockY(), pos.blockZ());
            player.getInstance().setBlock(pos.add(0, -1 ,0), Block.GLASS);
            for(int j = 0; j < 3; j++) {
                player.getInstance().setBlock(pos.add(1, j ,0), Block.GLASS);
                player.getInstance().setBlock(pos.add(0, j ,1), Block.GLASS);
                player.getInstance().setBlock(pos.add(0, j ,-1), Block.GLASS);
                player.getInstance().setBlock(pos.add(-1, j ,0), Block.GLASS);
            }
            player.teleport(spawnPoint.add(0.5, 0, 0.5));
        }
    }

    @Override
    public void onEnd() {
        GameManager.setGameState(new RunningState());
    }

    @Override
    public void run() {
        super.run();

        GameManager.getPlayers().forEach(player -> {
            Component title = Component.text(getTime()).color(TextColor.fromHexString("#7ed6df"));
            player.sendTitlePart(TitlePart.TITLE, title);
        });

        if (getTime() == 1) {
            for (int i = 0; i < GameManager.getPlayers().size(); i++) {
                Player player = GameManager.getPlayers().get(i);
                Pos pos = GameManager.getMap().getSpawnPoints().get(i);
                player.getInstance().setBlock(pos.add(0, -1 ,0), Block.AIR);
                for(int j = 0; j < 3; j++) {
                    player.getInstance().setBlock(pos.add(1, j ,0), Block.AIR);
                    player.getInstance().setBlock(pos.add(0, j ,1), Block.AIR);
                    player.getInstance().setBlock(pos.add(0, j ,-1), Block.AIR);
                    player.getInstance().setBlock(pos.add(-1, j ,0), Block.AIR);
                }
            }
        }

    }

    @Override
    public EventNode<Event> events() {
        EventNode<Event> node = EventNode.all("waiting-state");
        node.addListener(PlayerBlockBreakEvent.class, event -> {
            event.setCancelled(true);
        });
        node.addListener(PlayerBlockPlaceEvent.class, event -> {
            event.setCancelled(true);
        });
        return node;
    }
}
