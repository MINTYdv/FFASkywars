package com.minty.leemonmc.ffaskywars.loot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface ChestLootTable {

	public List<ItemStack> content();
	public int getMaxItems();
	public int getMinItems();
	
}
