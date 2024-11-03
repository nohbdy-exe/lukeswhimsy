package me.nohbdyexe.lukesWhimsy.commands;

import me.nohbdyexe.lukesWhimsy.LukesWhimsy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    private final LukesWhimsy plugin;
    private String PLUGIN_PREFIX;

    public HelpCommand(LukesWhimsy plugin) {
        this.plugin = plugin;
        this.PLUGIN_PREFIX = plugin.getPluginPrefix();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("whimsyhelp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(PLUGIN_PREFIX + "You must be a player to use this command.");
                return true;
            }

            if (sender.isOp()) {
                sender.sendMessage(ChatColor.BLUE + "---------- [LukesWhimsy] Help ----------");
                sender.sendMessage(ChatColor.BLUE + "/feed" + ChatColor.RESET + " - Fills the hunger bar of yourself.");
                sender.sendMessage(ChatColor.BLUE + "/heal" + ChatColor.RESET + " - Fills the hunger bar of yourself.");
                sender.sendMessage(ChatColor.BLUE + "/fling [player]" + ChatColor.RESET + " - Fling yourself or a certain player.");
                sender.sendMessage(ChatColor.BLUE + "/onepunchman" + ChatColor.RESET + " - Makes one invincible and able to one shot everything.");
                sender.sendMessage(ChatColor.BLUE + "/catapult" + ChatColor.RESET + " - Shoots a projectile that explodes.");
                sender.sendMessage(ChatColor.BLUE + "/fly" + ChatColor.RESET + " - Toggles fly on/off for yourself.");
                sender.sendMessage(ChatColor.BLUE + "/sit [player]" + ChatColor.RESET + " - Sit anywhere or on a player.");
                sender.sendMessage(ChatColor.BLUE + "/hng" + ChatColor.RESET + " - Make a villager noise.");
                return true;
            }

            sender.sendMessage(ChatColor.BLUE + "---------- [LukesWhimsy] Help ----------");
            sender.sendMessage(ChatColor.BLUE + "/sit [player]" + ChatColor.RESET + " - Sit anywhere or on a player.");
            sender.sendMessage(ChatColor.BLUE + "/hng" + ChatColor.RESET + " - Make a villager noise.");

            return true;
        }
        return true;
    }
}