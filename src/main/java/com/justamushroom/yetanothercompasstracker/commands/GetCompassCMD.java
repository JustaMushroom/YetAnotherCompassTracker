package com.justamushroom.yetanothercompasstracker.commands;

import com.justamushroom.yetanothercompasstracker.utility.CompassManagement;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GetCompassCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player plr) {
            if (!CompassManagement.hasCompass(plr.getInventory())) { // Check if they already have a tracking compass
                plr.getInventory().addItem(CompassManagement.getCompass()); // If they don't, give them one

                plr.sendMessage(ChatColor.GREEN + "Successfully gave you a tracking compass!"); // Send success message
            } else {
                plr.sendMessage(ChatColor.YELLOW + "You already have a tracking compass!"); // Tell the player they already have one
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!"); // Tell console they can't do that
        }
        return true;
    }
}
