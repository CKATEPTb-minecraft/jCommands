package dev.ckateptb.minecraft.jcommands.config;

import dev.ckateptb.minecraft.jcommands.JCommands;
import dev.ckateptb.minecraft.jcommands.event.JCommandsReloadEvent;
import dev.ckateptb.minecraft.jyraf.config.Config;
import dev.ckateptb.minecraft.jyraf.config.hocon.HoconConfig;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.internal.configurate.ConfigurateException;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Component
public class JCommandsConfig extends HoconConfig implements Listener, Config.Autoloader {
    private String clear = "&aYour inventory has been cleared.";
    private String clearOther = "&aYou have successfully cleared the inventory of &e%player_name%&a.";
    private String coords = """
            &aCoordinates&8:
              &aX&8: &e%x%
              &aY&8: &e%y%
              &aZ&8: &e%z%
              &aYAW&8: &e%yaw%
              &aPITCH&8: &e%pitch%
            """;
    private String feed = "&aYour hunger has been quenched.";
    private String feedOther = "&aYou have successfully satisfied the hunger of &e%player_name%&a.";
    private String feedError = "&cAn external event prevented the hunger from being satisfied.";
    private String gameMode = "&aYour game mode has been changed to &e%gamemode%&a.";
    private String gameModeOther = "&aYou have changed the game mode of player &e%player_name%&a to &e%gamemode%&a.";

    @Override
    public File getFile() {
        return JCommands.getPlugin().getDataFolder().toPath().resolve("config.conf").toFile();
    }

    @EventHandler
    public void on(JCommandsReloadEvent event) {
        Logger logger = JCommands.getPlugin().getLogger();
        try {
            this.load();
            this.save();
            logger.info("config.conf reloaded successfully.");
        } catch (ConfigurateException e) {
            logger.log(Level.SEVERE, new RuntimeException(e), () -> "Failed to reload configuration of config.conf file.");
        }
    }
}
