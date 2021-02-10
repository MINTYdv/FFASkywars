package com.minty.leemonmc.ffaskywars.core;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.handlers.GameManager;
import com.minty.leemonmc.ffaskywars.loot.ChestLootTable;
import com.minty.leemonmc.ffaskywars.loot.IslandChestTable;
import com.minty.leemonmc.ffaskywars.loot.MiddleChestTable;
import com.minty.leemonmc.ffaskywars.task.TimerTaskLoot;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FFAChest {
	
	Location location;
	Chest chest = null;
	Inventory chestInv;
	FFASkywars main;
	ChestType chestType;
	ChestLootTable table;
	BlockFace face;
	boolean canOpentheChest;
	Player playerCanOpen;
	
	public FFAChest(Location chestLoc,FFASkywars _main,ChestType _chestType,BlockFace facingDirection) {
		location = chestLoc;
		canOpentheChest = true;
		this.main = _main;
		this.chestType = _chestType;
		face = facingDirection;
		if(chestType.equals(ChestType.GOOD))
		{
			location.getBlock().setType(Material.ENDER_CHEST);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					Block block = getLocation().getBlock();
					BlockState state = block.getState();
					EnderChest enderChest = new EnderChest(face);
					state.setData(enderChest);
					state.update();
					table = new MiddleChestTable();
					
				}
			}.runTaskLater(main, 5);
		}
		else if(chestType.equals(ChestType.NORMAL)) {
			location.getBlock().setType(Material.CHEST);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					chest = (Chest) location.getBlock().getState();
					table = new IslandChestTable();
				}
			}.runTaskLater(main, 5);
			
		}
		
	}
	
	
	public Location getLocation() {
		return location;
	}
	
	public boolean isChestHere() {
		if(location.getBlock().getType().equals(Material.CHEST) || location.getBlock().getType().equals(Material.ENDER_CHEST)) {
			return true;
		}
		return false;
	}
	
	public void generateChestAndLoot() {
		if(this.chestType.equals(ChestType.NORMAL)) {
			this.getLocation().getBlock().setType(Material.CHEST);
		}
		if(this.chestType.equals(ChestType.GOOD))
		{
			this.getLocation().getBlock().setType(Material.ENDER_CHEST);
			Block block = this.getLocation().getBlock();
			BlockState state = block.getState();
			EnderChest enderChest = new EnderChest(this.face);
			state.setData(enderChest);
			state.update();
		}
		
	}
	
	private ItemStack randomItemInTable(ChestLootTable table)
	{
		Random rand = new Random();
		ItemStack randomElement = table.content().get(rand.nextInt(table.content().size()));
		
		
		return GameManager.normalLoreToEnchantsLore(randomElement, false);
	}
	
	public void loot(Player playerOpen) {
		
		if(!canOpentheChest ) {
			if(playerCanOpen != null && playerOpen != null) {
				if(!playerOpen.equals(playerCanOpen)) {
					playerOpen.sendMessage("§cTu ne peux pas ouvrir les coffres de quelqu'un dautre");
					return;
				}
			}
			else {
				playerOpen.sendMessage("§cTu ne peux pas ouvrir les coffres de quelqu'un dautre");
				return;
			}
		}
		
		this.getLocation().getBlock().setType(Material.AIR);
		
		Random r = new Random();
		int itemsAmount = r.nextInt(table.getMaxItems() - table.getMinItems()) + table.getMinItems();
		Location lootLoc = this.getLocation();
		lootLoc.setY(lootLoc.getY() + 1);

		ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < itemsAmount; i++)
		{
			Item it1 = lootLoc.getWorld().dropItem(lootLoc,randomItemInTable(table));
			items.add(it1);
		}
        TimerTaskLoot task = new TimerTaskLoot(main,items,this.location);
        task.runTaskTimer(main, 5, 1);
		
        lootLoc.setY(lootLoc.getY() - 1);
        this.location = lootLoc;
        
        if(doIGivePulpe()) {
        	for(Entity entity : this.getLocation().getWorld().getNearbyEntities(this.getLocation(), 5, 5, 5)) {
        		if(entity instanceof Player) {
        			Player player = (Player) entity;
        			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
        			int amount = r.nextInt(5 - 2) + 2;
        			account.addPulpe(amount);
        			if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
        				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eVous avez trouvrée " + amount + " pulpes"));
        			}
        			else {
        				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eVous avez trouvré " + amount + " pulpes"));
        			}
        			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HARP, 100, 1);
        			
        			break;
        		}
        	}
        }

	}
	
	private boolean doIGivePulpe() {
		Random r = new Random();
		//5 % de chance
		if(r.nextInt(99) <= 15) {
			return true;
		}
		return false;
	}
}
