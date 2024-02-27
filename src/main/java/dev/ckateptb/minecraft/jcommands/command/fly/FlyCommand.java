package dev.ckateptb.minecraft.jcommands.command.fly;

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

@Component
@RequiredArgsConstructor
public class FlyCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("fly")
    @CommandPermission("jcommands.fly")
    public void fly(Player player) {
        this.flyOther(player, player);
    }

    @CommandMethod("fly <target>")
    @CommandPermission("jcommands.fly.other")
    public void flyOther(CommandSender sender, @Argument("target") Player target) {
        boolean allowFlight = target.getAllowFlight();
        target.setAllowFlight(!allowFlight);
        boolean other = sender != target;
        String name = target.getName();
        if (allowFlight) {
            target.setFlying(false);
            target.sendMessage(Text.of(this.config.getFlyDeny()));
            if (other) {
                sender.sendMessage(Text.of(this.config.getFlyDenyOther(), "%player_name%", name));
            }
        } else {
            target.sendMessage(Text.of(this.config.getFlyAllow()));
            if (other) {
                sender.sendMessage(Text.of(this.config.getFlyAllowOther(), "%player_name%", name));
            }
        }
    }
}
