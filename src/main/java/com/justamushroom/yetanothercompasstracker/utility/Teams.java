package com.justamushroom.yetanothercompasstracker.utility;

import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.*;

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
            // Get teams that are managed by the plugin
            List<?> teams = Main.instance.getConfig().getList("teams.teamNames");

            // Iterate over every team that the plugin manages
            for (Object teamName : teams) {
                // Search for the team object
                Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName.toString());

                // If the player is in the team, return it
                if (team.getEntries().contains(plr.getName())) return team;
            }
            return null; // If no team found, return null
        } catch (NullPointerException error) { // If something goes wrong, return null
            return null;
        }
    }

    @Nullable
    public static Player getClosestPlayerOfTeam(Player basePlayer) {
        return getClosestPlayerOfTeam(getPlayerTeam(basePlayer), basePlayer);
    }

    // Get the closest player to basePlayer who is in matchTeam
    // Returns the basePlayer if no matching teammates in matchTeam are found
    public static Player getClosestPlayerOfTeam(Team matchTeam, Player basePlayer) {
        if (matchTeam == null) return null; // Should only return under specific circumstances
        Set<String> playerNames = matchTeam.getEntries(); // Get player names in the team

        Player closestPlr = basePlayer; // Define the closest player and their distance
        double distance = Double.MAX_VALUE;
        Location baseLoc = basePlayer.getLocation(); // Get the basePlayer's location

        for (String name : playerNames) { // Go through all the team members
            Player plr = Bukkit.getPlayer(name);
            if (plr == null) continue; // Doesn't Exist? Skip it

            if (!plr.isOnline()) continue; // Not online? Skip them

            Location targetLoc = plr.getLocation(); // Get the location of the team member

            // Not in the same world? Skip them
            if (targetLoc.getWorld() != baseLoc.getWorld()) continue; // TODO: Add an argument to ignore this check

            double distanceTo = baseLoc.distance(targetLoc); // Get the basePlayer's distance to the target

            if (distanceTo < distance) { // If they are closer than the current closest, update it
                distance = distanceTo;
                closestPlr = plr;
            }
        }
        return closestPlr; // Return the closest target, will return BasePlayer if no other valid teammates are found
    }
}
