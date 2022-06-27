package com.justamushroom.yetanothercompasstracker;

import com.justamushroom.yetanothercompasstracker.commands.*;
import com.justamushroom.yetanothercompasstracker.compass.CompassRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance; // Points to the current instance of the plugin

    private void registerCommands()
    {
        Bukkit.getLogger().info("[" + this.getName() + "] {Commands} " + ChatColor.YELLOW + "Registering commands...");

        TabCompletion completer = new TabCompletion();
        this.getCommand("getcompass").setExecutor(new GetCompassCMD()); // Register /getcompass
        this.getCommand("changeteam").setExecutor(new TeamChangeCMD()); // Register /changeteam
        this.getCommand("changeteam").setTabCompleter(completer); // Add tab completion to /changeteam

        Bukkit.getLogger().info("[" + this.getName() + "] {Commands} " + ChatColor.GREEN + "All commands registered!");
    }

    private void loadConfig()
    {
        FileConfiguration config = this.getConfig();

        // Initialize default options
        config.addDefault("commands.allowTeamSwap", true); // Allow team swapping by default
        config.addDefault("compass.allowRightClick", true); // Allow compasses to be right-clicked
        config.addDefault("teams.teamNames", new String[]{}); // List of team names in the scoreboard, there's probably a better way to store this data
        config.addDefault("teams.allowActionBarTracking", true); // Allows the action bar to be used to track teammates

        // Copy default
        config.options().copyDefaults(true);

        // Save any changes to the config
        this.saveConfig();

        List<?> items = config.getList("teams.teamNames");

        if (items.size() > 0) Bukkit.getLogger().info("[" + this.getName() + "] {Config} " + ChatColor.GOLD + "Found unregistered teams! Registering " + items.size() + " teams...");

        for (Object item : items) {
            
            if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(item.toString()) == null) {

                // Register teams present in the config file but not in the scoreboard
                Bukkit.getLogger().info("[" + this.getName() + "] {Config} Registering team: " + item);
                Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(item.toString());
            }
        }
    }

    private void registerEvents() {
        Bukkit.getLogger().info("[" + this.getName() + "] {Events} " + ChatColor.YELLOW + "Registering events...");

        // Register compass right-clicking
        Bukkit.getPluginManager().registerEvents(new CompassRightClickEvent(), this);

        Bukkit.getLogger().info("[" + this.getName() + "] {Events} " + ChatColor.GREEN + "All events registered!");
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.YELLOW + "Setting up " + this.getName() + "...");

        registerCommands(); // Register commands
        registerEvents(); // Register event listeners
        loadConfig(); // Load the config
        instance = this; // Ensure everything knows this is the plugin instance

        Bukkit.getLogger().info(ChatColor.GREEN + "Successfully started " + this.getName() + "!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Stopping " + this.getName() + "...");
    }
}
