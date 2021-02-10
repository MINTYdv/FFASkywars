package com.minty.leemonmc.ffaskywars.core;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.stats.StatsData;
import com.minty.leemonmc.core.stats.StatsDataHandler;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.events.FFAStateChangeEvent;

public class FFAData {
	Player player;
	int killStreak;
	int pulpe;
	Player lastDamager;
	public ArrayList<Player> targetedPlayerList;
	
	InvinsibilityState invisibilityState;
	FFAState state;
	
	FFASkywars main;
	
	public FFAData(Player player) {
		this.player = player;
		this.killStreak = 0;
		main = FFASkywars.getInstance();
		lastDamager = null;
		targetedPlayerList = new ArrayList<Player>();
	}
	
	public void setInvisibilityState(InvinsibilityState invisibilityState) {
		this.invisibilityState = invisibilityState;
	}
	
	public InvinsibilityState getInvisibilityState() {
		return invisibilityState;
	}
	
	public void setState(FFAState state) {
		FFASkywars.getInstance().getServer().getPluginManager().callEvent(new FFAStateChangeEvent(player, state, this.getState()));
		this.state = state;
	}
	
	public FFAState getState() {
		return state;
	}
	
	public double getRatio() {
		StatsData data = StatsDataHandler.getPlayerStats(player);
		try {
			float ratio = (float) data.getStat("kills") / (float) data.getStat("deaths");
			if(data.getStat("deaths") == 0 && data.getStat("kills") >= 1) {
				return -1;
			}
			return (double) Math.round(ratio * 100.0) / 100.0;
		} catch(Exception e) {
			return 1;
		}

	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getKillStreak() {
		return killStreak;
	}
	
	public void addKillStreak(int i) {
		killStreak = killStreak + i;
	}
	
	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
		if(killStreak == 0) {
			main.wantedList.remove(player);
		}
	}
	
	public void addKills(int i) {
		StatsData data = StatsDataHandler.getPlayerStats(player);
		
		data.addStat("kills", i);
		this.addKillStreak(i);
		if(killStreak % 5 == 0) {
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			Bukkit.broadcastMessage("§7§m---------------------------------------------------");
			Bukkit.broadcastMessage("                                                §c§lWANTED");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§7Le joueur §e" + account.getNickedName() + "§7a atteint §e" + this.killStreak + " kills §7d'affilé");
			Bukkit.broadcastMessage("§7le joueur qui l'éliminera remportera une prime de §e"+ this.getPulpeToGive() + " pulpes §7!");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§7§m---------------------------------------------------");
		}
		
		if(this.getKillStreak() >= 5) {
			if(!main.wantedList.contains(player)) {
				main.wantedList.add(player);
			}
		}
		this.setNewBestKillStreak(this.killStreak);
	}

	public int getPulpeToGive() {
		return killStreak;
	}
	
	public void setLastDamager(Player player) {
		if(player != null) {
			Account account = main.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			if(account.isModEnabled()) return;
		}
		this.lastDamager = player;
	}
	
	public Player getLastDamager() {
		return this.lastDamager;
	}
	
	public void setNewBestKillStreak(int streak) {
		StatsData data = StatsDataHandler.getPlayerStats(player);
		if(streak > data.getStat("ksrecord")) {
			data.setStat("ksrecord", streak);
		}
	}
	
}
