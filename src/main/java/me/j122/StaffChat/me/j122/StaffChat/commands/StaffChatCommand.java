package me.j122.StaffChat.me.j122.StaffChat.commands;

import me.j122.StaffChat.main;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
    private main plugin;
    public StaffChatCommand(main Plugin) {
        plugin = Plugin;
        Plugin.getCommand("staffchat").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.getMessage("command-player-only"));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("staffchat.send")) {
            p.sendMessage(Utils.getMessage("no-permissions"));
            return true;
        }
        if(args.length == 0) {
            p.sendMessage(Utils.getMessage("usages.staffchatcommand"));
            return true;
        }

        Utils.SendStaffChatMessage(p, String.join(" ", args));
        return true;
    }
}
