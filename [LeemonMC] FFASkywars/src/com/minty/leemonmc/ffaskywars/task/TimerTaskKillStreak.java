package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;

public class TimerTaskKillStreak extends BukkitRunnable {

	@Override
	public void run() {
		
		if(FFASkywars.getInstance().getGameManager().getPlayingPlayer() == null) return;
		
		for(Player player : FFASkywars.getInstance().getGameManager().getPlayingPlayer()) {
			FFAData data = FFADataHandler.getPlayerData(player);
			if(data.getKillStreak() >= 5) {
				FFASkywars.getInstance().spawnParticulesKillStreak(player);
			}
		}
		
	}

}
