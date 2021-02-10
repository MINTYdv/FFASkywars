package com.minty.leemonmc.ffaskywars.task;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.MyBlock;

public class TimerTaskBridgeEgg extends BukkitRunnable {
	FFASkywars main;
	Entity egg;
	Location previousLocation = null;
	Location loc;
	int tick;
	boolean firstTime;
	
	public TimerTaskBridgeEgg(FFASkywars main,Location loc) {
		this.main = main;
		this.tick = 15;
		this.loc = loc;
		firstTime = true;
	}
	
	@Override
	public void run() {
		
		if(firstTime) {
			Collection<Entity> entityList = loc.getWorld().getNearbyEntities(loc,5,5,5);
			for(Entity entity : entityList) {
				if(entity.getType().equals(EntityType.EGG)) {
					this.egg = entity;
					break;
				}
			}
			if(egg == null) {
				System.out.println("No egg found");
				cancel();
				return;
			}
		}
		
		previousLocation = egg.getLocation();
		previousLocation.setY(previousLocation.getY() - 2);
		
		if(tick <= 0) {
			cancel();
			return;
		}

		if(previousLocation.getBlock().getType().equals(Material.AIR)) {
			Block block = previousLocation.getBlock();
			block.setType(Material.WOOD);
			MyBlock myBlock = new MyBlock(block);
			main.getGameManager().addBlockInList(myBlock);
		}
		
		previousLocation = egg.getLocation();
		previousLocation.setY(previousLocation.getY() - 1);
		
		tick--;
	}

}
