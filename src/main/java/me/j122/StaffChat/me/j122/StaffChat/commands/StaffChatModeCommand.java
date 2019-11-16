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
            sender.sendMessage(Utils.getMessage("command-player-only"));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("staffchat.staffchatmode")) {
            p.sendMessage(Utils.getMessage("no-permissions"));
            return true;
        }
        if (Plugin.getPlayersInStaffChatMode().contains(p.getUniqueId())) {
            Plugin.getPlayersInStaffChatMode().remove(p.getUniqueId());
            p.sendMessage(Utils.getMessage("staffchatmode-disabled"));
        } else {
            Plugin.getPlayersInStaffChatMode().add(p.getUniqueId());
            p.sendMessage(Utils.getMessage("staffchatmode-enabled"));
        }
        return true;
    }
}
