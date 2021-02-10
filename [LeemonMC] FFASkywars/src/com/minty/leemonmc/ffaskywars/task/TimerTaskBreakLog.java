package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.MyBlock;

public class TimerTaskBreakLog extends BukkitRunnable {
	FFASkywars main;
	Block block;
	Material mat;
	Byte data;
	int second;
	
	@SuppressWarnings("deprecation")
	public TimerTaskBreakLog(FFASkywars main,Block block) {
		this.main = main;
		second = main.getConfig().getInt("secondBeforeLogRespawn");
		this.block = block;
		this.mat = block.getType();
		this.data = block.getData();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		if(second <= 0) {
			block.setType(mat);
			block.setData(data);
			cancel();
			return;
		}
		if(second >= 1) {
			block.setType(Material.STAINED_GLASS);
			block.setData((byte) 12);
		}
		
		second--;
	}
	
}
