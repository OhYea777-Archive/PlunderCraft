package com.ohyea777.plundercraft.api;

import com.ohyea777.plundercraft.api.module.IModules;
import com.ohyea777.plundercraft.api.player.PlunderCraftPlayer;
import com.ohyea777.plundercraft.api.util.IGsonUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class PlunderCraft {

    private static IPlunderCraft instance;

    /**
     * Returns a helper class for all of the Modules.
     */
    public static IModules getModules() {
        return instance.getModules();
    }

    /**
     * Returns the {@link org.bukkit.plugin.Plugin} for MCJailed.
     */
    public static Plugin getPlugin() {
        return instance.getPlugin();
    }

    /**
     * Returns a helper class for creating objects from, and saving objects to, files.
     */
    public static IGsonUtils getGsonUtils() {
        return instance.getGsonUtils();
    }

    /**
     * Returns a {@link com.ohyea777.plundercraft.api.player.PlunderCraftPlayer} instance of a Bukkit {@link org.bukkit.OfflinePlayer}.
     *
     * @param player The player.
     */
    public static PlunderCraftPlayer getPlayer(OfflinePlayer player) {
        return instance.getPlayer(player);
    }

    /**
     * Returns an instance of MCJailed.
     */
    public static IPlunderCraft getPlunderCraft() {
        return instance;
    }

    /**
     * Sets the instance for MCJailed.
     *
     * @param instance The instance of the plugin.
     */
    public static void setPlunderCraft(IPlunderCraft instance) {
        PlunderCraft.instance = instance;
    }

}
