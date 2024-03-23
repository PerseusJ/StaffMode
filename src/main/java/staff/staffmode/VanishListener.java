package staff.staffmode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VanishListener implements Listener {

    private final StaffMode plugin;

    public VanishListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // Check if the player is in Staff Mode
        if (plugin.getStaffModePlayers().contains(player.getUniqueId())) {
            ItemStack item = event.getItem();
            if (item != null && (item.getType() == Material.STONE || item.getType() == Material.GLASS)) {
                if (item.getType() == Material.STONE) {
                    // Make the player invisible
                    for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
                        otherPlayer.hidePlayer(plugin, player);
                    }
                    player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS));
                    player.sendMessage(ChatColor.GREEN + "You are now invisible to other players.");
                } else if (item.getType() == Material.GLASS) {
                    // Make the player visible
                    for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
                        otherPlayer.showPlayer(plugin, player);
                    }
                    player.getInventory().setItemInMainHand(new ItemStack(Material.STONE));
                    player.sendMessage(ChatColor.GREEN + "You are now visible to other players.");
                }
                event.setCancelled(true); // Prevent the usual item use
            }
        }
    }
}

