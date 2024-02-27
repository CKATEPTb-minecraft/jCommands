package dev.ckateptb.minecraft.jcommands.command.gamemode.spectator;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.command.gamemode.GameModeCommand;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component
public class GameModeSpectatorCommand extends GameModeCommand {
    public GameModeSpectatorCommand(JCommands plugin, JCommandsConfig config) {
        super(plugin, config);
    }

    @CommandMethod("gmsp")
    @CommandPermission("jcommands.gamemode.self.spectator")
    public void gm(Player player) {
        this.gamemode(player);
    }

    @CommandMethod("gamemode spectator")
    @CommandPermission("jcommands.gamemode.self.spectator")
    public void gamemode(Player player) {
        this.gmOther(player, player);
    }

    @CommandMethod("gmsp <target>")
    @CommandPermission("jcommands.gamemode.other.spectator")
    public void gmOther(CommandSender sender, @Argument("target") Player target) {
        this.gamemodeOther(sender, target);
    }

    @CommandMethod("gamemode spectator <target>")
    @CommandPermission("jcommands.gamemode.other.spectator")
    public void gamemodeOther(CommandSender sender, @Argument("target") Player target) {
        this.apply(sender, GameMode.SPECTATOR, target);
    }
}
