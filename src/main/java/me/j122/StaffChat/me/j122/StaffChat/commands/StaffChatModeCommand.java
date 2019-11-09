package me.j122.StaffChat.me.j122.StaffChat.commands;

import me.j122.StaffChat.main;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class StaffChatModeCommand implements CommandExecutor {
    private main Plugin;
    public StaffChatModeCommand(main Plugin) {
        this.Plugin = Plugin;
        Plugin.getCommand("staffchatmode").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You can't use this commmand in console!");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("staffchat.staffchatmode")) {
            p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
            return true;
        }
        if (Plugin.getPlayersInStaffChatMode().contains(p.getUniqueId())) {
            Plugin.getPlayersInStaffChatMode().remove(p.getUniqueId());
            p.sendMessage(Utils.Chat("&aStaff Chat Mode is disabled!"));
        } else {
            Plugin.getPlayersInStaffChatMode().add(p.getUniqueId());
            p.sendMessage(Utils.Chat("&aStaff Chat Mode is enabled!"));
        }
        return true;
    }
}
