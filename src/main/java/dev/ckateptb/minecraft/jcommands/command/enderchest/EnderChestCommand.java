package dev.ckateptb.minecraft.jcommands.command.enderchest;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Component
@RequiredArgsConstructor
public class EnderChestCommand implements Command {
    private final JCommands plugin;

    @CommandMethod("enderchest|ec")
    @CommandPermission("jcommands.enderchest")
    public void open(Player player) {
        Inventory enderChest = player.getEnderChest();
        this.plugin.syncScheduler().schedule(() -> player.openInventory(enderChest));
    }

    @CommandMethod("enderchest|ec <target>")
    @CommandPermission("jcommands.enderchest.other")
    public void openOther(Player player, @Argument("target") Player target) {
        Inventory enderChest = target.getEnderChest();
        this.plugin.syncScheduler().schedule(() -> player.openInventory(enderChest));
    }
}
