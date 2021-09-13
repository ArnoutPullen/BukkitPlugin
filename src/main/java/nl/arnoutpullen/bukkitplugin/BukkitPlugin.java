package nl.arnoutpullen.bukkitplugin;

import nl.arnoutpullen.bukkitplugin.Commands.PlayerCommands;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlugin extends JavaPlugin {

    private final PluginDescriptionFile pluginDescriptionFile = this.getDescription();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info( this.pluginDescriptionFile.getName() + " version " + pluginDescriptionFile.getVersion() + " is enabled!" );
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
