package com.minty.leemonmc.ffaskywars.task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAIsland;

public class TimerTaskCreateMap extends BukkitRunnable {
	FFASkywars main;
	
	public TimerTaskCreateMap(FFASkywars main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		/*ArrayList<FFAIsland> islandList = new ArrayList<FFAIsland>();
		islandList.add(main.generateIslandTest(main.locTest(-8,142,18),main.locTest(-6,143,18),main.locTest(-9,142,17),1));
		islandList.add(main.generateIslandTest(main.locTest(-8,142,31),main.locTest(-6,143,31),main.locTest(-9,142,30),2));
		islandList.add(main.generateIslandTest(main.locTest(-16,142,49),main.locTest(-18,143,49),main.locTest(-15,142,50),3));
		islandList.add(main.generateIslandTest(main.locTest(-32,142,57),main.locTest(-34,143,57),main.locTest(-31,142,58),4));
		islandList.add(main.generateIslandTest(main.locTest(-48,142,59),main.locTest(-48,143,61),main.locTest(-47,142,58),5));
		islandList.add(main.generateIslandTest(main.locTest(-62,143,47),main.locTest(-62,144,49),main.locTest(-61,143,46),6));
		islandList.add(main.generateIslandTest(main.locTest(-72,142,31),main.locTest(-72,143,33),main.locTest(-71,142,30),7));
		islandList.add(main.generateIslandTest(main.locTest(-71,142,17),main.locTest(-71,143,19),main.locTest(-70,142,16),8));
		islandList.add(main.generateIslandTest(main.locTest(-63,142,2),main.locTest(-63,143,4),main.locTest(-62,142,1),9));
		islandList.add(main.generateIslandTest(main.locTest(-49,142,-5),main.locTest(-49,143,-3),main.locTest(-48,142,-6),10));
		islandList.add(main.generateIslandTest(main.locTest(-35,142,-6),main.locTest(-35,143,-4),main.locTest(-34,142,-7),11));
		islandList.add(main.generateIslandTest(main.locTest(-16,142,2),main.locTest(-14,143,2),main.locTest(-17,142,1),12));
		islandList.add(main.generateMidleTest(main.locTest(-33, 144, 26),main.locTest(-39, 144, 31), main.locTest(-45, 144, 26), main.locTest(-39, 144, 20), 13,BlockFace.EAST,BlockFace.SOUTH,BlockFace.WEST,BlockFace.NORTH));
		main.map = main.createMap(1,islandList,"Map test","world");
		Bukkit.broadcastMessage("map " + main.map.getName() + " créé");*/
		main.createAllMaps();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				TimerTaskChangeMap task = new TimerTaskChangeMap(main);
				task.runTaskTimer(main, 0, 20);
			}
		}.runTaskLater(main, 20);
		

		
		cancel();
	}
	
}
