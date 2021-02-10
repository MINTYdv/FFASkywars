package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import com.minty.leemonmc.ffaskywars.FFASkywars;

public class TimerTaskNormalChest extends BukkitRunnable {
	FFASkywars main;
	int second;
	
	public TimerTaskNormalChest(FFASkywars main) {
		this.main = main;
		second = main.getConfig().getInt("secondBetweenRegenNormalChest");
	}
	
	@Override
	public void run() {
		
		if(second <= 0) {
			main.getGameManager().regenNormalChest();
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §7Les coffres §6des îles §7sont régénérés §7!");
			second = main.getConfig().getInt("secondBetweenRegenNormalChest");
		}
		
		if(second == 60) {
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §7Les coffres §6des îles §7vont être régénérés dans §660 secondes §7!");
		}
		
		second--;
	}
	
	public int getSecond() {
		return second;
	}
	
}
