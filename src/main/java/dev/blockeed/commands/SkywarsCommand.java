package dev.blockeed.commands;

import dev.blockeed.entities.Map;
import dev.blockeed.managers.MapManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

import java.util.*;

public class SkywarsCommand extends Command {

    private static java.util.Map<UUID, Map.MapBuilder> mapBuilders = new HashMap<>();
    private static java.util.Map<UUID, List<Pos>> spawnPositions = new HashMap<>();
    private static java.util.Map<UUID, List<Pos>> chestPositions = new HashMap<>();
    private static java.util.Map<UUID, List<Pos>> advancedChestPositions = new HashMap<>();

    public SkywarsCommand() {
        super("skywars", "sw");

        setDefaultExecutor(((sender, context) -> {
            sender.sendMessage("Skywars help!");
        }));

        addSubcommand(new SkywarsCreateArenaSubcommand());
        addSubcommand(new SkywarsSetMapNameSubcommand());
        addSubcommand(new SkywarsAddSpawnSubcommand());
        addSubcommand(new SkywarsAddChestSubcommand());
        addSubcommand(new SkywarsAddAdvancedChestSubcommand());
        addSubcommand(new SkywarsSetLobbySubcommand());
        addSubcommand(new SkywarsSetSpectatorSubcommand());
        addSubcommand(new SkywarsSaveArenaSubcommand());
    }

    public static class SkywarsCreateArenaSubcommand extends Command {

        public SkywarsCreateArenaSubcommand() {
            super("createarena");

            setDefaultExecutor(((sender, context) -> {
                sender.sendMessage("Usage: /skywars createarena <id>");
            }));

            var arenaIdArg = ArgumentType.String("id");

            addSyntax(((sender, context) -> {
                if (sender instanceof Player player) {
                    try {
                        String id = context.get(arenaIdArg);
                        if (mapBuilders.containsKey(player.getUuid())) {
                            player.sendMessage("You have already started creating an arena.");
                            return;
                        }
                        if (id.contains(" ")) {
                            player.sendMessage("The arena's id cannot contain any spaces.");
                            return;
                        }

                        Map.MapBuilder builder = Map.builder().id(id);
                        mapBuilders.put(player.getUuid(), builder);
                        player.sendMessage("You have successfully started creating an arena.");
                    } catch (ArgumentSyntaxException e) {
                        player.sendMessage("Invalid arena id!");
                    }
                }
            }), arenaIdArg);
        }



    }

    public static class SkywarsSetMapNameSubcommand extends Command {

        public SkywarsSetMapNameSubcommand() {
            super("setmapname");

            setDefaultExecutor(((sender, context) -> {
                sender.sendMessage("Usage: /skywars setmapname <arena name>");
            }));

            var arenaNameArg = ArgumentType.String("arenaName");

            addSyntax(((sender, context) -> {
                if (sender instanceof Player player) {
                    try {
                        String mapName = context.get(arenaNameArg);
                        if (!mapBuilders.containsKey(player.getUuid())) {
                            player.sendMessage("You haven't started creating an arena yet.");
                            return;
                        }

                        mapBuilders.get(player.getUuid()).mapName(mapName);
                        player.sendMessage("You have successfully set the arena's name.");
                    } catch (ArgumentSyntaxException e) {
                        player.sendMessage("Invalid arena name!");
                    }
                }
            }), arenaNameArg);
        }
    }

    public static class SkywarsAddSpawnSubcommand extends Command {

        public SkywarsAddSpawnSubcommand() {
            super("addspawn");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                Pos pos = player.getPosition();
                if (spawnPositions.containsKey(player.getUuid())) {
                    List<Pos> newPositions = new ArrayList<>(spawnPositions.get(player.getUuid()));
                    newPositions.add(pos);
                    spawnPositions.replace(player.getUuid(), newPositions);
                } else  {
                    spawnPositions.put(player.getUuid(), Collections.singletonList(pos));
                }

                player.sendMessage("You have successfully added a spawn location.");
            }));

        }
    }

    public static class SkywarsAddChestSubcommand extends Command {

        public SkywarsAddChestSubcommand() {
            super("addchest");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                Pos pos = player.getPosition();
                if (chestPositions.containsKey(player.getUuid())) {
                    List<Pos> newPositions = new ArrayList<>(chestPositions.get(player.getUuid()));
                    newPositions.add(pos);
                    chestPositions.replace(player.getUuid(), newPositions);
                } else  {
                    chestPositions.put(player.getUuid(), Collections.singletonList(pos));
                }

                player.sendMessage("You have successfully added a chest location.");
            }));

        }
    }

    public static class SkywarsAddAdvancedChestSubcommand extends Command {

        public SkywarsAddAdvancedChestSubcommand() {
            super("addadvancedchest");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                Pos pos = player.getPosition();
                if (advancedChestPositions.containsKey(player.getUuid())) {
                    List<Pos> newPositions = new ArrayList<>(advancedChestPositions.get(player.getUuid()));
                    newPositions.add(pos);
                    advancedChestPositions.replace(player.getUuid(), newPositions);
                } else  {
                    advancedChestPositions.put(player.getUuid(), Collections.singletonList(pos));
                }

                player.sendMessage("You have successfully added a chest location.");
            }));

        }
    }

    public static class SkywarsSetLobbySubcommand extends Command {

        public SkywarsSetLobbySubcommand() {
            super("setlobby");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                Pos pos = player.getPosition();
                mapBuilders.get(player.getUuid()).lobbyLocation(pos);

                player.sendMessage("You have successfully set the lobby's location.");
            }));

        }
    }

    public static class SkywarsSetSpectatorSubcommand extends Command {

        public SkywarsSetSpectatorSubcommand() {
            super("setspectator");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                Pos pos = player.getPosition();
                mapBuilders.get(player.getUuid()).spectatorLocation(pos);

                player.sendMessage("You have successfully set the spectator's spawn location.");
            }));

        }
    }

    public static class SkywarsSaveArenaSubcommand extends Command {

        public SkywarsSaveArenaSubcommand() {
            super("savearena");

            setDefaultExecutor(((sender, context) -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("You are not a player!");
                    return;
                }

                if (!mapBuilders.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't started creating an arena yet.");
                    return;
                }

                if (!spawnPositions.containsKey(player.getUuid())) {
                    player.sendMessage("You haven't created any spawn points.");
                    return;
                }

                if (spawnPositions.get(player.getUuid()).size() < 2) {
                    player.sendMessage("You have to create at least 2 spawn points.");
                    return;
                }

                if (chestPositions.get(player.getUuid()).size() < 2) {
                    player.sendMessage("You have to create at least 2 chest points.");
                    return;
                }

                if (advancedChestPositions.get(player.getUuid()).size() < 2) {
                    player.sendMessage("You have to create at least 2 advanced chest points.");
                    return;
                }


                mapBuilders.get(player.getUuid()).spawnPoints(spawnPositions.get(player.getUuid()));
                mapBuilders.get(player.getUuid()).chestPoints(chestPositions.get(player.getUuid()));
                mapBuilders.get(player.getUuid()).advancedChestPoints(advancedChestPositions.get(player.getUuid()));
                Map map = mapBuilders.get(player.getUuid()).build();
                MapManager.saveMap(map);
                player.sendMessage("You have successfully saved the arena");
                mapBuilders.remove(player.getUuid());
                spawnPositions.remove(player.getUuid());
            }));

        }
    }

}
