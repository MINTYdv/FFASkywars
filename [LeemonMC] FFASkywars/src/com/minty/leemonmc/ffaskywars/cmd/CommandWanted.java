package com.minty.leemonmc.ffaskywars.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.gui.WantedGui;

public class CommandWanted implements CommandExecutor {
	FFASkywars main = FFASkywars.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player))
		{
			return false;
		}
		
		Player player = (Player) sender;
		main.getAPI().getGuiManager().open(player, WantedGui.class);
		
		return false;
	}

}
