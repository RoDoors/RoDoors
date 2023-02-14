package me.gstudiosx.rodoors;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class RODoors extends JavaPlugin {
    @Getter private BukkitAudiences adventure;
    @Getter private MiniMessage miniMessage;

    @Override public void onEnable() {
        saveDefaultConfig();
        this.miniMessage = MiniMessage.miniMessage();
        this.adventure = BukkitAudiences.create(this);
    }

    @Override public void onDisable() {
        this.miniMessage = null;

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
