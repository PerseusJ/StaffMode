package staff.staffmode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OpenInventoryListener implements Listener {

    private final StaffMode plugin;

    public OpenInventoryListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            // Ensure the action is performed with the main hand.
            return;
        }

        if (!(event.getRightClicked() instanceof Player)) {
            // Ensure the entity being interacted with is a player.
            return;
        }

        Player staff = event.getPlayer();
        // Check if the staff member is in "Staff Mode" and has the appropriate permission.
        if (!plugin.getStaffModePlayers().contains(staff.getUniqueId()) || !staff.hasPermission("utilities.staff")) {
            return;
        }

        if (staff.getInventory().getItemInMainHand().getType() == Material.CHEST) {
            Player targetPlayer = (Player) event.getRightClicked();
            // Open the target player's inventory for the staff member.
            staff.openInventory(targetPlayer.getInventory());
            event.setCancelled(true); // Cancel the event to prevent default interaction behavior.
        }
    }
}

