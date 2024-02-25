package dev.ckateptb.minecraft.jcommands.command.clear;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

@Component
@RequiredArgsConstructor
public class ClearCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("clear")
    @CommandPermission("jcommands.clear")
    public void clear(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        player.sendMessage(Text.of(this.config.getClear()));
    }

    @CommandMethod("clear <target>")
    @CommandPermission("jcommands.clear.other")
    public void clearOther(CommandSender sender, @Argument("target") Player player) {
        this.clear(player);
        sender.sendMessage(Text.of(this.config.getClearOther(), "%player_name%", player.getName()));
    }
}
