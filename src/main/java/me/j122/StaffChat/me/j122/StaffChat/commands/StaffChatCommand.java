package me.j122.StaffChat.me.j122.StaffChat.commands;

import me.j122.StaffChat.main;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
    public StaffChatCommand(main Plugin) {
        Plugin.getCommand("staffchat").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You can't use this commmand in console!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("staffchat.send")) {
            p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
            return true;
        }
        if(args.length == 0) {
            p.sendMessage(Utils.Chat("&cUsage: /staffchat <message>"));
            return true;
        }

        Utils.SendStaffChatMessage(p, String.join(" ", args));
        return true;
    }
}
