package staff.staffmode;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.UUID;

public class SpectatorModeListener implements Listener {

    private final StaffMode plugin;

    public SpectatorModeListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseFeather(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getStaffModePlayers().contains(player.getUniqueId()) &&
                player.getInventory().getItemInMainHand().getType() == Material.FEATHER) {
            UUID playerUUID = player.getUniqueId();
            if (player.getGameMode() != GameMode.SPECTATOR) {
                // Store the current game mode before switching
                plugin.getOriginalGameModes().put(playerUUID, player.getGameMode());
                // Switch to Spectator mode
                player.setGameMode(GameMode.SPECTATOR);
            } else {
                // Restore the original game mode, default to SURVIVAL if not found
                GameMode originalGameMode = plugin.getOriginalGameModes().getOrDefault(playerUUID, GameMode.SURVIVAL);
                player.setGameMode(originalGameMode);
                // Optionally, remove the player from the map if you're done
                plugin.getOriginalGameModes().remove(playerUUID);
            }
            event.setCancelled(true);
        }
    }
}