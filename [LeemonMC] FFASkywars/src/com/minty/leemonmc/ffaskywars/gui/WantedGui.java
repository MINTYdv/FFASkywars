package com.minty.leemonmc.ffaskywars.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.util.GuiBuilder;
import com.minty.leemonmc.core.util.GuiUtils;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;

public class WantedGui implements GuiBuilder {

	private GuiUtils utils;
	private FFASkywars main;
	private Map<Integer, Player> playersSlots = new HashMap<>();
	private Map<Integer, Player> playersSlots2 = new HashMap<>();
	List<Integer> slots2_ = new ArrayList<>();
	List<Integer> slots_ = new ArrayList<>();
	
	public WantedGui(GuiUtils utils) {
		this.utils = utils;
		this.main = FFASkywars.getInstance();
	}
	
	@Override
	public void contents(Player player, Inventory inv) {
		
		slots2_ = new ArrayList<>();
		slots_ = new ArrayList<>();
		playersSlots = new HashMap<>();
		playersSlots2 = new HashMap<>();
		
		inv.setItem(4, utils.pane());
		inv.setItem(13, utils.pane());
		inv.setItem(22, utils.pane());
		inv.setItem(31, utils.pane());
		inv.setItem(40, utils.pane());
		inv.setItem(49, utils.pane());
		
		inv.setItem(0, this.getNonTargetPane());
		inv.setItem(1, this.getNonTargetPane());
		inv.setItem(2, this.getNonTargetPane());
		inv.setItem(3, this.getNonTargetPane());
		
		inv.setItem(5, this.getTargetPane());
		inv.setItem(6, this.getTargetPane());
		inv.setItem(7, this.getTargetPane());
		inv.setItem(8, this.getTargetPane());
		
		//Coté droit
		List<Integer> slots2 = new ArrayList<>();
		slots2.add(14);
		slots2.add(15);
		slots2.add(16);
		slots2.add(17);
		slots2.add(23);
		slots2.add(24);
		slots2.add(25);
		slots2.add(26);
		slots2.add(32);
		slots2.add(33);
		slots2.add(34);
		slots2.add(35);
		slots2.add(41);
		slots2.add(42);
		slots2.add(43);
		slots2.add(44);
		slots2.add(50);
		slots2.add(51);
		slots2.add(52);
		slots2.add(53);
		this.slots2_ = slots2;
		
		FFAData data2 = FFADataHandler.getPlayerData(player);
		if(data2.targetedPlayerList.size() > 0)
		{
			for(int i = 0; i < data2.targetedPlayerList.size(); i++) {
				
				Player p = data2.targetedPlayerList.get(i);
				int slot = slots2.get(i);
				Account account = main.getAPI().getAccountManager().getAccount(p.getUniqueId().toString());
				FFAData pData = FFADataHandler.getPlayerData(p);
				ItemStack it = GuiUtils.getHead(account.getNickedName());
				it.setAmount(pData.getKillStreak());
				ItemMeta itm = it.getItemMeta();
				itm.setDisplayName(account.getPrefixAccordingToSettings() + " " + account.getNickedName());
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("§8- §7Éliminations : §e" + pData.getKillStreak());
				if(pData.getPulpeToGive() <= 1) {
					lore.add("§8- §7Récompense : §6" + pData.getPulpeToGive() + " pulpe");
				}
				else {
					lore.add("§8- §7Récompense : §6" + pData.getPulpeToGive() + " pulpes");
				}
				lore.add("");
				lore.add("§6» §eClic-gauche pour ne plus tracer");
				itm.setLore(lore);
				it.setItemMeta(itm);
				inv.setItem(slot, it);
				
				playersSlots2.put(slot, p);
			}
		}
		
		//Coté gauche
		
		List<Integer> slots = new ArrayList<>();
		slots.add(9);
		slots.add(10);
		slots.add(11);
		slots.add(12);
		slots.add(18);
		slots.add(19);
		slots.add(20);
		slots.add(21);
		slots.add(27);
		slots.add(28);
		slots.add(29);
		slots.add(30);
		slots.add(36);
		slots.add(37);
		slots.add(38);
		slots.add(39);
		slots.add(45);
		slots.add(46);
		slots.add(47);
		slots.add(48);
		this.slots_ = slots;
		
		ArrayList<Player> wantedList = new ArrayList<Player>();
		for(Player p : main.getWantedList()) {
			if(!data2.targetedPlayerList.contains(p)) {
				wantedList.add(p);
			}
		}
		for(int i = 0; i < wantedList.size(); i++)
		{
			Player p = wantedList.get(i);
			if(!p.equals(player)) {
				int slot = slots.get(i);
				main.getAPI().getGuiUtils();
				Account account = main.getAPI().getAccountManager().getAccount(p.getUniqueId().toString());
				FFAData data = FFADataHandler.getPlayerData(p);
				if(!data2.targetedPlayerList.contains(p)) {
					ItemStack it = GuiUtils.getHead(account.getNickedName());
					it.setAmount(data.getKillStreak());
					ItemMeta itm = it.getItemMeta();
					itm.setDisplayName(account.getPrefixAccordingToSettings() + " " + account.getNickedName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("§8- §7Éliminations : §e" + data.getKillStreak());
					if(data.getPulpeToGive() <= 1) {
						lore.add("§8- §7Récompense : §6" + data.getPulpeToGive() + " pulpe");
					}
					else {
						lore.add("§8- §7Récompense : §6" + data.getPulpeToGive() + " pulpes");
					}
					lore.add("");
					lore.add("§6» §eClic-gauche pour tracer");
					itm.setLore(lore);
					it.setItemMeta(itm);
					inv.setItem(slot, it);
				  
				  playersSlots.put(slot, p);
				}
			}
		}
		
		
		
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public String name() {
		return "§c§lWANTED";
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack it, int slot) {
		if(this.slots_.contains(slot)) {
			if(playersSlots.containsKey(slot))
			{
				Player clicked = playersSlots.get(slot);
				FFAData data = FFADataHandler.getPlayerData(player);
				data.targetedPlayerList.add(clicked);
			}
		}
		else if(this.slots2_.contains(slot)) {
			if(playersSlots2.containsKey(slot)) {
				Player clicked = playersSlots2.get(slot);
				FFAData data = FFADataHandler.getPlayerData(player);
				data.targetedPlayerList.remove(clicked);
			}
		}
		main.getAPI().getGuiManager().open(player, WantedGui.class);
		return;
	}

	@Override
	public void onRightClick(Player arg0, Inventory arg1, ItemStack arg2, int arg3) {
		return;
	}
	
	private ItemStack getNonTargetPane() {
		ItemStack it = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 3);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName("§bJoueurs non tracés");
		it.setItemMeta(itm);
		return it;
	}
	
	private ItemStack getTargetPane() {
		ItemStack it = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
		ItemMeta itm = it.getItemMeta();
		itm.setDisplayName("§cJoueurs tracés");
		it.setItemMeta(itm);
		return it;
	}

}
