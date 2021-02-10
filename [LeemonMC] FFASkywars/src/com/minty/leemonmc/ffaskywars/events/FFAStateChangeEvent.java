package com.minty.leemonmc.ffaskywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.minty.leemonmc.ffaskywars.core.FFAState;

public class FFAStateChangeEvent extends Event {

	 private static final HandlerList handlers = new HandlerList();
	 
	 private FFAState newState;
	 private FFAState oldState;
	 private Player player;
	 
	 public FFAStateChangeEvent(Player player, FFAState newState, FFAState oldState)
	 {
		 this.player = player;
		 this.newState = newState;
		 this.oldState = oldState;
	 }
	 
	 public HandlerList getHandlers()
	 {
	    return handlers; 
	 } 
	 
	 public Player getPlayer() {
		return player;
	}
	 
	 public static HandlerList getHandlerList()
	 { 
		return handlers;
	 }
	
	 public FFAState getNewState() {
		 return newState;
	 }
	 
	 public FFAState getOldState() {
		return oldState;
	}
	
}
