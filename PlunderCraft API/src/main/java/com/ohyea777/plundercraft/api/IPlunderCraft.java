package com.ohyea777.plundercraft.api;

import com.ohyea777.plundercraft.api.module.IModules;
import com.ohyea777.plundercraft.api.player.PlunderCraftPlayer;
import com.ohyea777.plundercraft.api.util.IGsonUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public interface IPlunderCraft {

    /**
     * Returns a helper class for all of the Modules.
     */
    IModules getModules();

    /**
     * Returns the {@link org.bukkit.plugin.Plugin} for MCJailed.
     */
    Plugin getPlugin();

    /**
     * Returns a helper class for creating objects from, and saving objects to, files.
     */
    IGsonUtils getGsonUtils();

    /**
     * Returns a {@link com.ohyea777.plundercraft.api.player.PlunderCraftPlayer} instance of a Bukkit {@link org.bukkit.OfflinePlayer}.
     *
     * @param player The player.
     */
    PlunderCraftPlayer getPlayer(OfflinePlayer player);

}
