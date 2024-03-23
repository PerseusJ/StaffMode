package staff.staffmode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class StaffCommandExecutor implements CommandExecutor {

    private final StaffMode plugin;
    private final HashSet<UUID> staffModePlayers;
    private final HashMap<UUID, ItemStack[]> savedInventories;

    public StaffCommandExecutor(StaffMode plugin, HashSet<UUID> staffModePlayers, HashMap<UUID, ItemStack[]> savedInventories) {
        this.plugin = plugin;
        this.staffModePlayers = staffModePlayers;
        this.savedInventories = savedInventories;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("utilities.staff")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        UUID playerUUID = player.getUniqueId();
        if (staffModePlayers.contains(playerUUID)) {
            // Toggle off: Player was in Staff Mode, move them back to Player Mode
            staffModePlayers.remove(playerUUID);
            player.sendMessage(ChatColor.GREEN + "You have been toggled off Staff Mode.");

            // Restore the player's inventory
            ItemStack[] inventoryContents = savedInventories.remove(playerUUID);
            if (inventoryContents != null) {
                player.getInventory().setContents(inventoryContents);
            }

            // Additional code for making the player visible again (if you implemented the vanish feature)
            // and other necessary cleanup could go here.

        } else {
            // Toggle on: Player was in Player Mode, move them to Staff Mode
            staffModePlayers.add(playerUUID);
            player.sendMessage(ChatColor.GREEN + "You have been toggled into Staff Mode.");

            // Save the player's current inventory and clear it
            ItemStack[] inventoryContents = player.getInventory().getContents();
            savedInventories.put(playerUUID, inventoryContents);
            player.getInventory().clear();

            // Equip the player with the stone for vanish and Ender Chest for accessing other players' Ender Chests
            player.getInventory().addItem(new ItemStack(Material.STONE)); // For the vanish feature
            player.getInventory().addItem(new ItemStack(Material.ENDER_CHEST)); // For the Open Ender Chest feature
            player.getInventory().addItem(new ItemStack(Material.CHEST)); // For the Open Inventory feature
            player.getInventory().addItem(new ItemStack(Material.ICE)); // For the Freeze player feature
            player.getInventory().addItem(new ItemStack(Material.COMPASS)); // For the Tp player feature
            player.getInventory().addItem(new ItemStack(Material.FEATHER)); // Give the Feather for Spectator mode

        }

        return true;
    }
}

