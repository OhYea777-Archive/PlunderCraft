package com.ohyea777.plundercraft.api.player.network.play;

import com.ohyea777.plundercraft.api.player.network.Packet;
import com.ohyea777.plundercraft.api.util.NMSUtils;
import com.ohyea777.plundercraft.api.util.StringUtils;

public class PacketPlayOutChat implements Packet {

    private Object packetObject;

    public PacketPlayOutChat(String message) {
        this(message, ChatType.CHAT);
    }

    public PacketPlayOutChat(String message, ChatType chatType) {
        Object chatComponent = NMSUtils.createChatComponent(StringUtils.format(message));
        packetObject = NMSUtils.getNMSClass("{nms}.PacketPlayOutChat").getConstructor(NMSUtils.getNMSClass("{nms}.IChatBaseComponent"), byte.class).newInstance(chatComponent, chatType.b);
    }

    @Override
    public Object getPacketObject() {
        return packetObject;
    }

    public enum ChatType {

        CHAT((byte) 1),
        ACTION((byte) 2);

        private byte b;

        private ChatType(byte b) {
            this.b = b;
        }

    }

}
