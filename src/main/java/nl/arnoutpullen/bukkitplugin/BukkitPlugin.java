package nl.arnoutpullen.bukkitplugin;

import nl.arnoutpullen.bukkitplugin.Commands.PlayerCommands;
import nl.arnoutpullen.bukkitplugin.Data.LatestPlayerLocations;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin {

    private final PluginDescriptionFile pluginDescriptionFile = this.getDescription();
    public LatestPlayerLocations latestPlayerLocations;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info( this.pluginDescriptionFile.getName() + " version " + pluginDescriptionFile.getVersion() + " is enabled!" );

        // Register data handlers
        this.latestPlayerLocations = new LatestPlayerLocations();

        // Register Commands
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(this.pluginDescriptionFile.getName() + " is disabled!");
    }

    private void registerCommands() {
        new PlayerCommands(this);
    }
}
