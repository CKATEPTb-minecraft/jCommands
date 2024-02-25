package dev.ckateptb.minecraft.jcommands.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class JCommandsReloadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public JCommandsReloadEvent() {
        super(true);
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}