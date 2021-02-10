package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.MyBlock;

public class TimerTaskCreateBlock extends BukkitRunnable {
	FFASkywars main;
	Location loc;
	
	public TimerTaskCreateBlock(FFASkywars main,Location loc) {
		this.main = main;
		this.loc = loc;
	}
	
	
	@Override
	public void run() {
		
		if(!main.getGameManager().isBlockInList(loc.getBlock())) {
			MyBlock myBlock = new MyBlock(loc.getBlock());
			main.getGameManager().addBlockInList(myBlock);
		}
		else {
			MyBlock block = main.getGameManager().getBlockWithLocation(loc.getBlock().getLocation());
			main.getGameManager().deleteBlockInList(block);
			MyBlock myBlock = new MyBlock(loc.getBlock());
			main.getGameManager().addBlockInList(myBlock);
		}
		
		cancel();

	}

}
