package dev.ckateptb.minecraft.jcommands.command.heal;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import dev.ckateptb.minecraft.jyraf.internal.commons.math3.util.FastMath;
import lombok.RequiredArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;

@Component
@RequiredArgsConstructor
public class HealCommand implements Command {
    private final JCommands plugin;
    private final JCommandsConfig config;

    @CommandMethod("heal")
    @CommandPermission("jcommands.heal")
    public void heal(Player player) {
        this.healOther(player, player);
    }

    @CommandMethod("heal <target>")
    @CommandPermission("jcommands.heal.other")
    public void healOther(CommandSender sender, @Argument("target") Player target) {
        this.plugin.syncScheduler().schedule(() -> {
            double health = target.getHealth();
            if (health == 0) {
                sender.sendMessage(Text.of(this.config.getHealDead()));
                return;
            }
            AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) return;
            double maxHealth = attribute.getValue();
            double amount = maxHealth - health;
            EntityRegainHealthEvent event = new EntityRegainHealthEvent(target, amount, EntityRegainHealthEvent.RegainReason.CUSTOM);
            this.plugin.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                sender.sendMessage(Text.of(this.config.getHealError()));
                return;
            }
            target.setHealth(FastMath.min(health + event.getAmount(), maxHealth));
            target.setFireTicks(0);
            target.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(target::removePotionEffect);
            target.sendMessage(Text.of(this.config.getHeal()));
            if (sender != target) {
                sender.sendMessage(Text.of(this.config.getHealOther(), "%player_name%", target.getName()));
            }
        });
    }
}
