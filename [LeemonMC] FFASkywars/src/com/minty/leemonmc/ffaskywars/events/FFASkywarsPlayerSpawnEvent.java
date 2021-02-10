package com.minty.leemonmc.ffaskywars.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minty.leemonmc.ffaskywars.core.FFAIsland;

public class FFASkywarsPlayerSpawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Location loc;
	private FFAIsland island;
	
	public FFASkywarsPlayerSpawnEvent(Player player,Location loc,FFAIsland island) {
		this.player = player;
		this.loc = loc;
		this.island = island;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{ 
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getLocation() {
		return loc;
	}

	public FFAIsland getIsland() {
		return island;
	}
}
