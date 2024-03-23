package staff.buildmode;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import staff.staffmode.StaffMode; // Import your main plugin class

import java.util.HashSet;
import java.util.UUID;

public class BuildCommandExecutor implements CommandExecutor {

    private final StaffMode plugin;

    public BuildCommandExecutor(StaffMode plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("utilities.build")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        UUID playerUUID = player.getUniqueId();
        HashSet<UUID> buildModePlayers = plugin.getBuildModePlayers();
        if (buildModePlayers.contains(playerUUID)) {
            // Toggle off: Player was in Build Mode, move them back to Player Mode
            buildModePlayers.remove(playerUUID);
            player.sendMessage(ChatColor.GREEN + "Build Mode deactivated.");

            // Restore the player's original inventory (implementation depends on your inventory management)
            ItemStack[] inventoryContents = plugin.getSavedBuildModeInventories().remove(playerUUID);
            if (inventoryContents != null) {
                player.getInventory().setContents(inventoryContents);
            }
        } else {
            // Toggle on: Player was in Player Mode, move them to Build Mode
            buildModePlayers.add(playerUUID);
            player.sendMessage(ChatColor.GREEN + "Build Mode activated.");

            // Save the player's current inventory and clear it
            ItemStack[] inventoryContents = player.getInventory().getContents();
            plugin.getSavedBuildModeInventories().put(playerUUID, inventoryContents);
            player.getInventory().clear();

            // Set up the build mode inventory here, if needed
        }

        return true;
    }
}