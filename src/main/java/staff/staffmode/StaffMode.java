package staff.staffmode;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.GameMode;
import org.bukkit.Bukkit;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public final class StaffMode extends JavaPlugin {

    private final HashSet<UUID> staffModePlayers = new HashSet<>();
    private final HashSet<UUID> frozenPlayers = new HashSet<>();
    private final HashMap<UUID, GameMode> originalGameModes = new HashMap<>();
    private final HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();
    private final HashSet<UUID> buildModePlayers = new HashSet<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("staff").setExecutor(new StaffCommandExecutor(this, staffModePlayers, savedInventories));
        this.getCommand("build").setExecutor(new staff.buildmode.BuildCommandExecutor(this));
        getServer().getPluginManager().registerEvents(new VanishListener(this), this);
        getServer().getPluginManager().registerEvents(new OpenEnderChestListener(this), this);
        getServer().getPluginManager().registerEvents(new OpenInventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezePlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMovementListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportCompassListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportMenuInteractionListener(this), this);
        getServer().getPluginManager().registerEvents(new SpectatorModeListener(this), this);
        getServer().getPluginManager().registerEvents(new staff.buildmode.InventoryAccessListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<UUID, GameMode> getOriginalGameModes() {
        return originalGameModes;
    }
    public HashSet<UUID> getStaffModePlayers() {
        return staffModePlayers;
    }
    public HashSet<UUID> getFrozenPlayers() {
        return frozenPlayers;
    }
    public HashMap<UUID, ItemStack[]> getSavedInventories() {
        return savedInventories;
    }
    public HashSet<UUID> getBuildModePlayers() {
        return buildModePlayers;
    }
}

