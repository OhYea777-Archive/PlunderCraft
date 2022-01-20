package com.ohyea777.plundercraft.api.util;

import com.ohyea777.plundercraft.api.PlunderCraft;
import com.ohyea777.plundercraft.api.player.network.play.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    /**
     * Returns a formatted string given a string.
     *
     * @param string The string to be formatted.
     */
    public static String format(String string) {
        return format('&', string);
    }

    /**
     * Returns a formatted string given a string and a format code.
     *
     * @param formatCode The code to be replaced during formatting
     * @param string The string to be formatted.
     */
    public static String format(char formatCode, String string) {
        return ChatColor.translateAlternateColorCodes(formatCode, string);
    }

    public static boolean isNumber(String number) {
        try {
            Integer.valueOf(number);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int toNumber(String number) {
        try {
            return Integer.valueOf(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String toString(String[] args) {
        return toString(args, ' ');
    }

    public static String toString(int start, String[] args) {
        return toString(start, args.length, args);
    }

    public static String toString(int start, int end, String[] args) {
        return toString(Arrays.copyOfRange(args, start, end), ' ');
    }

    public static String toString(int start, String[] args, char separator) {
        return toString(start, args.length, args, separator);
    }

    public static String toString(int start, int end, String[] args, char separator) {
        return toString(Arrays.copyOfRange(args, start, end), separator);
    }

    public static String toString(String[] args, char separator) {
        StringBuilder string = new StringBuilder();

        if (args.length > 0) {
            string.append(args[0]);

            for (int i = 1; i < args.length; i ++) {
                string.append(separator).append(args[i]);
            }
        }

        return string.toString();
    }

    public static List<String> format(List<String> args) {
        return Arrays.asList(format(args.toArray(new String[args.size()])));
    }

    public static String[] format(String[] args) {
        String[] formatted = new String[args.length];

        for (int i = 0; i < args.length; i ++) {
            formatted[i] = format(args[i]);
        }

        return formatted;
    }

    public static void sendMessage(CommandSender sender, String message, PacketPlayOutChat chatPacket) {
        if (sender instanceof Player) {
            PlunderCraft.getPlayer((Player) sender).sendPacket(chatPacket);
        } else {
            sender.sendMessage(message);
        }
    }

}
