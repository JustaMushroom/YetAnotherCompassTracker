package com.justamushroom.yetanothercompasstracker.events;

import com.justamushroom.yetanothercompasstracker.Main;
import com.justamushroom.yetanothercompasstracker.utility.Teams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class UpdateActionBar implements Runnable {
    @Override
    public void run() {
        // If action bar tracking is turned off in the config, don't do anything
        // There's probably a better way of doing this
        if (!Main.instance.getConfig().getBoolean("teams.allowActionBarTracking")) return;

        for (Player plr : Bukkit.getOnlinePlayers()) { // Iterate over every online player

            if (Teams.getPlayerTeam(plr) != null) { // Skip over all players who aren't in a managed team

                Team playerTeam = Teams.getPlayerTeam(plr); // Get the player's team

                // Get the closest player and their distance to the player
                Player closestTeammate = Teams.getClosestPlayerOfTeam(plr);
                double distance = plr.getLocation().distance(closestTeammate.getLocation());

                // Build the display, what the player sees
                String display = "Your Team: " + ChatColor.BOLD + playerTeam.getColor() + playerTeam.getDisplayName()
                        + ChatColor.RESET + " | Closest Teammate: " + ChatColor.BOLD + playerTeam.getColor()
                        + closestTeammate.getDisplayName() + "[" + Math.round(distance * 100) / 100 + "m]";

                // Display the action bar to the player
                plr.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(display));

                // Point the player's compass to the closest teammate
                plr.setCompassTarget(closestTeammate.getLocation());
            }
        }
    }
}
