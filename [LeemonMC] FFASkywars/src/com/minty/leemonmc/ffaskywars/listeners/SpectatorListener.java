package com.minty.leemonmc.ffaskywars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAState;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;

public class SpectatorListener implements Listener {
	FFASkywars main = FFASkywars.getInstance();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if(e.hasItem()) {
			if(e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().hasDisplayName()) {
					if(main.map == null) {
						e.getPlayer().sendMessage("§6§lFFASkywars §f» §cLa carte n'est pas encore génerée!... Patience !");
						e.setCancelled(true);
						return;
					}
					
					//Observer
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lObserver§r§7 ▪ Clic-droit") && e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lObserver§r§7 ▪ Clic-droit") && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						main.getGameManager().setSpectator(e.getPlayer());
					}
					
					
					//Retour to normal
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §r§7 ▪ Clic-droit") && e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lRetour au lobby") && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						main.getGameManager().setLobby(e.getPlayer(), 1);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void PickupItem(PlayerPickupItemEvent event) {
		FFAData data = FFADataHandler.getPlayerData(event.getPlayer());
		if(data.getState() == FFAState.SPECTATOR || data.getState() == FFAState.INLOBBY) {
			event.setCancelled(true);
		}
	}
}
