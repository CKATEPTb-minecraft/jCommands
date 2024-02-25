package dev.ckateptb.minecraft.jcommands.command.coords;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class CoordsCommand implements Command {
    private final JCommandsConfig config;
    private final DecimalFormat format = new DecimalFormat("#.000");

    @CommandMethod("coords")
    @CommandPermission("jcommands.coords")
    public void coords(Player player) {
        Location location = player.getLocation();
        player.sendMessage(Text.of(this.config.getCoords(),
                "%x%", this.format.format(location.getX()),
                "%y%", this.format.format(location.getY()),
                "%z%", this.format.format(location.getZ()),
                "%yaw%", this.format.format(location.getYaw()),
                "%pitch%", this.format.format(location.getPitch())
        ));
    }
}
