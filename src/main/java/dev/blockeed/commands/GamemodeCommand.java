package dev.blockeed.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode");

        // Define the game mode arguments
        ArgumentLiteral creative = ArgumentType.Literal("creative");
        ArgumentLiteral survival = ArgumentType.Literal("survival");
        ArgumentLiteral adventure = ArgumentType.Literal("adventure");
        ArgumentLiteral spectator = ArgumentType.Literal("spectator");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) setGameMode(player, GameMode.CREATIVE);
        }, creative);

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) setGameMode(player, GameMode.SURVIVAL);
        }, survival);

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) setGameMode(player, GameMode.ADVENTURE);
        }, adventure);

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) setGameMode(player, GameMode.SPECTATOR);
        }, spectator);
    }

    // Utility method to set the game mode
    private void setGameMode(Player player, GameMode gameMode) {
        if (player != null) {
            player.setGameMode(gameMode);
            player.sendMessage("Your game mode has been changed to " + gameMode.name().toLowerCase());
        }
    }

    // Utility method to resolve the target player
    private Player getTargetPlayer(CommandSender sender, Player target) {
        if (target == null && sender instanceof Player) {
            return (Player) sender; // If no target is specified, use the command sender
        }
        return target;
    }

}
