package com.minty.leemonmc.ffaskywars.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minty.leemonmc.ffaskywars.core.PlayerDieCause;

public class FFASkywarsPlayerDeathEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player killer;
	private Player player;
	private Location loc;
	private PlayerDieCause dieCause;
	
	public FFASkywarsPlayerDeathEvent(Player player, Player killer,Location loc,PlayerDieCause dieCause) {
		this.player = player;
		this.killer = killer;
		this.loc = loc;
		this.dieCause = dieCause;
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
	
	public Player getKiller() {
		return killer;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public PlayerDieCause getDieCause() {
		return dieCause;
	}
	
}
