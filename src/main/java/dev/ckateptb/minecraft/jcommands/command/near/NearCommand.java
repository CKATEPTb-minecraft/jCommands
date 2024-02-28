package dev.ckateptb.minecraft.jcommands.command.near;

import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.colider.Colliders;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class NearCommand implements Command {
    private final JCommandsConfig config;
    private final DecimalFormat format = new DecimalFormat("#.##");

    @CommandMethod("near [radius]")
    @CommandPermission("jcommands.near")
    public void near(Player sender, @Argument("radius") Double radius) {
        Location location = sender.getLocation();
        Colliders.sphere(location, radius == null ? 20 : radius)
                .affectEntities(entities -> entities
                        .filter(entity -> entity instanceof Player)
                        .filter(entity -> entity != sender)
                        .map(entity -> (Player) entity)
                        .sort((o1, o2) -> {
                            Location first = o1.getLocation();
                            Location second = o2.getLocation();
                            return (int) (first.distanceSquared(location) - second.distanceSquared(location));
                        })
                        .map(player -> Text.of(this.config.getNearEntry(),
                                "%player_name%", player.getName(),
                                "%distance%", this.format.format(player.getLocation().distance(location))
                        ))
                        .collectList()
                        .map(players -> {
                            if (players.isEmpty()) return Text.of(this.config.getNearEmpty());
                            net.kyori.adventure.text.Component separator = Text.of(this.config.getNearDivider());
                            JoinConfiguration configuration = JoinConfiguration.separator(separator);
                            return net.kyori.adventure.text.Component.join(configuration, players);
                        })
                        .subscribe(result -> sender.sendMessage(Text.of(this.config.getNear())
                                .replaceText(builder -> builder
                                        .matchLiteral("%entries%")
                                        .replacement(result)
                                )))
                );
    }
}
