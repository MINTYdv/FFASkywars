package com.minty.leemonmc.ffaskywars.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class FFAMap {
	FFASkywars main;
	int id;
	String name;
	String worldName;
	ArrayList<FFAIsland> islandList = new ArrayList<FFAIsland>();
	private Map<FFAChest, ChestType> chestTypes = new HashMap<>();
	Location spectatorSpawn;
	
	public FFAMap(FFASkywars _main,int _id, ArrayList<FFAIsland> _islandList,String _name,String _worldName,Location specSpawn) {
		this.main = _main;
		this.id = _id;
		this.islandList = _islandList;
		this.name = _name;
		this.worldName = _worldName;
		spectatorSpawn = specSpawn;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<FFAIsland> getIslands(){
		return islandList;
	}
	
	public boolean anyIslandFree() {
		for(FFAIsland island : islandList) {
			if(!island.isOccupated()) {
				return true;
			}
		}
		return false;
	}

	
	public FFAChest getChestByLocation(Location location) {
		FFAChest chest = null;
		for(FFAIsland island : islandList) {
			if(island.getChestByLocation(location) != null) {
				chest = island.getChestByLocation(location);
			}
		}
		return chest;
	}
	
	
	
	public ChestType setChestType(FFAChest chest, ChestType newState)
	{
		if(getChestTypes().containsKey(chest))
		{
			getChestTypes().remove(chest);
		}
		getChestTypes().put(chest, newState);
		
		return getChestType(chest);
	}
	
	public ChestType getChestType(FFAChest chest)
	{
		if(!getChestTypes().containsKey(chest))
		{
			getChestTypes().put(chest, ChestType.NORMAL);
		}
		return getChestTypes().get(chest);
	}
		
	public Map<FFAChest, ChestType> getChestTypes() {
		return chestTypes;
	}
	
	public World getWorld() {
		return Bukkit.getWorld(worldName);
	}
	
	public Location getSpectatorSpawn() {
		return this.spectatorSpawn;
	}
}
