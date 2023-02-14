package me.gstudiosx.rodoors.listeners.player;

import lombok.Getter;
import me.gstudiosx.rodoors.RODoors;
import me.gstudiosx.rodoors.Utils;
import me.gstudiosx.rodoors.api.http.HttpRequest;
import me.gstudiosx.rodoors.api.player.CustomPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {
    private static final String PACK_ZIP = "https://raw.githubusercontent.com/%s/%s/build/Pack.zip";
    private static final String HASH = "https://raw.githubusercontent.com/%s/%s/build/hash.txt";

    public static final String repo;
    public static final String branch;

    public static final byte[] hash;
    public static final String packUrl;

    static {
        Configuration config = RODoors.getInstance().getConfig();

        repo = config.getString("repo", "RoDoors/TexturePack");
        branch = config.getString("branch", "main");

        String hashString = config.getString("resource_pack.hash", HASH).formatted(repo, branch);
        if (hashString.startsWith("http") && hashString.contains("://")) {
            hashString = new HttpRequest(hashString.formatted(repo, branch)).get().split(" ")[0];
        }

        hash = Utils.hexBytes(hashString);
        packUrl = config.getString("resource_pack.url", PACK_ZIP).formatted(repo, branch);
    }

    @Getter private final Map<UUID, CustomPlayer> players = new HashMap<>();

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event) {
        Configuration configuration = RODoors.getInstance().getConfig();

        CustomPlayer player = new CustomPlayer(event.getPlayer());
        players.put(player.getUniqueId(), player);

        if (configuration.getBoolean("showcase", false)) {
            Component welcome = RODoors.getInstance().getMiniMessage().deserialize("""
                <red>Welcome to the Showcase.</red>
                                
                <gray>At the moment only the items are showcased here I will probably make this area accessible, by beating the game in the future and this area would probably also contain the jumpscares and other things.</gray>
                
                <white><bold>This area is Unfinished</bold></white> <gray>and none of the game is complete yet and only some items have been made.</gray>
                                
                <gray>Im hoping to get some people to help me make better models/builds.</gray>
                <gray>But progress is going well ! and im trying to get this released as soon as possible.</gray>
                                
                <white><bold>Thank you for playing.</bold></white>
                """);
            player.sendMessage(welcome);

            Component title = RODoors.getInstance().getMiniMessage().deserialize("<red>Welcome to the Showcase</red>");
            player.sendInstantTitle(Title.title(title, Component.empty()), Duration.ofSeconds(10));
        }

        if (packUrl != null) {
            player.setResourcePack(packUrl, hash, configuration.getBoolean("showcase", false) ?
                String.join("\n", new String[]{
                    "§cWelcome to the showcase room.",
                    "§7You need to accept the pack to see the custom models."
                }) : String.join("\n", new String[]{
                    "§cWelcome to RODoors",
                    "", "§7Roblox Doors in Minecraft!",
                    "§7Please Accept and Download this Resource Pack", "",
                    "§c(WARNING you can still play without resource pack but it will be better with the resource pack)"
                }));
        }
    }

    @EventHandler public void onPlayerQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {
        CustomPlayer player = getPlayer(event.getPlayer());

        if (player != null) {
            player.setHasResourcePack(event.getStatus() != PlayerResourcePackStatusEvent.Status.DECLINED
                    && event.getStatus() != PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD);
        }
    }

    public CustomPlayer getPlayer(Player player) {
        if (player == null)
            return null;
        return getPlayer(player.getUniqueId());
    }

    public CustomPlayer getPlayer(UUID player) {
        if (player == null)
            return null;

        if (!players.containsKey(player)) {
            CustomPlayer p = new CustomPlayer(player);
            players.put(player, p);
            return p;
        }
        
        return players.get(player);
    }
}
