package com.justamushroom.yetanothercompasstracker.commands;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.justamushroom.yetanothercompasstracker.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] arguments) {
        if (cmd.getName().equalsIgnoreCase("changeteam")) {
            if (arguments.length < 1) return null;
            return Lists.transform(Main.instance.getConfig().getList("teams.teamNames"), Functions.toStringFunction());
        } else if (cmd.getName().equalsIgnoreCase("settings")) {
            if (arguments[0].equals("")) {
                return new ArrayList<String>() {{
                    add("teams");
                    add("enableCompasses");
                    add("enableActionbar");
                    add("allowTeamSwap");
                }};
            } else if (arguments[1].equals("")) {
                switch (arguments[0].toLowerCase()) {
                    case "teams":
                    {
                        return new ArrayList<>() {{
                            add("add");
                            add("remove");
                            add("list");
                        }};
                    }
                    default:
                    {
                        return new ArrayList<>() {{
                            add("true");
                            add("false");
                        }};
                    }
                }
            }
        }
        return null;
    }
}
