package dev.ckateptb.minecraft.jcommands.command.speed;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.specifier.Range;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component
@RequiredArgsConstructor
public class SpeedCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("speed <speed>")
    @CommandPermission("jcommands.speed")
    public void speed(Player player,
                      @Argument("speed") @Range(min = "0", max = "10") Float speed) {
        this.speed(player, speed, null);
    }

    @CommandMethod("speed <speed> <type>")
    @CommandPermission("jcommands.speed")
    public void speed(Player player,
                      @Argument("speed") @Range(min = "0", max = "10") Float speed,
                      @Argument("type") SpeedType type) {
        this.speedOther(player, speed, type, player);
    }

    @CommandMethod("speed <speed> <type> <target>")
    @CommandPermission("jcommands.speed.other")
    public void speedOther(CommandSender sender,
                           @Argument("speed") @Range(min = "0", max = "10") Float speed,
                           @Argument("type") SpeedType type,
                           @Argument("target") Player target) {
        if (type == null) type = target.isFlying() ? SpeedType.FLY : SpeedType.WALK;
        String speedStr = String.valueOf(speed);
        float defaultSpeed = type == SpeedType.FLY ? 0.1f : 0.2f;
        speed = speed < 1 ? defaultSpeed * speed : (((speed - 1) / 9) * (1 - defaultSpeed)) + defaultSpeed;
        switch (type) {
            case FLY -> {
                target.setFlySpeed(speed);
                target.sendMessage(Text.of(this.config.getSpeedFly(), "%speed%", speedStr));
                if (target != sender) {
                    sender.sendMessage(Text.of(this.config.getSpeedFlyOther(),
                            "%speed%", speedStr,
                            "%player_name%", target.getName()
                    ));
                }
            }
            case WALK -> {
                target.setWalkSpeed(speed);
                target.sendMessage(Text.of(this.config.getSpeedWalk(), "%speed%", speedStr));
                if (target != sender) {
                    sender.sendMessage(Text.of(this.config.getSpeedWalkOther(),
                            "%speed%", speedStr,
                            "%player_name%", target.getName()
                    ));
                }
            }
        }
    }

    public enum SpeedType {
        WALK, FLY
    }
}
