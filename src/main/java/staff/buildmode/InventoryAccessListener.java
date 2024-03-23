package staff.buildmode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import staff.staffmode.StaffMode; // Ensure you import your main class correctly

public class InventoryAccessListener implements Listener {

    private final StaffMode plugin;

    public InventoryAccessListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        // Check if the inventory belongs to a chest and if the player is in Build Mode
        if (isChest(event.getInventory().getHolder()) && plugin.getBuildModePlayers().contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Access to chests is restricted in Build Mode.");
            event.setCancelled(true); // Prevent the chest from being opened
        }
    }

    private boolean isChest(InventoryHolder holder) {
        // Example check for a single chest or a double chest
        return holder instanceof org.bukkit.block.Chest || holder instanceof org.bukkit.block.DoubleChest;
    }
}
