import com.ohyea777.plundercraft.api.module.AbstractModule;
import com.ohyea777.plundercraft.api.util.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class KillNetherBuilding extends AbstractModule<KillNetherBuildingConfig> implements Listener {

    private KillNetherBuildingConfig config;

    @Override
    public String getName() {
        return "Kill Nether Building";
    }

    @Override
    public Class<KillNetherBuildingConfig> getConfigClass() {
        return KillNetherBuildingConfig.class;
    }

    @Override
    public void onEnable(KillNetherBuildingConfig config) {
        super.onEnable(config);

        this.config = config;

        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @Override
    public void onReload() {
        super.onReload();

        config = reloadConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        HandlerList.unregisterAll(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onBuild(BlockPlaceEvent event) {
        event.setCancelled(!checkCanBuild(event.getPlayer(), event.getBlock()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onDestroy(BlockBreakEvent event) {
        event.setCancelled(!checkCanBuild(event.getPlayer(), event.getBlock()));
    }

    private boolean checkCanBuild(Player player, Block block) {
        if (!player.hasPermission("killnetherbuilding") && player.getWorld().getName().endsWith("_nether")) {
            int height = config.getHeight();

            if (block.getLocation().getBlockY() >= height) {
                player.sendMessage(StringUtils.format(config.getPrefix() + config.getNoBuild()));

                return false;
            }
        }

        return true;
    }

}
