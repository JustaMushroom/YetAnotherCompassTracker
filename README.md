# Yet Another Compass Tracker
[![](https://badgen.net/github/release/JustaMushroom/YetAnotherCompassTracker/stable)](https://github.com/JustaMushroom/YetAnotherCompassTracker/releases/latest)  
This is a simple plugin that allows players to track teammates through compasses  
Teams are managed through the vanilla scoreboard system.

**This Plugin is built for `Minecraft 1.18`, Other versions are not supported at this time**

## How to use
### Commands
`/compass` - Gives the player a tracking compass  
`/settings <option> <value>` - Change a tracking setting (Requires op)  
`/settings teams <add/remove/list> [teamName]` - Modify/List Tracking teams (Requires op)  
`/changeteam` - Changes your team for tracking

### Mechanics
Right-click with a tracking compass to display the distance to all your teammates  
If Action bar tracking is enabled (Default: on) the distance to your closest teammate is displayed on the action bar  
**All tracking mechanics only work if the player is in a team registered by this plugin**

### Settings / Config Entries
*Settings can be found at `/plugins/YetAnotherCompassTracker/config.yml`*

- Allow Team Swapping: When disabled, the `changeteam` command is disabled, and it is not possible for users to change their team


- Allow Right-Clicking: When disabled, Tracking compasses will not do anything when right-clicked


- Action Bar Tracking: If enabled, The action bar will be used by the plugin to display the player's current team and distance to closest teammate.  
*Disable this if other plugins are using the actionbar*


- Team Names: A list of team names used by the plugin to track which teams are used for tracking  
*Adding to this setting manually in `config.yml` will automatically register any missing teams on next restart*


## Building
Clone the repository  
```
git clone https://github.com/JustaMushroom/YetAnotherCompassTracker.git 
```
Import the repository into your IDE as a gradle project

To build the project, run the `jar` build task

## To-dos
- [ ] Implement more team customization (Team Colors, Prefixes/Suffixes)  
- [ ] Allow tracking individual players (for 1v1 scenarios)
- [ ] Implement Crafting/Enchantment recipes for tracking compasses
- [ ] Allow admins to forcefully manipulate player teams