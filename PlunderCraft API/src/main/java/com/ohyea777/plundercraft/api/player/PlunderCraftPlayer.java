package com.ohyea777.plundercraft.api.player;

import com.ohyea777.plundercraft.api.player.network.Packet;
import com.ohyea777.plundercraft.api.util.NMSUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlunderCraftPlayer {

    private UUID id;

    private transient OfflinePlayer player;

    public PlunderCraftPlayer(OfflinePlayer player) {
        this.id = player.getUniqueId();

        this.player = player;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public Player getPlayer() {
        return player.getPlayer();
    }

    public Object getPlayerConnection() {
        Player player = getPlayer();
        Object entityPlayer = NMSUtils.getNMSClass("{cb}.entity.CraftPlayer").getMethod("getHandle").of(player).call();

        return NMSUtils.getNMSClass("{nms}.EntityPlayer").getField("playerConnection").of(entityPlayer).get();
    }

    public void sendPacket(Packet packet) {
        if (player.isOnline()) {
            Object actualPacket = packet.getPacketObject();
            Object playerConnection = getPlayerConnection();

            NMSUtils.getNMSClass("{nms}.PlayerConnection").getMethod("sendPacket", NMSUtils.getNMSClass("{nms}.Packet")).of(playerConnection).call(actualPacket);
        }
    }

}
