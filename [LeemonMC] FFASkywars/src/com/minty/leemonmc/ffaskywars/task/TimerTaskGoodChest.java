package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import com.minty.leemonmc.ffaskywars.FFASkywars;

public class TimerTaskGoodChest extends BukkitRunnable {
	FFASkywars main;
	int second;
	
	public TimerTaskGoodChest(FFASkywars main) {
		this.main = main;
		second = main.getConfig().getInt("secondBetweenRegenGoodChest");
	}
	
	@Override
	public void run() {
		
		if(second <= 0) {
			main.getGameManager().regenGoodChest();
			Bukkit.broadcastMessage("§6§lLeemonMC §f» §7Les coffres §6du centre de la carte §7sont régénérés §7!");
			second = main.getConfig().getInt("secondBetweenRegenGoodChest");
		}
		
		if(second == 60) {
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §7Les coffres §6du centre de la carte §7vont être régénérés dans §660 secondes §7! ");
		}
		
		if(second == 240) {
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §7Les coffres §6du centre de la carte §7vont être régénérés dans §64 minutes §7! ");
		}
		
		second--;
	}
	
	public int getSecond() {
		return second;
	}
	
}
