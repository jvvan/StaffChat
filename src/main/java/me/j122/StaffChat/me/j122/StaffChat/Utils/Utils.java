package me.j122.StaffChat.me.j122.StaffChat.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.j122.StaffChat.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Utils {
    public static void reload() {
        main.plugin.reloadConfig();
        main.plugin.loadYamls();
    }
    public static void ConsoleMessage(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(Utils.Chat(msg));
    }
    public static String Chat(String msg) {
        String s = ChatColor.translateAlternateColorCodes('&', msg);
        return s;
    }
    public static String ChatWithPlaceholders(Player p, String msg) {
        String s = PlaceholderAPI.setPlaceholders(p,Chat(msg));
        return s;
    }
    public static String Format_(String format, Player p, String msg) {
        return format
                .replaceAll("\\{channel}", getPlayersChannel(p))
                .replaceAll("\\{world}", p.getWorld().getName())
                .replaceAll("\\{username}", p.getName())
                .replaceAll("\\{displayname}", p.getDisplayName())
                .replaceAll("\\{message}", msg);
    }
    public static String Format(String format, Player p, String msg) {
        if(main.placeholders) return ChatWithPlaceholders(p, Format_(format, p, msg));
        else return Chat(Format_(format,p,msg));
    }
    public static void SendStaffChatMessage(Player p, String msg) {
        if(!channelExists(getPlayersChannel(p))) {
            setPlayersChannel(p, getDefaultChannel());
            p.sendMessage(Utils.getMessage("player-kicked-from-channel"));
        }
        String scmsg = Format(main.plugin.getConfig().getString("format"), p, msg);
        if(getPlayersChannel(p) == getDefaultChannel()) {
            Bukkit.broadcast(scmsg, "staffchat.view");
        } else {
            Bukkit.broadcast(scmsg, "staffchat.channels."+getPlayersChannel(p).toLowerCase()+".view");
        }
    }

    public static void setPlayersChannel(Player p, String channel) {
        main.plugin.getPlayerChannels().put(p.getUniqueId(), channel);
    }
    public static void setDefaultChannel(String Channel) {
        if(!getChannels().contains(Channel.toLowerCase())) return;
        main.plugin.getConfig().set("default_channel", Channel.toLowerCase());
        main.plugin.saveConfig();
        main.plugin.reloadConfig();
    }
    public static boolean channelExists(String channel) {
        return getChannels().contains(channel);
    }
    public static boolean playerExistsInMap(Player p) {
        return main.plugin.getPlayerChannels().containsKey(p.getUniqueId());
    }
    public static String getPlayersChannel(Player p) {
        if (!playerExistsInMap(p)) setPlayersChannel(p, getDefaultChannel());
        return main.plugin.getPlayerChannels().get(p.getUniqueId()).toString();
    }
    public static List<String> getChannels() {
        return main.plugin.getConfig().getStringList("channels");
    }
    public static void addChannel(String Channel) {
        List<String> list = main.plugin.getConfig().getStringList("channels");
        list.add(Channel.toLowerCase());
        main.plugin.getConfig().set("channels", list);
        main.plugin.saveConfig();
        main.plugin.reloadConfig();
    }
    public static void removeChannel(String Channel) {
        if(Channel.toLowerCase().equalsIgnoreCase(getDefaultChannel().toLowerCase())) return;
        List<String> list = main.plugin.getConfig().getStringList("channels");
        list.remove(Channel.toLowerCase());
        main.plugin.getConfig().set("channels", list);
        main.plugin.saveConfig();
        main.plugin.reloadConfig();
        for(Object uuid : main.plugin.getPlayerChannels().keySet()) {
            if(getPlayersChannel(Bukkit.getPlayer((UUID) uuid)).equalsIgnoreCase(Channel)) {
                setPlayersChannel(Bukkit.getPlayer((UUID) uuid), getDefaultChannel());
                Bukkit.getPlayer((UUID) uuid).sendMessage(Utils.getMessage("channel-deleted-while-player-in"));
            }
        }
    }
    public static String getDefaultChannel() {
        return main.plugin.getConfig().getString("default_channel");
    }
    public static String getMessage(String msg) {
        return Chat(main.plugin.getLang().getString(msg));
    }
}
