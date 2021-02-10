package com.minty.leemonmc.ffaskywars.core;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class MyBlock {
	Block block;

	public MyBlock(Block b2) {
		this.block = b2;
	}
	
	public void restoreType() {
		this.block.setType(Material.AIR);
	}
	
	public Block getBlock() {
		return block;
	}
	
}
