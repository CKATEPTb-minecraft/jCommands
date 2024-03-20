package dev.ckateptb.minecraft.jcommands.command.repair;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

@Component
@RequiredArgsConstructor
public class RepairCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("repair")
    @CommandPermission("jcommands.repair")
    public void repair(Player player) {
        this.repair(player.getInventory().getItemInMainHand());
        player.sendMessage(Text.of(this.config.getRepair()));
    }

    @CommandMethod("repair all")
    @CommandPermission("jcommands.repair")
    public void repairAll(Player player) {
        player.getInventory().forEach(this::repair);
        player.sendMessage(Text.of(this.config.getRepairAll()));
    }

    private void repair(ItemStack itemStack) {
        if (itemStack == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(0);
            itemStack.setItemMeta(itemMeta);
        }
    }
}
