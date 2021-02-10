package com.minty.leemonmc.ffaskywars.task;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class TimerTaskLoot extends BukkitRunnable {
	FFASkywars main;
	ArrayList<Item> items;
	Location loc;
	
	public TimerTaskLoot(FFASkywars main,ArrayList<Item> items,Location loc) {
		this.main = main;
		this.items = items;
		this.loc = loc;
	}
	
	@Override
	public void run() {
		
		for(Item item : items) {
			item.setVelocity(new Vector());
			new BukkitRunnable() {
				
				@Override
				public void run() {
					//item.teleport(loc);
					cancel();
				}
			}.runTaskTimer(main, 5, 1);
		}
		cancel();
		
	}
	
}

