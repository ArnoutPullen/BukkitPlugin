package nl.arnoutpullen.bukkitplugin.Events;

import nl.arnoutpullen.bukkitplugin.BukkitPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class DeathEventHandler implements Listener {

    private final BukkitPlugin plugin;

    public DeathEventHandler(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent playerDeath) {
        // Get player data
        Player player = playerDeath.getEntity();
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();

        // Update player the latest location
        this.plugin.latestPlayerLocations.insertOrUpdate(uuid, location);

        // Update death counter
        int deathCounter = persistentDataContainer.get(new NamespacedKey(this.plugin, "deathCounter"), PersistentDataType.INTEGER);
        deathCounter++;
        player.sendMessage(ChatColor.DARK_RED + "You died " + deathCounter + " times already, git gud scrub!");
        player.getPersistentDataContainer().set(new NamespacedKey(this.plugin, "deathCounter"), PersistentDataType.INTEGER, deathCounter);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();

        // Initialize deathCounter
        if (!player.getPersistentDataContainer().has(new NamespacedKey(this.plugin, "deathCounter"), PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().set(new NamespacedKey(this.plugin, "deathCounter"), PersistentDataType.INTEGER, 0);
        }

        // Update player the latest location
        this.plugin.latestPlayerLocations.insertOrUpdate(uuid, location);
    }
}