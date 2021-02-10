package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.MyBlock;

public class TimerTaskBlock extends BukkitRunnable {
	FFASkywars main;
	MyBlock block;
	int second;
	
	public TimerTaskBlock(FFASkywars main,MyBlock block) {
		this.main = main;
		this.block = block;
		if(block.getBlock().getType().equals(Material.LAVA) || block.getBlock().getType().equals(Material.STATIONARY_LAVA)) {
			second = 5;
		}
		else if(block.getBlock().getType().equals(Material.WATER) || block.getBlock().getType().equals(Material.STATIONARY_WATER)) {
			second = main.getConfig().getInt("secondBeforeDespawnLiquid");
		}
		else {
			second = main.getConfig().getInt("secondBeforeDespawnBlock");
		}
	}
	
	@Override
	public void run() {
		
		if(second <= 0) {
			block.restoreType();
			main.getGameManager().deleteBlockInList(block);
			cancel();
			return;
		}
		
		second--;
	}
	
	public MyBlock getBlock() {
		return block;
	}
	
}
