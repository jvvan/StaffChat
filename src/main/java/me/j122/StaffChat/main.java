package me.j122.StaffChat;

import me.j122.StaffChat.me.j122.StaffChat.commands.StaffChatChannelCommand;
import me.j122.StaffChat.me.j122.StaffChat.commands.StaffChatModeCommand;
import me.j122.StaffChat.me.j122.StaffChat.commands.StaffChatReloadCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import me.j122.StaffChat.me.j122.StaffChat.commands.StaffChatCommand;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public final class main extends JavaPlugin implements Listener {
    public static main plugin;
    public static boolean placeholders = false;
    public static ArrayList<UUID> PlayersInStaffChatMode = new ArrayList<>();
    public static Map<UUID, String> PlayerChannels = new HashMap<>();
    public ArrayList getPlayersInStaffChatMode() { return PlayersInStaffChatMode; }
    public Map getPlayerChannels() { return PlayerChannels; }
    private File l = null;
    private YamlConfiguration lang = new YamlConfiguration();
    @Override
    public void onEnable() {
        Utils.ConsoleMessage("&b[StaffChat] Enabling plugin version: "+ getDescription().getVersion());
        // Plugin startup logic
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI")!= null) {
            Utils.ConsoleMessage("&b[StaffChat] PlaceholderAPI found!");
            Utils.ConsoleMessage("&b[StaffChat] Using PlaceholderAPI!");
            placeholders = true;
        } else {
            Utils.ConsoleMessage("&b[StaffChat] PlaceholderAPI not found!");
            Utils.ConsoleMessage("&b[StaffChat] Not using PlaceholderAPI!");
        }
        new StaffChatCommand(this);
        new StaffChatModeCommand(this);
        new StaffChatChannelCommand(this);
        new StaffChatReloadCommand(this);
        getServer().getPluginManager().registerEvents(this, this);
        plugin = this;
        saveDefaultConfig();
        this.l = new File(getDataFolder(), "language.yml");
        mkdir();
        loadYamls();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Utils.ConsoleMessage("&b[StaffChat] Disabling plugin version: "+ getDescription().getVersion());
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().startsWith(getConfig().getString("staffchatprefix"))) {
            if(event.getPlayer().hasPermission("staffchat.send")) {
                event.setCancelled(true);
                Player p = event.getPlayer();
                Utils.SendStaffChatMessage(p, event.getMessage().substring(getConfig().getString("staffchatprefix").length()));
            }
            return;
        }
        if(this.getPlayersInStaffChatMode().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Player p = event.getPlayer();
            Utils.SendStaffChatMessage(p, event.getMessage());
            return;
        }
    }
    private void mkdir(){
        if (!this.l.exists()) {
            saveResource("language.yml", false);
        }
    }

    public void loadYamls(){
        try {
            lang.load(l);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getLang() { return this.lang; }
    public void saveLang() {
        try {
            this.lang.save(this.l);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
