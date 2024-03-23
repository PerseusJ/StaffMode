package staff.buildmode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import staff.staffmode.StaffMode; // Make sure to import your main class

public class BuildModeChestAccessListener implements Listener {

    private final StaffMode plugin;

    public BuildModeChestAccessListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        // Check if the entity that opened the inventory is a player
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        // Check if the inventory belongs to a chest (or other container types if you wish)
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof org.bukkit.block.Chest || holder instanceof org.bukkit.block.ShulkerBox) { // Add or remove container types as needed
            // Check if the player is in Build Mode
            if (plugin.getBuildModePlayers().contains(player.getUniqueId())) {
                // Cancel the event, preventing the chest from being opened
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Access to chests is restricted in Build Mode.");
            }
        }
    }
}
