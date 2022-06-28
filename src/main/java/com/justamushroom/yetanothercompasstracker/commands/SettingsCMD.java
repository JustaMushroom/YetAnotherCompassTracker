package com.justamushroom.yetanothercompasstracker.commands;

import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class SettingsCMD implements CommandExecutor {

    private void broadcastTeamLockMessage(boolean enabled) {
        if (enabled) {
            Bukkit.getServer().broadcastMessage("Team Changing is now " + ChatColor.BOLD + ChatColor.GREEN + "ENABLED" + ChatColor.RESET + "!");
        } else {
            Bukkit.getServer().broadcastMessage("Team Changing is now " + ChatColor.BOLD + ChatColor.RED + "DISABLED" + ChatColor.RESET + "!");
        }
    }
    // TODO: Implement Command Body w/ Autocomplete

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Invalid Usage!");
            return false;
        }

        String setting = args[0].toLowerCase();
        String val1 = args[1].toLowerCase();

        FileConfiguration config = Main.instance.getConfig();

        if (setting.equalsIgnoreCase("teams")) {
            List<String> teams = config.getStringList("teams.teamName");
            switch (val1) {
                case "add":
                {
                    if (args.length < 3)
                    {
                        sender.sendMessage("Invalid Usage!");
                        sender.sendMessage("/settings teams add <teamName>");
                        return true;
                    }
                    Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(args[2]);
                    teams.add(args[2]);
                    config.set("teams.teamNames", teams);
                    sender.sendMessage(ChatColor.GREEN + "Team added sucessfully!");
                    break;
                }
                case "remove":
                {
                    if (args.length < 3)
                    {
                        sender.sendMessage("Invalid Usage!");
                        sender.sendMessage("/settings teams remove <teamName>");
                        return true;
                    }
                    Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[2]);
                    if (team == null) {
                        sender.sendMessage("Invalid team to remove! \""+ args[2] + "\"");
                        return true;
                    }
                    team.unregister();
                    teams.remove(args[2]);
                    config.set("teams.teamNames", teams);
                    sender.sendMessage(ChatColor.GREEN + "Team removed sucessfully!");
                    break;
                }
                case "list":
                {
                    sender.sendMessage("Here are all teams currently added");
                    sender.sendMessage(String.join(",", teams));
                }
            }
        } else {
            switch (setting) {
                case "enablecompasses":
                {
                    config.set("compass.allowRightClick", Boolean.parseBoolean(val1));
                    sender.sendMessage(ChatColor.GREEN + "Compass Right-clicking was " + (Boolean.parseBoolean(val1)? "enabled" : "disabled") + "!");
                    break;
                }
                case "enableactionbar":
                {
                    config.set("teams.allowActionBarTracking", Boolean.parseBoolean(val1));
                    sender.sendMessage(ChatColor.GREEN + "Action bar tracking was " + (Boolean.parseBoolean(val1)? "enabled" : "disabled") + "!");
                    break;
                }
                case "allowteamswap":
                {
                    config.set("commands.allowTeamSwap", Boolean.parseBoolean(val1));
                    sender.sendMessage(ChatColor.GREEN + "Team Swapping was " + (Boolean.parseBoolean(val1)? "enabled" : "disabled") + "!");
                    broadcastTeamLockMessage(Boolean.parseBoolean(val1));
                    break;
                }
            }
        }
        Main.instance.saveConfig();
        return true;
    }
}
