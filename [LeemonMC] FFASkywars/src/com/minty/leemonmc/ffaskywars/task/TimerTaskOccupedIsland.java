package com.minty.leemonmc.ffaskywars.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAIsland;
import com.minty.leemonmc.ffaskywars.core.InvinsibilityState;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TimerTaskOccupedIsland extends BukkitRunnable {
	FFASkywars main;
	FFAIsland island;
	Player player;
	int tick;
	float second = 0;
	
	public TimerTaskOccupedIsland(FFASkywars main, Player player, FFAIsland island) {
		this.main = main;
		this.island = island;
		this.player = player;
		tick = main.getConfig().getInt("tickInvinsibility");
	}
	
	@Override
	public void run() {
		
		if(tick <= 0) {
			FFAData data = FFADataHandler.getPlayerData(player);
			
			data.setInvisibilityState(InvinsibilityState.NORMAL);
			island.setOccupated(false);
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§8» §eVous n'êtes plus invincible ! §8«"));
			cancel();
			return;
		}
		
		island.setOccupated(true);
		tick--;
		second = (float) tick / 20f;
		String secondStr = Float.toString(second);
		if(tick <= 20) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eVotre invincibilité prend fin dans §6 "+ secondStr + "seconde §e!"));
		}
		else {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eVotre invincibilité prend fin dans §6 "+ secondStr + "secondes §e!"));
		}
		
	}
	
}

