package dev.ckateptb.minecraft.jcommands.command.mob;

import com.google.common.base.Predicates;
import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.config.JCommandsConfig;
import dev.ckateptb.minecraft.jyraf.async.tracker.entity.EntityTrackerService;
import dev.ckateptb.minecraft.jyraf.async.tracker.entity.world.WorldRepository;
import dev.ckateptb.minecraft.jyraf.colider.Colliders;
import dev.ckateptb.minecraft.jyraf.command.Command;
import dev.ckateptb.minecraft.jyraf.component.Text;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.Argument;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandMethod;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.CommandPermission;
import dev.ckateptb.minecraft.jyraf.internal.commands.annotations.specifier.Range;
import dev.ckateptb.minecraft.jyraf.math.ImmutableVector;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MobCommand implements Command {
    private final JCommands plugin;
    private final JCommandsConfig config;
    private final EntityTrackerService entityTrackerService;

    @CommandMethod("mob spawn <type> [amount] [location]")
    @CommandPermission("jcommands.mob.spawn")
    public void mobSpawn(Player sender, @Argument("type") EntityType type,
                         @Argument("amount") @Range(min = "1") Integer amount, @Argument("location") Location location) {
        if (!type.isAlive()) {
            sender.sendMessage(Text.of(this.config.getMobSpawnError()));
            return;
        }
        final int count = amount != null ? amount : 1;
        Mono.justOrEmpty(location)
                .map(ImmutableVector::of)
                .switchIfEmpty(Mono.defer(() -> Colliders.ray(sender, 100, 0.1)
                        .getPosition(true, false, true, true,
                                Predicates.alwaysTrue(), Predicates.alwaysTrue()
                        )))
                .subscribeOn(this.plugin.syncScheduler())
                .subscribe(vector -> {
                    World world = sender.getWorld();
                    Location loc = vector.toLocation(world);
                    for (int i = 0; i < count; i++) {
                        world.spawnEntity(loc, type);
                    }
                    sender.sendMessage(Text.of(this.config.getMobSpawn(), "%count%", String.valueOf(count)));
                });
    }

    @CommandMethod("mob kill <radius>")
    @CommandPermission("jcommands.mob.kill")
    public void mobKill(Player sender, @Argument("radius") Double radius) {
        Location location = sender.getLocation();
        Colliders.sphere(location, radius)
                .affectEntities(entities -> this.mobKill(entities)
                        .subscribe(count -> sender.sendMessage(Text.of(this.config.getMobKill(),
                                "%count%", String.valueOf(count),
                                "%radius%", String.valueOf(radius)
                        )))
                );
    }

    @CommandMethod("mob kill all [world]")
    @CommandPermission("jcommands.mob.kill")
    public void mobKill(CommandSender sender, @Argument("world") World world) {
        this.mobKill(Flux.fromIterable(Bukkit.getWorlds())
                        .map(World::getUID)
                        .filter(value -> world == null || value.equals(world.getUID()))
                        .flatMap(this.entityTrackerService::getWorld)
                        .flatMap(WorldRepository::getEntities))
                .subscribe(count -> {
                    if (world != null) {
                        sender.sendMessage(Text.of(this.config.getMobKillWorld(),
                                "%count%", String.valueOf(count),
                                "%world%", world.getName()
                        ));
                    } else {
                        sender.sendMessage(Text.of(this.config.getMobKillAll(),
                                "%count%", String.valueOf(count)
                        ));
                    }
                });
    }

    private Mono<Long> mobKill(Flux<Entity> entities) {
        return entities
                .filter(entity -> entity instanceof LivingEntity && !(entity instanceof Player))
                .cast(LivingEntity.class)
                .publishOn(this.plugin.syncScheduler())
                .doOnNext(Entity::remove)
                .count();
    }
}
