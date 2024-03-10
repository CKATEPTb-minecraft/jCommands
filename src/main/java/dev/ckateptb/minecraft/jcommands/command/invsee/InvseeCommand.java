package dev.ckateptb.minecraft.jcommands.command.invsee;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import dev.ckateptb.minecraft.jyraf.menu.Menu;
import dev.ckateptb.minecraft.jyraf.menu.chest.ChestMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class InvseeCommand implements Command {
    private final JCommands plugin;
    private final JCommandsConfig config;

    @CommandMethod("invsee <target>")
    @CommandPermission("jcommands.invsee")
    public void invsee(Player player, @Argument("target") Player target) {
        PlayerInventory inventory = target.getInventory();
        boolean editable = player.hasPermission("jcommands.invsee.modify");
        ChestMenu menu = Menu.builder()
                .chest(Text.of(this.config.getInvseeTitle(), "%player_name%", target.getName()), 5)
                .closable(true)
                .editable(editable)
                .updateContext(context -> {
                    context.set(2, context.item(inventory.getHelmet(), (Menu.ClickHandler) event ->
                            this.setItem(editable, event, inventory::setHelmet)));
                    context.set(3, context.item(inventory.getChestplate(), (Menu.ClickHandler) event ->
                            this.setItem(editable, event, inventory::setChestplate)));
                    context.set(4, context.item(inventory.getLeggings(), (Menu.ClickHandler) event ->
                            this.setItem(editable, event, inventory::setLeggings)));
                    context.set(5, context.item(inventory.getBoots(), (Menu.ClickHandler) event ->
                            this.setItem(editable, event, inventory::setBoots)));
                    context.set(6, context.item(inventory.getItemInOffHand(), (Menu.ClickHandler) event ->
                            this.setItem(editable, event, inventory::setItemInOffHand)));
                    for (int slot = 9; slot < 5 * 9; slot++) {
                        final int original = slot - 9;
                        context.set(slot, context.item(inventory.getItem(original),
                                (Menu.ClickHandler) event -> this.setItem(editable, event,
                                        itemStack -> inventory.setItem(original, itemStack)
                                )
                        ));
                    }
                })
                .build();
        this.plugin.syncScheduler().schedule(() -> menu.open(player));
    }

    private void setItem(boolean editable, InventoryClickEvent event, Consumer<ItemStack> consumer) {
        switch (event.getAction()) {
            case CLONE_STACK -> {
            }
            case PICKUP_ALL, PLACE_ALL, SWAP_WITH_CURSOR -> {
                if (editable) consumer.accept(event.getCursor());
            }
            default -> event.setCancelled(true);
        }
    }
}
