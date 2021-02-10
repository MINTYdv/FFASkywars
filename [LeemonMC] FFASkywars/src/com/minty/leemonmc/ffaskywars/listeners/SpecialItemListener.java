package com.minty.leemonmc.ffaskywars.listeners;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;
import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;
import com.minty.leemonmc.ffaskywars.task.TimerTaskBridgeEgg;

public class SpecialItemListener implements Listener {
	
	FFASkywars main;
	public CoreMain leemonmc;
	
	public SpecialItemListener(FFASkywars main) {
		this.main = main;
		leemonmc = (CoreMain) Bukkit.getServer().getPluginManager().getPlugin("LeemonCore");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if(e.hasItem()) {
			if(e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().hasDisplayName()) {
					
					// Normal lucky block
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpecialItemsHandler.getNormalLuckyBlock().getItemMeta().getDisplayName())) {
						e.setCancelled(true);
						Player player = e.getPlayer();
						if(player.getInventory().getItemInMainHand().getAmount() <= 1) player.getInventory().setItemInMainHand(null);
						else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						main.getGameManager().lootNormal(e.getPlayer().getLocation());
					}
					
					// Good lucky block
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpecialItemsHandler.getGoodLuckyBlock().getItemMeta().getDisplayName())) {
						Player player = e.getPlayer();
						if(player.getInventory().getItemInMainHand().getAmount() <= 1) player.getInventory().setItemInMainHand(null);
						else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						main.getGameManager().lootGood(e.getPlayer().getLocation());
						e.setCancelled(true);
					}
					
					// Thor
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpecialItemsHandler.getThor().getItemMeta().getDisplayName())) {
						Player player = e.getPlayer();
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(SpecialItemsHandler.getThor().getItemMeta().getDisplayName())) {
							Block block = player.getTargetBlock((Set<Material>) null, 100);
							Location bl = block.getLocation();
							if(player.getInventory().getItemInMainHand().getAmount() <= 1) player.getInventory().setItemInMainHand(null);
							else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							for(Entity entity : bl.getWorld().getNearbyEntities(bl, 4, 4, 4)) {
								if(entity instanceof Player) {
									Player p = (Player) entity;
									FFAData data = FFADataHandler.getPlayerData(p);
									data.setLastDamager(e.getPlayer());
								}
							}
							bl.getWorld().strikeLightning(bl);
						}
					}
					
					// Bridge egg
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpecialItemsHandler.getBridgeEgg().getItemMeta().getDisplayName()) && e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
						TimerTaskBridgeEgg task = new TimerTaskBridgeEgg(main,e.getPlayer().getLocation());
						task.runTaskTimer(main, 2, 1);
					}
					
					// Coconut
					String name = SpecialItemsHandler.getCoconut().getItemMeta().getDisplayName();
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name) && e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
						Player player = e.getPlayer();
						if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(name)) {
							if(player.getInventory().getItemInMainHand().getAmount() <= 1) player.getInventory().setItemInMainHand(null);
							else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							if(player.getHealth() + 2 > player.getMaxHealth()) {
								player.setHealth(20);
							}
							else {
								player.setHealth(player.getHealth() + 4);
							}
							player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 50, 1);
							e.setCancelled(true);
						}
					}
					
				}
			}
		}
		
	}
	
}
