package me.gstudiosx.rodoors.listeners.player;

import me.gstudiosx.rodoors.RODoors;
import me.gstudiosx.rodoors.api.event.InteractEvent;
import me.gstudiosx.rodoors.api.player.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.util.List;

public class PlayerInteractListener implements Listener {
    public boolean shouldCancel(Player p, InteractEvent.Action action, Event event) {
        CustomPlayer player = RODoors.getInstance().getPlayer(p);

        if (player == null) return true;

        InteractEvent interactEvent = new InteractEvent(player, !player.canInteract(), action, event);

        if (interactEvent.isCancelled()) {
            return true;
        }

        if (interactEvent.isCanInteract()) {
            player.sendMessage(RODoors.getInstance().getMessage(
                    "messages.cantInteract",
                    List.of("§cYou can't do this here")
            ));

            return true;
        }

        return false;
    }

    @EventHandler public void onInteract(BlockBreakEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.BLOCK_BREAK, event));
    }

    @EventHandler public void onInteract(BlockPlaceEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.BLOCK_PLACE, event));
    }

    @EventHandler public void onInteract(PlayerHarvestBlockEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.BLOCK_HARVEST, event));
    }

    @EventHandler public void onInteract(PlayerInteractAtEntityEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.ENTITY_INTERACT, event));
    }

    @EventHandler public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.BLOCK_INTERACT, event));
    }

    @EventHandler public void onInteract(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            event.setCancelled(shouldCancel(player, InteractEvent.Action.ENTITY_DAMAGE, event));
        }
    }

    @EventHandler public void onInteract(PlayerDropItemEvent event) {
        event.setCancelled(shouldCancel(event.getPlayer(), InteractEvent.Action.PLAYER_DROP, event));
    }
}
