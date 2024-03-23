package staff.staffmode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import java.util.UUID;

public class FreezePlayerListener implements Listener {

    private final StaffMode plugin;

    public FreezePlayerListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        Player staff = event.getPlayer();
        if (!plugin.getStaffModePlayers().contains(staff.getUniqueId()) || !staff.hasPermission("utilities.staff")) {
            return;
        }

        if (staff.getInventory().getItemInMainHand().getType() == Material.ICE) {
            Player targetPlayer = (Player) event.getRightClicked();
            UUID targetUUID = targetPlayer.getUniqueId();
            if (plugin.getFrozenPlayers().contains(targetUUID)) {
                // Unfreeze the player
                plugin.getFrozenPlayers().remove(targetUUID);
                staff.sendMessage("Player " + targetPlayer.getName() + " has been unfrozen.");
                targetPlayer.sendMessage("You have been unfrozen.");
            } else {
                // Freeze the player
                plugin.getFrozenPlayers().add(targetUUID);
                staff.sendMessage("Player " + targetPlayer.getName() + " has been frozen.");
                targetPlayer.sendMessage("You have been frozen.");
            }
            event.setCancelled(true);
        }
    }
}
