package me.j122.StaffChat.me.j122.StaffChat.commands;

import me.j122.StaffChat.main;
import me.j122.StaffChat.me.j122.StaffChat.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatChannelCommand implements CommandExecutor {
    public StaffChatChannelCommand(main Plugin) {
        Plugin.getCommand("staffchatchannel").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't use this commmand in console!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("staffchat.channel")) {
            p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
            return true;
        }
        if(args.length < 2) {
            p.sendMessage(Utils.Chat("&cUsage: /staffchatchannel <select/add/remove/setdefault> <name>"));
            return true;
        }
        String subcommand = args[0];
        String name = args[1].toLowerCase();
        if (subcommand.equalsIgnoreCase( "select")) {
            if (Utils.channelExists(name)) {
                if (!p.hasPermission("staffchat.channels." + name + ".send")) {
                    p.sendMessage(Utils.Chat("&cYou don't have permission to access this channel!"));
                    return true;
                }
                Utils.setPlayersChannel(p, name);
                p.sendMessage(Utils.Chat("&aChannel set to '" + name + "'"));
                return true;
            } else {
                p.sendMessage(Utils.Chat("&cThat channel doesn't exist!"));
            }
        } else if (subcommand.equalsIgnoreCase("add")) {
            if(Utils.channelExists(name)) {
                p.sendMessage(Utils.Chat("&cThat channnel already exists!"));
                return true;
            }
            if(!p.hasPermission("staffchat.channel.add")) {
                p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
                return true;
            }
            Utils.addChannel(name);
            p.sendMessage(Utils.Chat("&aChannel '"+name+"' added!"));
        } else if (subcommand.equalsIgnoreCase("remove")) {
            if(!Utils.channelExists(name)) {
                p.sendMessage(Utils.Chat("&cThat channnel doesn't exist!"));
                return true;
            }
            if(!p.hasPermission("staffchat.channel.remove")) {
                p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
                return true;
            }
            if(name.equalsIgnoreCase(Utils.getDefaultChannel())) {
                p.sendMessage(Utils.Chat("&cYou can't remove a default channel!\nIf you want to change default channel use /scc setdefault <name>."));
                return true;
            }
            Utils.removeChannel(name);
            p.sendMessage(Utils.Chat("&aChannel '"+name+"' removed!"));
        } else if (subcommand.equalsIgnoreCase("setdefault")) {
            if(!p.hasPermission("staffchat.channel.setdefault")) {
                p.sendMessage(Utils.Chat("&cYou don't have permission to use this command!"));
                return true;
            }
            if(name.equalsIgnoreCase(Utils.getDefaultChannel())) {
                p.sendMessage(Utils.Chat("&cThat channel is already default!"));
            }
            if(!Utils.channelExists(name)) {
                p.sendMessage(Utils.Chat("&cThat channnel doesn't exist!"));
                return true;
            }
            Utils.setDefaultChannel(name);
            p.sendMessage(Utils.Chat("&aChannel '"+name+"' is now default!"));
        } else {
            p.sendMessage(Utils.Chat("&cCan't find that subcommand!"));
            return true;
        }
        return true;
    }
}
