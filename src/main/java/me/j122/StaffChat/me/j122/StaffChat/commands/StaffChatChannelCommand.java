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
            sender.sendMessage(Utils.getMessage("command-player-only"));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("staffchat.channel")) {
            p.sendMessage(Utils.getMessage("no-permissions"));
            return true;
        }
        if(args.length < 2) {
            p.sendMessage(Utils.getMessage("usages.staffchatchannelcommand"));
            return true;
        }
        String subcommand = args[0];
        String name = args[1].toLowerCase();
        if (subcommand.equalsIgnoreCase( "select")) {
            if (Utils.channelExists(name)) {
                if (!p.hasPermission("staffchat.channels." + name + ".send")) {
                    p.sendMessage(Utils.getMessage("no-permissions"));
                    return true;
                }
                Utils.setPlayersChannel(p, name);
                p.sendMessage(Utils.getMessage("channel-selected").replaceAll("\\{name}", name));
                return true;
            } else {
                p.sendMessage(Utils.getMessage("channel-doesnt-exist").replaceAll("\\{name}", name));
            }
        } else if (subcommand.equalsIgnoreCase("add")) {
            if(Utils.channelExists(name)) {
                p.sendMessage(Utils.getMessage("channel-already-exists").replaceAll("\\{name}", name));
                return true;
            }
            if(!p.hasPermission("staffchat.channel.add")) {
                p.sendMessage(Utils.getMessage("no-permissions"));
                return true;
            }
            Utils.addChannel(name);
            p.sendMessage(Utils.getMessage("channel-added").replaceAll("\\{name}", name));
        } else if (subcommand.equalsIgnoreCase("remove")) {
            if(!Utils.channelExists(name)) {
                p.sendMessage(Utils.getMessage("channel-doesnt-exist").replaceAll("\\{name}", name));
                return true;
            }
            if(!p.hasPermission("staffchat.channel.remove")) {
                p.sendMessage(Utils.getMessage("no-permissions"));
                return true;
            }
            if(name.equalsIgnoreCase(Utils.getDefaultChannel())) {
                p.sendMessage(Utils.getMessage("cant-remove-default-channel"));
                return true;
            }
            Utils.removeChannel(name);
            p.sendMessage(Utils.getMessage("channel-removed").replaceAll("\\{name}", name));
        } else if (subcommand.equalsIgnoreCase("setdefault")) {
            if(!p.hasPermission("staffchat.channel.setdefault")) {
                p.sendMessage(Utils.getMessage("no-permissions"));
                return true;
            }
            if(name.equalsIgnoreCase(Utils.getDefaultChannel())) {
                p.sendMessage(Utils.getMessage("channel-already-default").replaceAll("\\{name}", name));
            }
            if(!Utils.channelExists(name)) {
                p.sendMessage(Utils.getMessage("channel-doesnt-exist").replaceAll("\\{name}", name));
                return true;
            }
            Utils.setDefaultChannel(name);
            p.sendMessage(Utils.getMessage("channel-set-to-default").replaceAll("\\{name}", name));
        } else {
            p.sendMessage(Utils.getMessage("unknown-subcommand").replaceAll("\\{subcommand}", subcommand));
            return true;
        }
        return true;
    }
}
