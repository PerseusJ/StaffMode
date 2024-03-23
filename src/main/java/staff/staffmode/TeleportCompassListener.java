package staff.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TeleportCompassListener implements Listener {

    private final StaffMode plugin;

    public TeleportCompassListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseCompass(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStaffModePlayers().contains(player.getUniqueId()) &&
                player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            openTeleportMenu(player);
            event.setCancelled(true);
        }
    }

    private void openTeleportMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Teleport to Player"); // Adjust size as needed
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(onlinePlayer);
                meta.setDisplayName(onlinePlayer.getName());
                head.setItemMeta(meta);
            }
            inv.addItem(head);
        }
        player.openInventory(inv);
    }
}
