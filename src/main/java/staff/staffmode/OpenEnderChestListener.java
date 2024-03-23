package staff.staffmode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OpenEnderChestListener implements Listener {

    private final StaffMode plugin;

    public OpenEnderChestListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            // This ensures the event is only triggered by the main hand action
            return;
        }

        if (!(event.getRightClicked() instanceof Player)) {
            // Ensures the entity being interacted with is a player
            return;
        }

        Player staff = event.getPlayer();
        // Check if the staff member is in "Staff Mode" and has permission
        if (!plugin.getStaffModePlayers().contains(staff.getUniqueId()) || !staff.hasPermission("utilities.staff")) {
            return;
        }

        if (staff.getInventory().getItemInMainHand().getType() == Material.ENDER_CHEST) {
            Player targetPlayer = (Player) event.getRightClicked();
            // Open the target player's Ender Chest to the staff member
            staff.openInventory(targetPlayer.getEnderChest());
            staff.sendMessage(ChatColor.GREEN + "Opened " + targetPlayer.getName() + "'s Ender Chest.");
            event.setCancelled(true); // Cancel the event to avoid default interaction behavior
        }
    }
}
