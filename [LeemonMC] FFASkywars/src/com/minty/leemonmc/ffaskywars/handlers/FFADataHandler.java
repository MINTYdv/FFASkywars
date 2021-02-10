package com.minty.leemonmc.ffaskywars.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.minty.leemonmc.ffaskywars.core.FFAData;

public class FFADataHandler {

	private static Map<Player, FFAData> playersDatas = new HashMap<>();
	
	public static FFAData getPlayerData(Player player)
	{
		if(!getPlayersDatas().containsKey(player)) {
			getPlayersDatas().put(player, new FFAData(player));
		}
		return getPlayersDatas().get(player);
	}
	
	public static void removePlayerData(Player player)
	{
		if(getPlayersDatas().containsKey(player)) {
			getPlayersDatas().remove(player);
		}
	}
	
	public static Map<Player, FFAData> getPlayersDatas() {
		return playersDatas;
	}
	
}
