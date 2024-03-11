package dev.ckateptb.minecraft.jcommands.command.sudo;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component
@RequiredArgsConstructor
public class SudoCommand implements Command {
    private final JCommands plugin;
    private final JCommandsConfig config;

    @CommandMethod("sudo <target> <command>")
    @CommandPermission("jcommands.sudo")
    public void sudo(CommandSender sender, @Argument("target") Player target, @Argument("command") String[] command) {
        sender.sendMessage(Text.of(this.config.getSudo(), "%player_name%", target.getName()));
        this.plugin.syncScheduler().schedule(() -> Bukkit.dispatchCommand(target, String.join(" ", command)));
    }

    @CommandMethod("sudo <target> c: <command>")
    @CommandPermission("jcommands.sudo")
    public void sudoChat(CommandSender sender, @Argument("target") Player target, @Argument("command") String[] command) {
        sender.sendMessage(Text.of(this.config.getSudoChat(), "%player_name%", target.getName()));
        this.plugin.syncScheduler().schedule(() -> target.chat(String.join(" ", command)));
    }
}
