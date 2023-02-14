package me.gstudiosx.rodoors.api.event;

import lombok.Getter;
import lombok.Setter;
import me.gstudiosx.rodoors.api.player.CustomPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class InteractEvent extends Event implements Cancellable {
    @Getter private static final HandlerList handlerList = new HandlerList();

    @Getter private final CustomPlayer player;
    @Getter @Setter private boolean canInteract;
    @Getter private final Action action;
    @Getter @Setter private boolean cancelled;
    @Getter private final Event event;

    public enum Action {
        BLOCK_BREAK,
        BLOCK_PLACE,
        BLOCK_HARVEST,
        BLOCK_INTERACT,
        ENTITY_INTERACT,
        ENTITY_DAMAGE,
        PLAYER_DROP
    }

    public InteractEvent(CustomPlayer player, boolean canInteract, Action action, Event event) {
        this.player = player;
        this.canInteract = canInteract;
        this.action = action;
        this.event = event;
    }

    @NotNull @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
