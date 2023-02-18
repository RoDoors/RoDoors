package me.gstudiosx.rodoors;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class RODoors extends JavaPlugin {
    @Getter 
    private BukkitAudiences adventure;

    @Override 
    public void onEnable() {
        saveDefaultConfig();
        this.adventure = BukkitAudiences.create(this);
    }

    @Override 
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
