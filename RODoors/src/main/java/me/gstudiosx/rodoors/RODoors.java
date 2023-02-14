package me.gstudiosx.rodoors;

import lombok.Getter;
import me.gstudiosx.rodoors.api.player.CustomPlayer;
import me.gstudiosx.rodoors.listeners.player.PlayerConnectionListener;
import me.gstudiosx.rodoors.listeners.player.PlayerInteractListener;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.UUID;

public final class RODoors extends JavaPlugin {
    @Getter private static RODoors instance;

    @Getter private boolean debug;

    private BukkitAudiences adventure;
    private MiniMessage miniMessage;

    @Getter private PlayerConnectionListener playerConnectionListener;
    @Getter private PlayerInteractListener playerInteractListener;

    @Override public void onEnable() {
        RODoors.instance = this;

        saveDefaultConfig();

        this.debug = getConfig().getBoolean("debug", false);

        this.miniMessage = MiniMessage.miniMessage();
        this.adventure = BukkitAudiences.create(this);

        this.playerConnectionListener = new PlayerConnectionListener();
        this.playerInteractListener = new PlayerInteractListener();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(this.playerConnectionListener, this);
        pluginManager.registerEvents(this.playerInteractListener, this);
    }

    @Override public void onDisable() {
        HandlerList.unregisterAll(this);

        this.miniMessage = null;

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }

        RODoors.instance = null;
    }

    public CustomPlayer getPlayer(UUID player) {
        return playerConnectionListener.getPlayer(player);
    }

    public CustomPlayer getPlayer(Player player) {
        return playerConnectionListener.getPlayer(player);
    }

    public Component getMessage(String key, Collection<String> def) {
        return getMiniMessage().deserialize(Utils.toMiniMessage('\u00A7', Utils.getMessage(getConfig(), key, def)));
    }

    public BukkitAudiences getAdventure() {
        if (this.adventure == null)
            throw new IllegalStateException("adventure is null");
        return this.adventure;
    }

    public MiniMessage getMiniMessage() {
        if (this.miniMessage == null)
            throw new IllegalStateException("miniMessage is null");
        return this.miniMessage;
    }
}
