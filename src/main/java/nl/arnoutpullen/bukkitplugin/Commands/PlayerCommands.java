package nl.arnoutpullen.bukkitplugin.Commands;

import nl.arnoutpullen.bukkitplugin.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

        this.registerCommand("up", this::teleportPlayerUp);
        this.registerCommand("online", this::online);
        this.registerCommand("enderchest", this::openEnderChest);
        this.registerCommand("heal", this::healPlayer);
        this.registerCommand("msg", this::sendPlayerDirectMessage);
        this.registerCommand("ping", this::ping);
    }

    /**
     * Teleport player up
     * /up [1-256]
     * /up [player] [1-256]
     */
    public boolean teleportPlayerUp(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            int y = 1;
            Player player = (Player) sender;
            Location location = player.getLocation();

            if (args.length == 1) {
                y = Integer.parseInt(args[0]);
            }

            if (args.length == 2) {
                y = Integer.parseInt(args[0]);
                String username = args[1];
                player   = this.plugin.getServer().getPlayer(username);
            }
            if (player == null) {
                return false;
            }
            // Prevent player from spawning too high
            double playerY   = location.getY();
            double maxHeight = player.getWorld().getMaxHeight();

            // Change y to max height
            if ((playerY + y) > maxHeight) {
                y = (int)maxHeight;
            }

            // Prevent player from spawning too low
            if (y < 1) {
                y = 1;
            }

            // todo permission check

            // Teleport player to Y
            location.setY(location.getY() + y);
            return player.teleport(location);
        } else {
            sender.sendMessage("Can't move you!");
            return false;
        }
    }

    /**
     * Display in chat which users are online
     * /online
     */
    public boolean online(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            sender.sendMessage(player.getName() + ", The following players are online");

            for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
                Location location = player1.getLocation();

                sender.sendMessage(player1.getName());
                sender.sendMessage("X: " + location.getBlockX() + " Y:" + location.getBlockY() + " Z:" + location.getBlockZ());
            }
        } else {
            sender.sendMessage("You must be a player!");
            return false;
        }

        if (cmd.getName().equalsIgnoreCase("online")) {
            // Players online
            plugin.getLogger().info("The following players are online:");
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                plugin.getLogger().info(player.getName());
            }
            return true;
        }

        return false;
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
