package com.justamushroom.yetanothercompasstracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance; // Points to the current instance of the plugin

    private void registerCommands()
    {
        Bukkit.getLogger().info("[" + this.getName() + "] {Commands} " + ChatColor.GREEN + "All Commands Registered!");
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
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.YELLOW + "Setting up " + this.getName() + "...");

        registerCommands(); // Register commands
        loadConfig(); // Load the config
        instance = this; // Ensure everything knows this is the plugin instance

        Bukkit.getLogger().info(ChatColor.GREEN + "Successfully started " + this.getName());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Stopping " + this.getName() + "...");
    }
}
