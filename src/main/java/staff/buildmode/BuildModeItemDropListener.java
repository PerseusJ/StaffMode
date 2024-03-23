package staff.buildmode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import staff.staffmode.StaffMode; // Make sure this imports your main plugin class correctly

public class BuildModeItemDropListener implements Listener {

    private final StaffMode plugin;

    public BuildModeItemDropListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (plugin.getBuildModePlayers().contains(event.getPlayer().getUniqueId())) {
            // Cancel the drop event to prevent the player from dropping the item
            event.setCancelled(true);
            event.getPlayer().sendMessage(org.bukkit.ChatColor.RED + "Dropping items is disabled in Build Mode.");
        }
    }
}