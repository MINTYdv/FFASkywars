package com.minty.leemonmc.ffaskywars.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class SuperAnvilHandler implements Listener {

	private FFASkywars main = FFASkywars.getInstance();
	
	private Map<Location, Player> anvilsLocations = new HashMap<>();
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();

		Block block = e.getBlock();
		if(block.getType() == Material.ANVIL)
		{
			String anvilName = SpecialItemsHandler.getSuperAnvil().getItemMeta().getDisplayName();
			if(e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(anvilName))
			{
				if(getAnvilsLocations().values().contains(player)) {
					player.sendMessage("§cTu as déjà placé une table de réparation !");
					e.setCancelled(true);
					return;
				}
				
				if(getAnvilsLocations().containsKey(block.getLocation()))
				{
					getAnvilsLocations().remove(block.getLocation());
				}
				
				getAnvilsLocations().put(block.getLocation(), player);
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		
		Block block = e.getBlock();
		if(block.getType() == Material.ANVIL && getAnvilsLocations().containsKey(block.getLocation()))
		{
			if(player != getAnvilsLocations().get(block.getLocation()))
			{
				player.sendMessage("§cTu ne peux pas détruire la table de réparation d'un autre joueur !");
				e.setCancelled(true);
				return;
			}
			
			e.setCancelled(true);
			block.setType(Material.AIR);
			player.getInventory().addItem(SpecialItemsHandler.getSuperAnvil());
			getAnvilsLocations().remove(block.getLocation());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		Action action = e.getAction();
		
		if(action == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = e.getClickedBlock();
			if(block.getType() == Material.ANVIL && getAnvilsLocations().containsKey(block.getLocation()))
			{
				if(getAnvilsLocations().get(block.getLocation()) != player)
				{
					player.sendMessage("§cTu ne peux pas utiliser la table de réparation d'un autre joueur !");
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onAnvil(PrepareAnvilEvent e)
	{
		if(e.getResult() != null) {
			ItemStack result = e.getResult();
			List<String> lore = new ArrayList<>();
			lore.add("");
			result.getItemMeta().setLore(lore);
			e.setResult(GameManager.normalLoreToEnchantsLore(result, true));
		}
	}
	
	@EventHandler
	public void onUse(InventoryClickEvent e)
	{
		if(!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		
		Player player = (Player) e.getWhoClicked();
		if(e.getInventory().getType() == InventoryType.ANVIL)
		{
			ItemStack it = e.getCurrentItem();
			if(it == null) return;
			
			if(e.getRawSlot() == 2 && it.getType() != Material.AIR)
			{
				it.getItemMeta().setLore(new ArrayList<>());
				player.getInventory().addItem(it);
				
				e.getInventory().setItem(0, new ItemStack(Material.AIR));
				e.getInventory().setItem(1, new ItemStack(Material.AIR));
				e.getInventory().setItem(2, new ItemStack(Material.AIR));
				e.setCancelled(true);
				player.closeInventory();
				
				
				for(Entry<Location, Player> entry : getAnvilsLocations().entrySet())
				{
					if(entry.getValue() == player)
					{
						Block block = player.getWorld().getBlockAt(entry.getKey());
						if(block != null) {
							block.setType(Material.AIR);
						}
						getAnvilsLocations().remove(entry.getKey());
					}
				}
				
			}
		}
	}
	
	public Map<Location, Player> getAnvilsLocations() {
		return anvilsLocations;
	}
	
}
