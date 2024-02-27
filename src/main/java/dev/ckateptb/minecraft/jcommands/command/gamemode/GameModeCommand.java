package dev.ckateptb.minecraft.jcommands.command.gamemode;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public abstract class GameModeCommand implements Command {
    protected final JCommands plugin;
    protected final JCommandsConfig config;

    public abstract void gm(Player player);

    public abstract void gamemode(Player player);

    public abstract void gmOther(CommandSender sender, @Argument("target") Player target);

    public abstract void gamemodeOther(CommandSender sender, @Argument("target") Player target);

    protected void apply(CommandSender sender, GameMode gameMode, Player target) {
        String name = gameMode.name();
        Component targetMessage = Text.of(this.config.getGameMode(), "%gamemode%", name);
        Component senderMessage = Text.of(this.config.getGameModeOther(),
                "%gamemode%", name,
                "%player_name%", target.getName()
        );
        this.plugin.syncScheduler().schedule(() -> {
            target.setGameMode(gameMode);
            target.sendMessage(targetMessage);
            if (target != sender) sender.sendMessage(senderMessage);
        });
    }
}
