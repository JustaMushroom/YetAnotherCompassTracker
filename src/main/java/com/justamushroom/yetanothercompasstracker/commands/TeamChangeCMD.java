package com.justamushroom.yetanothercompasstracker.commands;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.justamushroom.yetanothercompasstracker.Main;
import com.justamushroom.yetanothercompasstracker.utility.Teams;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.List;

public class TeamChangeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player plr) { // Checks whether this is executed from a player

            List<?> teams = Main.instance.getConfig().getList("teams.teamNames"); // Get the list of teams

            if (Main.instance.getConfig().getBoolean("commands.allowTeamSwap")) { // Check if team swapping is allowed

                String teamName = args[0];

                if (teamName.equalsIgnoreCase("None")) {
                    Teams.removePlayerTeam(plr);
                } else if (teams.contains(teamName)) { // Check if the team actually exists

                    Team targetTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName); // Get the team
                    Teams.changePlayerTeam(plr, targetTeam);

                } else { // If the team name wasn't valid

                    // Tell the user what went wrong
                    plr.sendMessage(ChatColor.RED + "That is not a valid team!");

                    // Display the list of valid teams
                    plr.sendMessage(ChatColor.YELLOW + "Valid teams are: " + String.join(",",
                            Lists.transform(teams, Functions.toStringFunction())));

                    // Show the command usage
                    return false;

                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute that command!");
        }
        return true;
    }
}
