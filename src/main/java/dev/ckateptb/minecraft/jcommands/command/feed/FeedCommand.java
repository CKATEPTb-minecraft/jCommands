package dev.ckateptb.minecraft.jcommands.command.feed;

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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@Component
@RequiredArgsConstructor
public class FeedCommand implements Command {
    private final JCommands plugin;
    private final JCommandsConfig config;

    @CommandMethod("feed")
    @CommandPermission("jcommands.feed")
    public void feed(Player player) {
        this.feedOther(player, player);
    }

    @CommandMethod("feed <target>")
    @CommandPermission("jcommands.feed.other")
    public void feedOther(CommandSender sender, @Argument("target") Player target) {
        this.plugin.syncScheduler().schedule(() -> {
            FoodLevelChangeEvent event = new FoodLevelChangeEvent(target, 30);
            this.plugin.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                sender.sendMessage(Text.of(this.config.getFeedError()));
                return;
            }
            target.setFoodLevel(FastMath.min(event.getFoodLevel(), 20));
            target.setSaturation(10);
            target.setExhaustion(0);
            target.sendMessage(Text.of(this.config.getFeed()));
            if (sender != target) {
                sender.sendMessage(Text.of(this.config.getFeedOther(), "%player_name%", target.getName()));
            }
        });
    }
}
