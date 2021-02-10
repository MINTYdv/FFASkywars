package com.minty.leemonmc.ffaskywars.task;

import java.util.Map.Entry;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class TimerTaskSuperAnvil extends BukkitRunnable {

	private FFASkywars main = FFASkywars.getInstance();
	
	@Override
	public void run()
	{
		for(Entry<Location, Player> entry : main.getSuperAnvilHandler().getAnvilsLocations().entrySet())
		{
		    for (int j = 0; j <= 2; j++) {
		    	entry.getKey().getWorld().playEffect(entry.getKey(),Effect.MOBSPAWNER_FLAMES,j);
		    }
		}
	}

	
	
}
