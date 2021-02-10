package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.sun.javafx.collections.MapListenerHelper;

public class TimerTaskChangeMap extends BukkitRunnable {
	FFASkywars main;
	int second;
	
	@Override
	public void run() {
		
		if(second <= 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				main.getGameManager().setLobby(player, 20);
			}
			if(main.mapChangeid >= main.mapList.size()) {
				main.mapChangeid = 0;
			}
			main.map = main.mapList.get(main.mapChangeid);
			second = main.getConfig().getInt("secondBetweenChangeMap");
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §cCarte changée !");
			
			main.canGoInGame = true;
		}
		
		if(second <= 10) {
			main.canGoInGame = false;
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §cLa carte change dans " + second + " secondes !");
		}
		
		if(second == 60) {
			Bukkit.broadcastMessage("§6§lFFASkywars §f» §cLa carte change dans une minute !");
		}
	
		second--;
		main.secondBeforeChangeMap = second;
	}

	
	public TimerTaskChangeMap(FFASkywars main) {
		this.main = main;
		second = main.getConfig().getInt("secondBetweenChangeMap");
		main.map = main.mapList.get(0);
		System.out.println("Islands size : " + main.map.getIslands().size());
	}
	
}
