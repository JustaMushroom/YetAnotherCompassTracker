package com.justamushroom.yetanothercompasstracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Successfully started " + this.getName());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Stopping " + this.getName() + "...");
    }
}
