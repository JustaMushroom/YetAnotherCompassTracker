package com.justamushroom.yetanothercompasstracker.compass;

import com.justamushroom.yetanothercompasstracker.Main;
import com.justamushroom.yetanothercompasstracker.utility.CompassManagement;
import com.justamushroom.yetanothercompasstracker.utility.Teams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;


public class CompassRightClickEvent implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getItem() == null) return;

        // If right-clicking tracking compasses is disabled, don't do anything
        if (!Main.instance.getConfig().getBoolean("compass.allowRightClick")) return;

        Player plr = event.getPlayer(); // Get Player
        Team plrTeam = Teams.getPlayerTeam(plr); // Get Player's Team

        // Check if the item they right-clicked with is a valid tracking compass and they are in a team
        if (CompassManagement.isCompass(event.getItem()) && plrTeam != null) {

            // Get distances of player's teammates
            HashMap<Player, Double> distances = Teams.getDistanceToTeamPlayers(plr);

            StringBuilder message = new StringBuilder("Here are the distances to all players on team "
                    + ChatColor.BOLD + plrTeam.getColor() +
                    plrTeam.getDisplayName() + ChatColor.RESET + ":"); // Begin building the Output

            // Iterate over player's teammates
            for (Player tPlr : distances.keySet()) {

                double distance = distances.get(tPlr); // Grab distance from the distances HashMap

                // Append the player info to the message
                message.append("\n" + ChatColor.BOLD)
                        .append(plrTeam.getColor()).append(tPlr.getDisplayName()).append(ChatColor.RESET)
                        .append(" - ").append(distance).append("m"); // This could be made easier to read
            }

            plr.sendMessage(message.toString()); // Send the player their teammates
        }
    }
}
