package dev.ckateptb.minecraft.jcommands.command.top;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import dev.ckateptb.minecraft.jyraf.internal.commons.math3.util.FastMath;
import dev.ckateptb.minecraft.jyraf.math.ImmutableVector;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component
@RequiredArgsConstructor
public class TopCommand implements Command {
    private final JCommandsConfig config;

    @CommandMethod("top")
    @CommandPermission("jcommands.top")
    public void top(Player player) {
        this.topOther(player, player);
    }

    @CommandMethod("top <target>")
    @CommandPermission("jcommands.top.other")
    public void topOther(CommandSender sender, @Argument("target") Player target) {
        World world = target.getWorld();
        int maxHeight = world.getMaxHeight();
        Location location = target.getLocation();
        double distanceAboveGround = ImmutableVector.of(location)
                .setY(maxHeight)
                .getDistanceAboveGround(world, false);
        location.setY(FastMath.max(maxHeight - distanceAboveGround, world.getMinHeight()));
        target.teleportAsync(location)
                .thenRun(() -> {
                    target.sendMessage(Text.of(this.config.getTop()));
                    if (sender != target) {
                        sender.sendMessage(Text.of(this.config.getTopOther(), "%player_name%", target.getName()));
                    }
                });
    }
}
