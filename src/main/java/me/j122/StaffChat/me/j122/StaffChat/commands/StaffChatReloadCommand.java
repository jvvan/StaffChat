package me.j122.StaffChat.me.j122.StaffChat.commands;

import me.j122.StaffChat.main;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatReloadCommand implements CommandExecutor {
    main plugin;
    public StaffChatReloadCommand(main Plugin) {
        Plugin.getCommand("staffchatreload").setExecutor(this);
        plugin = Plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Utils.reload();
            sender.sendMessage(Utils.getMessage("config-reloaded"));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("staffchat.reload")) {
            p.sendMessage(Utils.getMessage("no-permissions"));
            return true;
        }
        Utils.reload();
        p.sendMessage(Utils.getMessage("config-reloaded"));
        return true;
    }
}
