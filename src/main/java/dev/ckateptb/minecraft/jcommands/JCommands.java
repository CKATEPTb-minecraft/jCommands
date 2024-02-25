package dev.ckateptb.minecraft.jcommands;

import dev.ckateptb.minecraft.jyraf.Jyraf;
import dev.ckateptb.minecraft.jyraf.container.IoC;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import reactor.core.scheduler.Scheduler;

public class JCommands extends JavaPlugin {
    @Getter
    private static JCommands plugin;

    public JCommands() {
        JCommands.plugin = this;
        IoC.scan(this, string -> !string.startsWith(JCommands.class.getPackageName() + ".internal"));
    }

    public Scheduler syncScheduler() {
        return Jyraf.syncScheduler(this);
    }
}
