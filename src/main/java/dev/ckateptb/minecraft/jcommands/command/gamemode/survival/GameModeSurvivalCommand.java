package dev.ckateptb.minecraft.jcommands.command.gamemode.survival;

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
public class GameModeSurvivalCommand extends GameModeCommand {
    public GameModeSurvivalCommand(JCommands plugin, JCommandsConfig config) {
        super(plugin, config);
    }

    @CommandMethod("gms")
    @CommandPermission("jcommands.gamemode.self.survival")
    public void gm(Player player) {
        this.gamemode(player);
    }

    @CommandMethod("gamemode survival")
    @CommandPermission("jcommands.gamemode.self.survival")
    public void gamemode(Player player) {
        this.gmOther(player, player);
    }

    @CommandMethod("gms <target>")
    @CommandPermission("jcommands.gamemode.other.survival")
    public void gmOther(CommandSender sender, @Argument("target") Player target) {
        this.gamemodeOther(sender, target);
    }

    @CommandMethod("gamemode survival <target>")
    @CommandPermission("jcommands.gamemode.other.survival")
    public void gamemodeOther(CommandSender sender, @Argument("target") Player target) {
        this.apply(sender, GameMode.SURVIVAL, target);
    }
}
