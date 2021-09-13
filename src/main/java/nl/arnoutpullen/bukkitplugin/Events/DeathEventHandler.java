package nl.arnoutpullen.bukkitplugin.Events;

import nl.arnoutpullen.bukkitplugin.BukkitPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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

        // Update player the latest location
        this.plugin.latestPlayerLocations.insertOrUpdate(uuid, location);
        this.plugin.getLogger().info("updated location of " + player.getName());
    }
}