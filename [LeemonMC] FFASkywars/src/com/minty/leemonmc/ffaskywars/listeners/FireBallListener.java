package com.minty.leemonmc.ffaskywars.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;

public class FireBallListener implements Listener {

	@EventHandler
	public void onInterract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(e.hasItem()) {
				if(e.getItem().hasItemMeta()) {
					if(e.getItem().getItemMeta().hasDisplayName()) {
						if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(SpecialItemsHandler.getFireBall().getItemMeta().getDisplayName())) {
							Location loc = player.getLocation();
							Vector vector = loc.getDirection();
							vector.multiply(2);
							Entity f = loc.getWorld().spawnEntity(loc.add(vector), EntityType.FIREBALL);
							f.setVelocity(loc.getDirection().multiply(0.5));
							if(player.getInventory().getItemInMainHand().getAmount() <= 1) player.getInventory().setItemInMainHand(null);
							else player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						}
					}
				}
			}
		}
	}
	
}
