package com.minty.leemonmc.ffaskywars.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;
import com.minty.leemonmc.ffaskywars.loot.IslandChestTable;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandFFAGive implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		String item = null;
		ArrayList<String> listItem = this.getStringItems();
		ArrayList<ItemStack> listItemStack = this.getItemStackItems();
		int index = 0;
		
		if(!(sender instanceof Player))
		{
			return false;
		}
		
		Player player = (Player) sender;
		String UUID = player.getUniqueId().toString();
		
		if(CoreMain.getInstance().getAccountManager().getAccount(UUID).getRank().getPower() >= Rank.DEVELOPER.getPower()) {
			// Have permission
			
			if(args.length == 0) {
				sendErrorMessage(player);
				return false;
			}
			
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("liste")) {
					for(String str : listItem) {
						TextComponent message = new TextComponent(str);
						message.setColor(ChatColor.BOLD);
						message.setBold(true);
						message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ffagive " + "@p" + " " + str));
						message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/ffagive " + "@p" + " " + str).color(ChatColor.GRAY).italic(true).create()));
						player.spigot().sendMessage(message);
					}
				}
				
				else {
					String playerToGiveStr = args[0];
					Player playerToGive = null;
					if(playerToGiveStr.equals("@p")) {
						playerToGive = player;
					}
					else {
						playerToGive = Bukkit.getPlayer(playerToGiveStr);
					}
					
					for(String str : listItem) {
						if(args[1].equalsIgnoreCase(str)) {
							item = str;
							index = listItem.indexOf(str);
							break;
						}
					}
					
					
					if(item == null) {
						sendErrorMessage(player);
						return false;
					}
					
					//Pour le @a
					if(playerToGive == null) {
						if(playerToGiveStr.equals("@a")) {
							if(args.length >= 3) {
								int amount = Integer.parseInt(args[2]);
								for(int i = 1;i <= amount;i++) {
									for(Player p : FFASkywars.getInstance().getGameManager().getPlayingPlayer()) {
										p.getInventory().addItem(listItemStack.get(index));
									}
								}
							}
							else {
								for(Player p : FFASkywars.getInstance().getGameManager().getPlayingPlayer()) {
									p.getInventory().addItem(listItemStack.get(index));
								}
							}
							return true;
						}
						return false;
					}
					
						
					//Pour un joueur
					if(args.length >= 3) {
						int amount = Integer.parseInt(args[2]);
						for(int i = 1;i <= amount;i++) {
							playerToGive.getInventory().addItem(listItemStack.get(index));
						}
						return true;
					}
					
					playerToGive.getInventory().addItem(listItemStack.get(index));
					return true;
				}
			}
		}
		
		return false;
	}
	


	private void sendErrorMessage(Player player) {
		player.sendMessage("§cUtilisation: /ffagive <liste/joueur> <item> [nombre]");
	}
	
	private ArrayList<String> getStringItems(){
		ArrayList<String> listItem = new ArrayList<String>();
		listItem.add("thor");
		listItem.add("bridgeegg");
		listItem.add("coconut");
		listItem.add("tnt");
		listItem.add("anvil");
		listItem.add("fireBall");
		listItem.add("luckyBlockNormal");
		listItem.add("luckyBlockGood");
		return listItem;
	}
	
	private ArrayList<ItemStack> getItemStackItems() {
		IslandChestTable islandTable = new IslandChestTable();
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(SpecialItemsHandler.getThor());
		items.add(SpecialItemsHandler.getBridgeEgg());
		items.add(SpecialItemsHandler.getCoconut());
		items.add(SpecialItemsHandler.getTnt(1));
		items.add(SpecialItemsHandler.getSuperAnvil());
		items.add(SpecialItemsHandler.getFireBall());
		items.add(SpecialItemsHandler.getNormalLuckyBlock());
		items.add(SpecialItemsHandler.getGoodLuckyBlock());
		return items;
	}

}
