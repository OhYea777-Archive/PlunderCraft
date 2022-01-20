import com.ohyea777.plundercraft.api.module.AbstractModule;
import com.ohyea777.plundercraft.api.module.Cmd;
import com.ohyea777.plundercraft.api.util.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class TotalMute extends AbstractModule<TotalMuteConfig> implements Listener {

    private TotalMuteConfig config;
    private Map<UUID, TotalMutePlayer> mutedPlayers = new HashMap<UUID, TotalMutePlayer>();

    @Override
    public String getName() {
        return "Total Mute";
    }

    @Override
    public Class getConfigClass() {
        return TotalMuteConfig.class;
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> databaseClasses = new ArrayList<Class<?>>();

        databaseClasses.add(TotalMutePlayer.class);

        return databaseClasses;
    }

    @Override
    public void onEnable(TotalMuteConfig config) {
        super.onEnable(config);

        this.config = config;

        loadMutedPlayers();
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
        registerCommandHandler(this);
    }

    @Override
    public void onReload() {
        super.onReload();

        config = reloadConfig();

        loadMutedPlayers();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        HandlerList.unregisterAll(this);
        deregisterCommandHandler(this);
    }

    private void loadMutedPlayers() {
        mutedPlayers.clear();

        for (TotalMutePlayer player : getPlugin().getDatabase().find(TotalMutePlayer.class).findList()) {
            mutedPlayers.put(player.getId(), player);
        }
    }

    @Cmd(value = "totalmute", aliases = { "tmute" })
    public boolean onTotalMute(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("totalmute.mute")) {
            if (args.length >= 1) {
                Timestamp timestamp = null;
                OfflinePlayer player = null;

                if (args.length > 1) timestamp = toTimestamp(Arrays.copyOfRange(args, 1, args.length));

                try {
                    player = getPlugin().getServer().getOfflinePlayer(UUID.fromString(args[0]));
                } catch (Exception e) {
                    player = getPlugin().getServer().getOfflinePlayer(args[0]);
                }

                if (player != null && player.hasPlayedBefore()) {
                    if (!mutedPlayers.containsKey(player.getUniqueId())) {
                        if (!player.getPlayer().hasPermission("totalmute.exempt")) {
                            TotalMutePlayer totalMutePlayer = timestamp != null ? new TotalMutePlayer(player, timestamp) : new TotalMutePlayer(player);

                            getPlugin().getDatabase().save(totalMutePlayer);
                            mutedPlayers.put(player.getUniqueId(), totalMutePlayer);

                            if (player.isOnline()) player.getPlayer().sendMessage(StringUtils.format(config.getPrefix() + config.getMuted()));

                            sender.sendMessage(StringUtils.format(config.getPrefix() + config.getMutedPlayer().replace("{player}", player.getName()).replace("{timestamp}", timestamp != null ? new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp) : "forever")));
                        } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getCanNotMute()));
                    } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getAlreadyMuted()));
                } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getInvalidPlayer()));
            } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getInvalidArgs()));
        } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getNoPerm()));

        return true;
    }

    @Cmd(value = "totalunmute", aliases = { "tunmute" })
    public boolean onTotalUnMute(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("totalmute.unmute")) {
            if (args.length == 1) {
                OfflinePlayer player = null;

                try {
                    player = getPlugin().getServer().getOfflinePlayer(UUID.fromString(args[0]));
                } catch (Exception e) {
                    player = getPlugin().getServer().getOfflinePlayer(args[0]);
                }

                if (player != null && player.hasPlayedBefore()) {
                    if (mutedPlayers.containsKey(player.getUniqueId())) {
                        TotalMutePlayer totalMutePlayer = mutedPlayers.remove(player.getUniqueId());

                        getPlugin().getDatabase().delete(totalMutePlayer);

                        if (player.isOnline()) player.getPlayer().sendMessage(StringUtils.format(config.getPrefix() + config.getUnMuted()));

                        sender.sendMessage(StringUtils.format(config.getPrefix() + config.getUnMutedPlayer().replace("{player}", player.getName())));
                    } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getNotMuted()));
                } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getInvalidPlayer()));
            } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getInvalidArgs()));
        } else sender.sendMessage(StringUtils.format(config.getPrefix() + config.getNoPerm()));

        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (config.muteFromChat() && !event.getPlayer().hasPermission("totalmute.exempt")) {
            if (mutedPlayers.containsKey(event.getPlayer().getUniqueId())) {
                TotalMutePlayer player = mutedPlayers.get(event.getPlayer().getUniqueId());

                if (player.checkMute()) {
                    mutedPlayers.remove(player.getId());
                    getPlugin().getDatabase().delete(player);
                } else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(StringUtils.format(config.getPrefix() + config.getNoChat()));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (config.getBlockedCommands() != null && config.getBlockedCommands().size() > 0 && !event.getPlayer().hasPermission("totalmute.exempt")) {
            String[] args = event.getMessage().replace("/", "").split(" ");

            if (args.length >= 1) {
                String command = args[0];

                if (config.getBlockedCommands().contains(command.toLowerCase())) {
                    if (mutedPlayers.containsKey(event.getPlayer().getUniqueId())) {
                        TotalMutePlayer player = mutedPlayers.get(event.getPlayer().getUniqueId());

                        if (player.checkMute()) {
                            mutedPlayers.remove(player.getId());
                            getPlugin().getDatabase().delete(player);
                        } else {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(StringUtils.format(config.getPrefix() + config.getBlockedCommand()));
                        }
                    }
                }
            }
        }
    }

    private Timestamp toTimestamp(String[] args) {
        Timestamp timestamp = null;
        int secs = 0, mins = 0, hrs = 0, days = 0, months = 0, years = 0;

        for (String arg : args) {
            if (arg.endsWith("s")) {
                arg = arg.replace("s", "");

                if (StringUtils.isNumber(arg)) {
                    secs = StringUtils.toNumber(arg);
                }
            } else if (arg.endsWith("m")) {
                arg = arg.replace("m", "");

                if (StringUtils.isNumber(arg)) {
                    mins = StringUtils.toNumber(arg);
                }
            } else if (arg.endsWith("h")) {
                arg = arg.replace("h", "");

                if (StringUtils.isNumber(arg)) {
                    hrs = StringUtils.toNumber(arg);
                }
            } else if (arg.endsWith("d")) {
                arg = arg.replace("d", "");

                if (StringUtils.isNumber(arg)) {
                    days = StringUtils.toNumber(arg);
                }
            } else if (arg.endsWith("M")) {
                arg = arg.replace("M", "");

                if (StringUtils.isNumber(arg)) {
                    months = StringUtils.toNumber(arg);
                }
            } else if (arg.endsWith("y")) {
                arg = arg.replace("y", "");

                if (StringUtils.isNumber(arg)) {
                    years = StringUtils.toNumber(arg);
                }
            }
        }

        if (secs != 0 || mins != 0 || hrs != 0 || days != 0 || months != 0 || years != 0) {
            timestamp = new Timestamp(new Date().getTime());
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(timestamp.getTime());
            calendar.add(Calendar.SECOND, secs);
            calendar.add(Calendar.MINUTE, mins);
            calendar.add(Calendar.HOUR, hrs);
            calendar.add(Calendar.DATE, days);
            calendar.add(Calendar.MONTH, months);
            calendar.add(Calendar.YEAR, years);

            return new Timestamp(calendar.getTime().getTime());
        }

        return null;
    }

}
