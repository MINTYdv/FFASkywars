package com.minty.leemonmc.ffaskywars.core;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class FFAIsland {
	
	FFASkywars main;
	int id;
	Location spawnPointLocation;
	ArrayList<FFAChest> chestList = new ArrayList<FFAChest>();
	boolean occupated;
	boolean isMidle;
	
	public FFAIsland(FFASkywars _main,int _id,Location _spawnPointLocation, ArrayList<FFAChest> _chestList,boolean isMidle) {
		this.main = _main;
		this.id = _id;
		this.spawnPointLocation = _spawnPointLocation;
		this.chestList = _chestList;
		this.isMidle = isMidle;
	}
	
	public boolean isOccupated() {
		return occupated;
	}
	
	public void setOccupated(boolean b) {
		occupated = b;
		for(FFAChest chest : this.chestList) {
			if(b) chest.canOpentheChest = false;
			if(!b) chest.canOpentheChest = true;
		}
	}
	
	public Location getSpawn() {
		return spawnPointLocation;
	}

	public void regenChest() {
		
		for(FFAChest chest : chestList) {
			chest.generateChestAndLoot();
		}
		
	}
	
	public void regenChest(Player player) {
		
		for(FFAChest chest : chestList) {
			chest.generateChestAndLoot();
			chest.playerCanOpen = player;
		}
		
	}

	public FFAChest getChestByLocation(Location location) {
		
		for(FFAChest chest : chestList) {
			if(chest.getLocation().equals(location)) {
				return chest;
			}
		}
		return null;
	}
	
	public boolean isMidle() {
		if(this.isMidle) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
