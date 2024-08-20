package dev.blockeed.entities;

import lombok.Builder;
import lombok.Data;
import net.minestom.server.coordinate.Pos;

import java.util.List;

@Data @Builder
public class Map {

    private String id;
    private String mapName;
    private Pos lobbyLocation;
    private Pos spectatorLocation;
    private List<Pos> spawnPoints;
    private List<Pos> chestPoints;
    private List<Pos> advancedChestPoints;

}
