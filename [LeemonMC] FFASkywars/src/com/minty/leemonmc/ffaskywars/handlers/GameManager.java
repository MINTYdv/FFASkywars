package com.minty.leemonmc.ffaskywars.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.stats.StatsData;
import com.minty.leemonmc.core.stats.StatsDataHandler;
import com.minty.leemonmc.core.util.LeemonUtils;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAChest;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAIsland;
import com.minty.leemonmc.ffaskywars.core.FFAState;
import com.minty.leemonmc.ffaskywars.core.InvinsibilityState;
import com.minty.leemonmc.ffaskywars.core.MyBlock;
import com.minty.leemonmc.ffaskywars.core.PlayerDieCause;
import com.minty.leemonmc.ffaskywars.events.FFASkywarsPlayerDeathEvent;
import com.minty.leemonmc.ffaskywars.events.FFASkywarsPlayerSpawnEvent;
import com.minty.leemonmc.ffaskywars.events.FFAStateChangeEvent;
import com.minty.leemonmc.ffaskywars.loot.ChestLootTable;
import com.minty.leemonmc.ffaskywars.loot.IslandChestTable;
import com.minty.leemonmc.ffaskywars.loot.MiddleChestTable;
import com.minty.leemonmc.ffaskywars.task.TimerTaskBlock;
import com.minty.leemonmc.ffaskywars.task.TimerTaskLoot;
import com.minty.leemonmc.ffaskywars.task.TimerTaskOccupedIsland;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GameManager {

	private FFASkywars main;

	private ArrayList<MyBlock> blockList = new ArrayList<MyBlock>();
	private ArrayList<TimerTaskBlock> taskList = new ArrayList<TimerTaskBlock>();
	
	public GameManager(FFASkywars _main) {
		main = _main;
	}

	public void setLobby(Player player, int netherStarDelay)
	{
		if(player.isDead()) {
			player.spigot().respawn();
		}
		
		Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
		if(account.isModEnabled()) return;
		
		Inventory pInv = player.getInventory();
		pInv.clear();
		
		player.setAllowFlight(false);
		for(Player player_ : Bukkit.getOnlinePlayers()) {
			player_.showPlayer(player);
		}
		
		new BukkitRunnable() {

			@Override
			public void run()
			{
				FFAData data = FFADataHandler.getPlayerData(player);
				Inventory pInv = player.getInventory();
				
				player.teleport(main.lobbyLocation);
				
				player.setGameMode(GameMode.ADVENTURE);
				player.setMaxHealth(2);
				player.setHealth(2);
				player.setLevel(0);
				player.setFireTicks(0);
				main.getAPI().getLeemonUtils().removeAllEffects(player);
				
				pInv.setItem(8,CoreMain.getInstance().getLeemonUtils().getLobbyItem());
				
				data.setState(FFAState.INLOBBY);
				data.setInvisibilityState(InvinsibilityState.NORMAL);
				cancel();
			}
        	
        }.runTaskTimer(main, 5, 1);
        
        new BukkitRunnable() {
        	
        	@Override
        	public void run() {
        		Inventory pInv = player.getInventory();
				pInv.clear();
        		pInv.setItem(0, main.itemStackGenerator(Material.NETHER_STAR, 1, "§e§lJouer§r§7 ▪ Clic-droit", null, 0));
        		pInv.setItem(4, main.itemStackGenerator(Material.SULPHUR, 1, "§b§lObserver§r§7 ▪ Clic-droit", null, 0));
        		pInv.setItem(3, main.itemStackGenerator(Material.SKULL_ITEM, 1, "§c§lWanted§r§7 ▪ Clic-droit", null, 0));
        		pInv.setItem(8,CoreMain.getInstance().getLeemonUtils().getLobbyItem());
        		cancel();
        	}
        	
        }.runTaskLater(main, netherStarDelay);
	}
	
	public void teleportIsland(Player player,FFAIsland island) {
		
		FFAData data = FFADataHandler.getPlayerData(player);
		
		player.teleport(island.getSpawn());
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		player.setMaxHealth(20);
		player.setHealth(20);
		data.setState(FFAState.INGAME);
		data.setInvisibilityState(InvinsibilityState.INVICIBLE);
		island.regenChest(player);
		TimerTaskOccupedIsland task = new TimerTaskOccupedIsland(main,player,island);
        task.runTaskTimer(main, 0, 1);
		
        main.createHelix(player.getLocation());
        
        FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerSpawnEvent(player, player.getLocation(), island));
	}

	public void lootChest(Location location,Player player) {
		
		FFAChest chest = main.map.getChestByLocation(location);
		if(chest == null) {
			return;
		}
		chest.loot(player);
		
	}
	
	public void regenNormalChest() {
		for(FFAIsland island : main.map.getIslands()) {
			if(!island.isMidle()) {
				island.regenChest();
			}
		}
	}
	
	public void regenGoodChest() {
		for(FFAIsland island : main.map.getIslands()) {
			if(island.isMidle()) {
				island.regenChest();
			}
		}
	}
	
	
	public ArrayList<MyBlock> getBlockList(){
		return blockList;
	}
	
	public void addBlockInList(MyBlock b) {
		blockList.add(b);
		TimerTaskBlock task = new TimerTaskBlock(main,b);
		task.runTaskTimer(main, 0, 20);
		taskList.add(task);
	}
	
	public void deleteBlockInList(MyBlock b) {
		blockList.remove(b);
		for(TimerTaskBlock task : this.taskList) {
			if(task.getBlock().getBlock().getLocation().equals(b.getBlock().getLocation())) {
				this.taskList.remove(task);
				break;
			}
		}
	}

	public boolean isBlockInList(Block block) {
		for(MyBlock myBlock : this.getBlockList()) {
			if(myBlock.getBlock().getLocation().equals(block.getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	public MyBlock getBlockWithLocation(Location loc) {
		for(MyBlock myBlock : this.getBlockList()) {
			if(myBlock.getBlock().getLocation().equals(loc)) {
				return myBlock;
			}
		}
		return null;
	}

	public void deleteTask(TimerTaskBlock task) {
		this.taskList.remove(task);
	}

	public boolean isBlockInListWithLocation(Location loc) {
		for(MyBlock myBlock : this.getBlockList()) {
			if(myBlock.getBlock().getLocation().equals(loc)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void playerDie(Player player, PlayerDieCause dieCause)
	{
		player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 50, 1);
		Inventory inv = player.getInventory();
		Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
		FFAData playerData = FFADataHandler.getPlayerData(player);
		
		if(dieCause == PlayerDieCause.VOID) {
			if(playerData.getLastDamager() != null) {
				Account killerAccount = main.getAPI().getAccountManager().getAccount(playerData.getLastDamager().getUniqueId().toString());
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussée dans le vide par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussé dans le vide par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				playerDrop(player,playerData.getLastDamager().getLocation());
				FFAData killerData = FFADataHandler.getPlayerData(playerData.getLastDamager());
				killerData.addKills(1);
				killerData.setLastDamager(null);
				Player killer = playerData.getLastDamager();
				killer.setHealth(killer.getMaxHealth());
				addKillPulpe(killer,player);
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, killer, player.getLocation(), dieCause));
			}
			
			
			else {
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7est morte");
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7est mort");
				}
				playerDrop(player,player.getLocation());
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
			}
			
			
		}
		
		if(dieCause == PlayerDieCause.FIRE) {
			if(playerData.getLastDamager() != null) {
				Account killerAccount = main.getAPI().getAccountManager().getAccount(playerData.getLastDamager().getUniqueId().toString());
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été carbonisée par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été carbonisé par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				playerDrop(player,player.getLocation());
				FFAData killerData = FFADataHandler.getPlayerData(playerData.getLastDamager());
				killerData.addKills(1);
				killerData.setLastDamager(null);
				Player killer = playerData.getLastDamager();
				killer.setHealth(killer.getMaxHealth());
				addKillPulpe(killer,player);
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, killer, player.getLocation(), dieCause));
			}
			
			
			else {
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été carbonisée");
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été carbonisé");
				}
				playerDrop(player,player.getLocation());
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
			}
			
			
		}
		
		
		if(dieCause == PlayerDieCause.EXPLOSION) {
			if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a explosée");
			}
			else {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a explosé");
			}
			playerDrop(player,player.getLocation());
			FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
		}
		
		if(dieCause == PlayerDieCause.PROJECTILE) {
			if(playerData.getLastDamager() != null) {
				Account killerAccount = main.getAPI().getAccountManager().getAccount(playerData.getLastDamager().getUniqueId().toString());
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussée dans le vide avec un projectile par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussé dans le vide avec un projectile par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				playerDrop(player,playerData.getLastDamager().getLocation());
				
				FFAData killerData = FFADataHandler.getPlayerData(playerData.getLastDamager());
				killerData.addKills(1);
				killerData.setLastDamager(null);
				Player killer = playerData.getLastDamager();
				killer.setHealth(killer.getMaxHealth());
				addKillPulpe(killer,player);
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, killer, player.getLocation(), dieCause));
			}
			
			
			else {
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussée dans le vide avec un projectile");
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été poussé dans le vide avec un projectile");
				}
				playerDrop(player,player.getLocation());
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
			}
		}
		
		if(dieCause == PlayerDieCause.ELECTRICITY) {
			if(playerData.getLastDamager() != null) {
				Account killerAccount = main.getAPI().getAccountManager().getAccount(playerData.getLastDamager().getUniqueId().toString());
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été éléctrocutée par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été éléctrocuté par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
				}
				playerDrop(player,playerData.getLastDamager().getLocation());
				
				FFAData killerData = FFADataHandler.getPlayerData(playerData.getLastDamager());
				killerData.addKills(1);
				killerData.setLastDamager(null);
				Player killer = playerData.getLastDamager();
				killer.setHealth(killer.getMaxHealth());
				addKillPulpe(killer,player);
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, killer, player.getLocation(), dieCause));
			}
			
			
			else {
				if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été éléctrocutée");
				}
				else {
					Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7a été éléctrocuté");
				}
				playerDrop(player,player.getLocation());
				FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
			}
		}
		
		if(dieCause == PlayerDieCause.PLAYER_KILL) {
			
			Player killer = playerData.getLastDamager();
			Account killerAccount = main.getAPI().getAccountManager().getAccount(killer.getUniqueId().toString());
			
			killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
			
			if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + "§7 a été tuée par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
			}
			else {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + "§7 a été tué par " + killerAccount.getPrefixAccordingToSettings() + " " + killerAccount.getNickedName());
			}
			
			killer.setHealth(killer.getMaxHealth());
			addKillPulpe(killer,player);
			FFADataHandler.getPlayerData(killer).addKills(1);
			FFADataHandler.getPlayerData(killer).setLastDamager(null);
			
			playerDrop(player,player.getLocation());
			FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, killer, player.getLocation(), dieCause));
		}
		
		if(dieCause == PlayerDieCause.OTHER) {
			
			if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE")) {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7est morte");
			}
			else {
				Bukkit.broadcastMessage(account.getPrefixAccordingToSettings() + " " + account.getNickedName() + " §7est mort");
			}
			playerDrop(player,player.getLocation());
			FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFASkywarsPlayerDeathEvent(player, null, player.getLocation(), dieCause));
		}
		
		if(account.getSetting("global_gender").equalsIgnoreCase("FEMALE"))
		{
			player.sendTitle("§c", "§4☠ §cVous êtes morte §4☠");
		} else {
			player.sendTitle("§c", "§4☠ §cVous êtes mort §4☠");
		}
		
		StatsData data = StatsDataHandler.getPlayerStats(player);
		data.addStat("deaths", 1);
		
		FFAData ffaData = FFADataHandler.getPlayerData(player);
		ffaData.setKillStreak(0);
		ffaData.setLastDamager(null);

		resetLastAllDamagerForAPlayer(player);
		
		new BukkitRunnable() {

			@Override
			public void run()
			{
				for(Entry<Location, Player> entry : main.getSuperAnvilHandler().getAnvilsLocations().entrySet())
				{
					if(entry.getValue() == player)
					{
						Block block = player.getWorld().getBlockAt(entry.getKey());
						if(block != null) {
							block.setType(Material.AIR);
						}
						main.getSuperAnvilHandler().getAnvilsLocations().remove(entry.getKey());
					}
				}
			}
			
		}.runTaskLater(main, 10L);

		this.setLobby(player, 20);
	}
	
	private void playerDrop(Player player,Location location) {
		for(Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
			if(entity instanceof Player) {
				Player p = (Player) entity;
				if(FFADataHandler.getPlayerData(p).getState() == FFAState.INLOBBY || FFADataHandler.getPlayerData(p).getState() == FFAState.SPECTATOR) {
					location = player.getLocation();
				}
			}
		}
		Inventory inv = player.getInventory();
		for(ItemStack item : inv.getContents()) {
			if(item != null) {
				location.getWorld().dropItem(location, item);
			}
		}
	}
	
	public static ItemStack normalLoreToEnchantsLore(ItemStack it, boolean clear)
	{
		ItemMeta meta = it.getItemMeta();
		
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		List<String> lore = new ArrayList<>();
		if(it.hasItemMeta() && it.getItemMeta().hasLore() && clear == false) {
			lore = meta.getLore();
		}
		
		for(Entry<Enchantment, Integer> entry : it.getEnchantments().entrySet())
		{
			Enchantment enchant = entry.getKey();
			Integer level = entry.getValue();
			
			LeemonUtils utils = CoreMain.getInstance().getLeemonUtils();
			
			String name = "§8» §e" + utils.enchantmentName(enchant);
			
			if(level <= 1) {
				name += " §b1";
			}
			if(level == 2) {
				name += " §62";
			}
			if(level >= 3) {
				name += " §c" + level;
			}
			if(lore != null) {
				lore.add(name);
			}
		}
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	public ArrayList<Player> getPlayingPlayer() {
		if(Bukkit.getOnlinePlayers().size() == 0) return null;
		
		ArrayList<Player> playingPlayerList = new ArrayList<Player>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			FFAData data = FFADataHandler.getPlayerData(player);
			
			if(data != null && account != null) {
				if(data.getState() != null) {
					if(!account.isModEnabled() && data.getState().equals(FFAState.INGAME)) {
						playingPlayerList.add(player);
					}
				}
			}
		}
		
		return playingPlayerList;
	}
	
	private void addKillPulpe(Player player,Player killed) {
		Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
		Account killedAcount = main.getAPI().getAccountManager().getAccount(killed.getUniqueId().toString());
		FFAData killedData = FFADataHandler.getPlayerData(killed);
		int amount;
		if(killedData.getPulpeToGive() <= 0) {
			amount = 1;
		}
		else {
			amount = killedData.getPulpeToGive();
		}
		account.addPulpe(amount);
		if(amount <= 1) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6Gain§e: §e+" + amount +" pulpe §8(§7Kill§8)"));
		}
		else {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6Gain§e: §e+" + amount +" pulpes §8(§7Kill§8)"));
		}
		
	}
	
	public FFAData getPlayerData(Player player) {
		FFAData playerData = null;
		for(FFAData data : main.playerDataList) {
			if(data.getPlayer().equals(player)) {
				playerData = data;
			}
		}
		if(playerData == null) {
			playerData = new FFAData(player);
		}
		return playerData;
	}
	
	public void removePlayerData(Player player) {
		for(FFAData data : main.playerDataList) {
			if(data.getPlayer().equals(player)) {
				main.playerDataList.remove(data);
			}
		}
	}
	
	public ArrayList<Player> getLobbyPlayers() {
		ArrayList<Player> loobyPlayers = new ArrayList<Player>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			FFAData data = FFADataHandler.getPlayerData(player);
			
			if(data != null && account != null) {
				if(!account.isModEnabled() && data.getState().equals(FFAState.INLOBBY)) {
					loobyPlayers.add(player);
				}
			}
		}
		return loobyPlayers;
	}
	
	public void resetLastAllDamagerForAPlayer(Player player) {
		for(Player online : Bukkit.getOnlinePlayers()){
			FFAData data = FFADataHandler.getPlayerData(online);
			if(data.getLastDamager() != null) {
				if(data.getLastDamager().getName().equalsIgnoreCase(player.getName())) {
					data.setLastDamager(null);
				}
			}
			if(data.targetedPlayerList.size() > 0) {
				if(data.targetedPlayerList.contains(player)) {
					data.targetedPlayerList.remove(player);
				}
			}
		}
			
	}
	
	public ArrayList<Player> getNonModPlayer(){
		ArrayList<Player> playerList = new ArrayList<Player>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			FFAData data = FFADataHandler.getPlayerData(player);
			
			if(data != null && account != null) {
				if(!account.isModEnabled()) {
					playerList.add(player);
				}
			}
		}
		return playerList;
	}
	
	public void setSpectator(Player player) {
		if(player == null) return;
		FFAData data = FFADataHandler.getPlayerData(player);
		data.setState(FFAState.SPECTATOR);
		player.teleport(main.map.getSpectatorSpawn());
		Inventory inv = player.getInventory();
		inv.clear();
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				inv.setItem(4, main.itemStackGenerator(Material.BARRIER, 1, "§c§lQuitter §r§7 ▪ Clic-droit", null, 0));
			}
		}.runTaskLater(main, 20);
	}
	
	public void hideSpectatorPlayers() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			FFAData data = FFADataHandler.getPlayerData(player);
			if(data != null) {
				if(data.getState() == FFAState.SPECTATOR) {
					for(Player player_ : Bukkit.getOnlinePlayers()) {
						player_.hidePlayer(player);
					}
				}
			}
		}
	}
	
	public void showPlayerNonSpecator() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			FFAData data = FFADataHandler.getPlayerData(player);
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			if(data != null) {
				if(data.getState() == FFAState.INGAME && !account.isModEnabled()) {
					for(Player player_ : Bukkit.getOnlinePlayers()) {
						player_.showPlayer(player);
					}
				}
			}
		}
	}
	
	public void lootNormal(Location loc) {
		ChestLootTable table;
		table = new IslandChestTable();
		Random r = new Random();
		int itemsAmount = r.nextInt(table.getMaxItems() - table.getMinItems()) + table.getMinItems();
		Location lootLoc = loc;
		lootLoc.setY(lootLoc.getY() + 1);

		ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < itemsAmount; i++)
		{
			Item it1 = lootLoc.getWorld().dropItem(lootLoc,randomItemInTable(table));
			items.add(it1);
		}
        TimerTaskLoot task = new TimerTaskLoot(main,items,loc);
        task.runTaskTimer(main, 5, 1);
		
        lootLoc.setY(lootLoc.getY() - 1);
        loc = lootLoc;
	}
	
	public void lootGood(Location loc) {
		ChestLootTable table;
		table = new MiddleChestTable();
		Random r = new Random();
		int itemsAmount = r.nextInt(table.getMaxItems() - table.getMinItems()) + table.getMinItems();
		Location lootLoc = loc;
		lootLoc.setY(lootLoc.getY() + 1);

		ArrayList<Item> items = new ArrayList<Item>();
		for(int i = 0; i < itemsAmount; i++)
		{
			Item it1 = lootLoc.getWorld().dropItem(lootLoc,randomItemInTable(table));
			items.add(it1);
		}
        TimerTaskLoot task = new TimerTaskLoot(main,items,loc);
        task.runTaskTimer(main, 5, 1);
		
        lootLoc.setY(lootLoc.getY() - 1);
        loc = lootLoc;
	}
	
	private ItemStack randomItemInTable(ChestLootTable table)
	{
		Random rand = new Random();
		ItemStack randomElement = table.content().get(rand.nextInt(table.content().size()));
		
		
		return GameManager.normalLoreToEnchantsLore(randomElement, false);
	}
	
}
