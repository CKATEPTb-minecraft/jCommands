package dev.ckateptb.minecraft.jcommands.command.gamemode.creative;

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
public class GameModeCreativeCommand extends GameModeCommand {
    public GameModeCreativeCommand(JCommands plugin, JCommandsConfig config) {
        super(plugin, config);
    }

    @CommandMethod("gmc")
    @CommandPermission("jcommands.gamemode.self.creative")
    public void gm(Player player) {
        this.gamemode(player);
    }

    @CommandMethod("gamemode creative")
    @CommandPermission("jcommands.gamemode.self.creative")
    public void gamemode(Player player) {
        this.gmOther(player, player);
    }

    @CommandMethod("gmc <target>")
    @CommandPermission("jcommands.gamemode.other.creative")
    public void gmOther(CommandSender sender, @Argument("target") Player target) {
        this.gamemodeOther(sender, target);
    }

    @CommandMethod("gamemode creative <target>")
    @CommandPermission("jcommands.gamemode.other.creative")
    public void gamemodeOther(CommandSender sender, @Argument("target") Player target) {
        this.apply(sender, GameMode.CREATIVE, target);
    }
}
