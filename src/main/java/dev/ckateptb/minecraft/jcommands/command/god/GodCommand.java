package dev.ckateptb.minecraft.jcommands.command.god;

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
public class GodCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("god")
    @CommandPermission("jcommands.god")
    public void god(Player player) {
        this.godOther(player, player);
    }

    @CommandMethod("god <target>")
    @CommandPermission("jcommands.god.other")
    public void godOther(CommandSender sender, @Argument("target") Player target) {
        boolean enabled = !target.isInvulnerable();
        target.setInvulnerable(enabled);
        target.sendMessage(Text.of(enabled ? this.config.getGodOn() : this.config.getGodOff()));
        if (sender != target) {
            sender.sendMessage(Text.of(enabled ? this.config.getGodOnOther() : this.config.getGodOffOther(),
                    "%player_name%", target.getName()
            ));
        }
    }
}
