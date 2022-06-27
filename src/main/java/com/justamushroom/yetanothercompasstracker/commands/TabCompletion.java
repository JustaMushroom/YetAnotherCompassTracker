package com.justamushroom.yetanothercompasstracker.commands;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] arguments) {
        if (cmd.getName().equalsIgnoreCase("changeteam")) {
            return Lists.transform(Main.instance.getConfig().getList("teams.teamNames"), Functions.toStringFunction());
        }
        return null;
    }
}
