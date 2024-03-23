package staff.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TeleportMenuInteractionListener implements Listener {

    private final StaffMode plugin;

    // Constructor that accepts StaffMode instance
    public TeleportMenuInteractionListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Teleport to Player")) {
            event.setCancelled(true); // Prevent taking items

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() != Material.PLAYER_HEAD) return;

            SkullMeta meta = (SkullMeta) clickedItem.getItemMeta();
            if (meta.getOwningPlayer() != null) { // Adjusted check for older API versions
                Player player = (Player) event.getWhoClicked();
                Player target = Bukkit.getPlayer(meta.getOwningPlayer().getUniqueId());
                if (target != null) { // Ensure the target player is still online
                    player.teleport(target);
                    player.closeInventory();
                }
            }
        }
    }
}