package com.justamushroom.yetanothercompasstracker.utility;

import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CompassManagement {

    public static ItemStack getCompass() {
        // Initialize the compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();

        meta.setDisplayName(ChatColor.RESET + "Tracking Compass"); // Custom name
        meta.setLore(new ArrayList<String>() {{ // Custom "enchant" tooltip
            add(ChatColor.GRAY + "Tracking I");
        }});

        // Add a useless enchant for the shimmer effect
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);

        // Set the value to recognize this compass as a valid tracking compass
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(Main.instance, "compass.isTracking"), PersistentDataType.STRING, "true");

        // Replace the metadata on the compass with the new modified version
        compass.setItemMeta(meta);

        return compass; // Return the generated Compass
    }

    public static boolean isCompass(ItemStack item) {
        if (item == null) return false;
        if (item.getType() != Material.COMPASS) return false; // Only compasses can have the tracking flag. No need to check further

        // Get item metadata
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        // Check if the compass has the tracking value.
        boolean isTracking = Boolean.parseBoolean(container.get(new NamespacedKey(Main.instance, "compass.isTracking"),
                PersistentDataType.STRING));

        if (isTracking) {
            return true; // It is a tracking compass
        } else {
            return false; // It is not a tracking compass
        }
    }

    public static boolean hasCompass(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (isCompass(item)) {
                return true; // There is a tracking compass in the inventory, no need to check any more items
            }
        }
        return false; // No compasses have been found
    }
}
