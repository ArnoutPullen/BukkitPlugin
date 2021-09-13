package nl.arnoutpullen.bukkitplugin.Commands;

import nl.arnoutpullen.bukkitplugin.BukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class PlayerCommands {

    private final BukkitPlugin plugin;

    public PlayerCommands(BukkitPlugin plugin) {
        this.plugin = plugin;

        this.registerCommand("ping", this::ping);
    }

    /**
     * Ping Command
     * /ping
     */
    public boolean ping(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Pong!");
        return true;
    }

    private void registerCommand(String command, CommandExecutor commandExecutor) {
        PluginCommand pluginCommand = this.plugin.getCommand(command);

        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);
        }
    }
}
