package me.gstudiosx.rodoors.api.player;


import lombok.Getter;
import lombok.Setter;
import me.gstudiosx.rodoors.RODoors;
import me.gstudiosx.rodoors.Utils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;

public class CustomPlayer {
    private final UUID player;
    @Getter private final Audience audience;
    @Getter @Setter private boolean hasResourcePack = false;

    public CustomPlayer(Player player) {
        this(player.getUniqueId());
    }

    public CustomPlayer(UUID player) {
        if (Bukkit.getPlayer(player) == null) {
            throw new IllegalArgumentException("Not a valid Player");
        }

        this.player = player;
        this.audience = RODoors.getInstance().getAdventure().player(this.player);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public UUID getUniqueId() {
        return player;
    }

    // for interacting with entities/breaking blocks/placing blocks/...
    public boolean canInteract() {
        Player player = getPlayer();
        if (player == null) return false;

        if (player.getGameMode() != GameMode.CREATIVE) return false;
        String permission = RODoors.getInstance().getConfig().getString("permissions.interaction", "");
        return permission.isEmpty() || player.hasPermission(permission);
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    public void sendMessage(ComponentLike component) {
        getAudience().sendMessage(component);
    }

    public void sendMessage(Component component) {
        getAudience().sendMessage(component);
    }

    public void clearTitle() {
        getAudience().clearTitle();
    }

    public void showTitle(Title title) {
        if (title != null) {
            getAudience().showTitle(title);
        }
    }

    public void sendTitle(Title title) {
        showTitle(title);
    }

    public void sendInstantTitle(Title title, Duration stay) {
        if (title == null) throw new NullPointerException("title is null");
        if (stay == null) throw new NullPointerException("stay is null");
        Title.Times times = Title.Times.times(Duration.ZERO, stay, Duration.ZERO);
        showTitle(Title.title(title.title(), title.subtitle(), times));
    }

    public void sendInstantTitleTicks(String title, String subtitle, int ticks) {
        getPlayer().sendTitle(title, subtitle, 0, ticks, 0);
    }

    public void sendInstantTitle(String title, String subtitle, int seconds) {
        sendInstantTitleTicks(title, subtitle, seconds * 20);
    }

    public void setResourcePack(String url, byte[] hash) {
        if (RODoors.getInstance().isDebug())
            RODoors.getInstance().getLogger().info("§e[DEBUG]§f URL: " + url + ", hash: " + Arrays.toString(hash));
        getPlayer().setResourcePack(url, hash);
    }

    public void setResourcePack(String url, byte[] hash, String prompt) {
        if (RODoors.getInstance().isDebug())
            RODoors.getInstance().getLogger().info("§e[DEBUG]§f URL: " + url + ", hash: " + Arrays.toString(hash) + ", prompt: " + prompt);
        getPlayer().setResourcePack(url, hash, prompt);
    }

    public void setResourcePack(String url, String hash) {
        setResourcePack(url, Utils.hexBytes(hash));
    }

    public void setResourcePack(String url, String hash, String prompt) {
        setResourcePack(url, Utils.hexBytes(hash), prompt);
    }
}
