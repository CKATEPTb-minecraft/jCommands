package dev.ckateptb.minecraft.jcommands.command.gamemode.adventure;

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
public class GameModeAdventureCommand extends GameModeCommand {
    public GameModeAdventureCommand(JCommands plugin, JCommandsConfig config) {
        super(plugin, config);
    }

    @CommandMethod("gma")
    @CommandPermission("jcommands.gamemode.self.adventure")
    public void gm(Player player) {
        this.gamemode(player);
    }

    @CommandMethod("gamemode|gm adventure")
    @CommandPermission("jcommands.gamemode.self.adventure")
    public void gamemode(Player player) {
        this.gmOther(player, player);
    }

    @CommandMethod("gma <target>")
    @CommandPermission("jcommands.gamemode.other.adventure")
    public void gmOther(CommandSender sender, @Argument("target") Player target) {
        this.gamemodeOther(sender, target);
    }

    @CommandMethod("gamemode|gm adventure <target>")
    @CommandPermission("jcommands.gamemode.other.adventure")
    public void gamemodeOther(CommandSender sender, @Argument("target") Player target) {
        this.apply(sender, GameMode.ADVENTURE, target);
    }
}
