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
    private String heal = "&aYou have been healed.";
    private String healOther = "&aYou have successfully healed the &e%player_name%&a.";
    private String healDead = "&cYou can't heal the dead.";
    private String healError = "&cAn external event prevented heal.";
    private String top = "&aYou have been teleported to the top solid/liquid block of the world relative to your location.";
    private String topOther = "&aYou have successfully teleported &e%player_name%&a to the top solid/liquid block relative to his location.";
    private String flyAllow = "&aFlight mode was &eenabled&a.";
    private String flyDeny = "&aFlight mode was &edisabled&a.";
    private String flyAllowOther = "&aFlight mode was &eenabled&a for &e%player_name%&a.";
    private String flyDenyOther = "&aFlight mode was &edisabled&a for &e%player_name%&a.";
    private String near = "&aPlayers nearby: %entries%&a.";
    private String nearEntry = "&e%player_name% &8(%distance%)";
    private String nearDivider = "&a, ";
    private String nearEmpty = "&8empty";
    private String invseeTitle = "Inventory Spy: %player_name%";
    private String godOn = "&aGod mode was &eenabled&a.";
    private String godOff = "&aGod mode was &edisabled&a.";
    private String godOnOther = "&aGod mode was &eenabled&a for &e%player_name%&a.";
    private String godOffOther = "&aGod mode was &edisabled&a for &e%player_name%&a.";
    private String speedFly = "&aYour flight speed is set to &e%speed%&a.";
    private String speedWalk = "&aYour walk speed is set to &e%speed%&a.";
    private String speedFlyOther = "&aThe &e%player_name%&a flight speed is set to &e%speed%&a.";
    private String speedWalkOther = "&aThe &e%player_name%&a walk speed is set to &e%speed%&a.";
    private String repair = "&aThe item in hand has been repaired.";
    private String repairAll = "&aItems have been repaired.";
    private String sudo = "&aForces sending of a command on behalf of &e%player_name%&a.";
    private String sudoChat = "&aForces sending of a message on behalf of &e%player_name%&a.";
    private String mobKill = "&e%count% &amobs within a radius of &e%radius% &awere killed.";
    private String mobKillWorld = "&e%count% &amobs in world &e%world% &ahave been killed.";
    private String mobKillAll = "&e%count% &amobs were killed.";
    private String mobSpawn = "&e%count% &amobs were successfully spawned.";
    private String mobSpawnError = "&cYou must specify the living entity type.";

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
