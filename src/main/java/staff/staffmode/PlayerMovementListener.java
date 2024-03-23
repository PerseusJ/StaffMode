package staff.staffmode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {

    private final StaffMode plugin;

    public PlayerMovementListener(StaffMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            // Cancel the move event if the player is frozen
            event.setCancelled(true);
        }
    }
}
