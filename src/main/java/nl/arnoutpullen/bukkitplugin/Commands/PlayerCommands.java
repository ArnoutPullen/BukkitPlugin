package nl.arnoutpullen.bukkitplugin.Commands;

import nl.arnoutpullen.bukkitplugin.BukkitPlugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.Arrays;

public class PlayerCommands {

    private final BukkitPlugin plugin;

    public PlayerCommands(BukkitPlugin plugin) {
        this.plugin = plugin;

        this.registerCommand("enderchest", this::openEnderChest);
        this.registerCommand("heal", this::healPlayer);
        this.registerCommand("msg", this::sendPlayerDirectMessage);
        this.registerCommand("ping", this::ping);
    }

    /**
     * Open enderchest of player
     * /enderchest
     * /enderchest Player
     */
    public boolean openEnderChest(CommandSender commandSender, Command cmd, String label, String[] args) {
        // Get player
        Player player = this.plugin.getServer().getPlayer(commandSender.getName());
        if (player == null) {
            return false;
        }

        // Get player enderchest
        Inventory inventory = player.getEnderChest();

        // Open enderchest of user
        if (args.length == 1) {
            String username = args[0];
            // Get player by username
            player = this.plugin.getServer().getPlayer(username);

            if (player == null) {
                commandSender.sendMessage("User not found");
                return false;
            }

            // todo permission check
        }

        // Open EnderChest
        InventoryView inv = player.openInventory(inventory);

        return inv != null;
    }

    /**
     * Heal commandSender or Player
     * /heal
     * /heal Player
     */
    public boolean healPlayer(CommandSender commandSender, Command cmd, String label, String[] args) {
        Player player = this.plugin.getServer().getPlayer(commandSender.getName());

        if (player == null) {
            return false;
        }

        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute == null) {
            return false;
        }
        double healing = attribute.getDefaultValue();

        if (args.length == 1) {
            String username = args[0];
            player = this.plugin.getServer().getPlayer(username);
        }

        if (player == null) {
            return false;
        }

        player.setHealth(healing);
        return true;
    }

    /**
     * Send message directly to user
     * /msg username message
     * */
    public boolean sendPlayerDirectMessage(CommandSender commandSender, Command cmd, String label, String[] args) {

        if (args.length > 2) {
            // Get username
            String username = args[0];
            // Combine all args to string except username
            String message  = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            Player player   = this.plugin.getServer().getPlayer(username);

            if (player == null) {
                commandSender.sendMessage("User not found");
                return false;
            }

            if (!player.isOnline()) {
                commandSender.sendMessage("User not online");
                return false;
            }

            player.sendMessage(message);
            return true;
        }

        return false;
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
