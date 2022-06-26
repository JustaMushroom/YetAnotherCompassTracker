package com.justamushroom.yetanothercompasstracker.utility;

import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class Teams {
    //TODO: Add more error handling
    public static void changePlayerTeam(Player plr, Team targetTeam, boolean silent) {
        Team currentTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(plr.getName()); // Get player's current team (if applicable)

        if (currentTeam != null) {
            currentTeam.removeEntry(plr.getName()); // Remove player from current team
        }

        targetTeam.addEntry(plr.getName()); // Add player to new team

        // Broadcast team change in chat if not done silently
        if (!silent) {
            Bukkit.getServer().broadcastMessage(plr.getDisplayName() + " is now on team " + ChatColor.BOLD + targetTeam.getColor() + targetTeam.getName() + ChatColor.RESET);
        }
    }
}
