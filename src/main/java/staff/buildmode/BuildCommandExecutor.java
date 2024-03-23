package staff.buildmode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import staff.staffmode.StaffMode; // Import the main class

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BuildCommandExecutor implements CommandExecutor {

    private final StaffMode plugin;
    private final HashSet<UUID> buildModePlayers = new HashSet<>();
    private final HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();

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
        if (buildModePlayers.contains(playerUUID)) {
            // Toggle off: Player was in Build Mode, move them back to Player Mode
            buildModePlayers.remove(playerUUID);
            player.sendMessage(ChatColor.GREEN + "Build Mode deactivated.");

            // Restore the player's original inventory
            ItemStack[] inventoryContents = savedInventories.remove(playerUUID);
            if (inventoryContents != null) {
                player.getInventory().setContents(inventoryContents);
            }
        } else {
            // Toggle on: Player was in Player Mode, move them to Build Mode
            buildModePlayers.add(playerUUID);
            player.sendMessage(ChatColor.GREEN + "Build Mode activated.");

            // Save the player's current inventory and clear it
            ItemStack[] inventoryContents = player.getInventory().getContents();
            savedInventories.put(playerUUID, inventoryContents);
            player.getInventory().clear();

            // Set up the build mode inventory here, if needed
        }

        return true;
    }
}
