package com.justamushroom.yetanothercompasstracker.utility;

import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;

public class Teams {

    // Overload of changePlayer team to make silent a non-required parameter
    public static void changePlayerTeam(Player plr, Team targetTeam) {
        changePlayerTeam(plr, targetTeam, false);
    }

    //TODO: Add more error handling
    public static void changePlayerTeam(Player plr, Team targetTeam, boolean silent) {
        // Get player's current team (if applicable)
        Team currentTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(plr.getName());

        if (currentTeam != null) {
            currentTeam.removeEntry(plr.getName()); // Remove player from their current team
        }

        targetTeam.addEntry(plr.getName()); // Add player to new team

        // Broadcast team change in chat if not done silently
        if (!silent) {
            Bukkit.getServer().broadcastMessage(plr.getDisplayName() + " is now on team "
                    + ChatColor.BOLD + targetTeam.getColor() + targetTeam.getName() + ChatColor.RESET);
        }
    }

    public static void removePlayerTeam(Player plr) {
        removePlayerTeam(plr, false);
    }

    // Removes a player from their team (if they are in one)
    public static void removePlayerTeam(Player plr, boolean silent) {
        // Get player's current team
        Team currentTeam = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(plr.getName());

        if (currentTeam != null) {
            // Remove the player from their team
            currentTeam.removeEntry(plr.getName());

            // Broadcast the removal message to all players if not done silently
            if (!silent) {
                Bukkit.getServer().broadcastMessage(plr.getDisplayName() + " is no longer on a team!");
            }
        }
    }

    // Get the Team object of a player's team. Returns null if they are not in a team
    @Nullable
    public static Team getPlayerTeam(Player plr) {
        try {
            return Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(plr.getName());
        } catch (NullPointerException error) {
            return null;
        }
    }
}
