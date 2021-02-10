package com.minty.leemonmc.ffaskywars.handlers;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.stats.StatsData;
import com.minty.leemonmc.core.stats.StatsDataHandler;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAState;
import com.minty.leemonmc.ffaskywars.events.FFAStateChangeEvent;

public class SbHandler implements Listener {

	private static FFASkywars main = FFASkywars.getInstance();
	private static Objective objective;
	private static String displayName;
	
	public SbHandler()
	{

	}
	
	public static void updateScoreboard(Player p) {
		displayName = "§6§lFFASkywars";
		
		FFAData data = FFADataHandler.getPlayerData(p);
		
		if(data.getState() == FFAState.INLOBBY)
		{
			updateWaiting(p);
		}
		
		if(data.getState() == FFAState.INGAME)
		{
			updatePlaying(p);
		}

	}
	
	@EventHandler
	public void onStateChange(FFAStateChangeEvent e)
	{
		objective.unregister();
		e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	private static void updatePlaying(Player p)
	{

	    if(p.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); //Per-player scoreboard, not necessary if all the same data, but we're personalizing the displayname and all
	    Scoreboard score = p.getScoreboard(); //Personalized scoreboard
	    objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName()); //Per-player objectives, even though it doesn't matter what it's called since we're using per-player scoreboards.

	    FFAData data = FFADataHandler.getPlayerData(p);
	    
	    DecimalFormat format = new DecimalFormat("00");
	    
	    String secondNormalChest = format.format(main.normalChestTask.getSecond() % 60);
	    String minuteNormalChest = format.format(main.normalChestTask.getSecond() / 60);
	    
	    String secondGoodChest = format.format(main.goodChestTask.getSecond() % 60);
	    String minuteGoodChest = format.format(main.goodChestTask.getSecond() / 60);
	    
	    double ratio = data.getRatio();
	    String ratioStr = "";
	    if(ratio == -1) {
	    	ratioStr = "∞";
	    }
	    else {
	    	ratioStr = Double.toString(ratio);
	    }
	    
	    objective.setDisplayName("\u00A7d\u00A7l" + displayName);
	    replaceScore(objective, 11, "§8");
	    replaceScore(objective, 10, "§7Killstreak : §9" + data.getKillStreak());
	    replaceScore(objective, 9, "§7Ratio : §9" + ratioStr);
	    replaceScore(objective, 8, "§9");
	    replaceScore(objective, 7, "§7Refill des îles : §a" + minuteNormalChest + ":" + secondNormalChest);
	    replaceScore(objective, 6, "§7Refill du centre : §c" + minuteGoodChest + ":" + secondGoodChest);
	    replaceScore(objective, 5, "§3");
	    replaceScore(objective, 4, "§7Joueurs: §e" + FFASkywars.getInstance().getPlayersNotMod() + "§7/§e" + CoreMain.getInstance().getServerManager().getServer().getMaxPlayers());
	    replaceScore(objective, 3, "§7");
	    replaceScore(objective, 2, "§4➠ §cAlliances interdites");
	    replaceScore(objective, 1, "§d");
	    replaceScore(objective, 0, CoreMain.getInstance().getScoreboardAnimator().getFooter());
	    
	    if(objective.getDisplaySlot() != DisplaySlot.SIDEBAR) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    p.setScoreboard(score);
	}
	
	private static void updateWaiting(Player p)
	{

	    if(p.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); //Per-player scoreboard, not necessary if all the same data, but we're personalizing the displayname and all
	    Scoreboard score = p.getScoreboard(); //Personalized scoreboard
	    objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName()); //Per-player objectives, even though it doesn't matter what it's called since we're using per-player scoreboards.

	    StatsData data = StatsDataHandler.getPlayerStats(p);
	    
	    FFAData ffaData = FFADataHandler.getPlayerData(p);
	    
	    objective.setDisplayName("\u00A7d\u00A7l" + displayName);
	    replaceScore(objective, 10, "§8");
	    replaceScore(objective, 9, "§7Kills : §a" + data.getStat("kills"));
	    replaceScore(objective, 8, "§7Morts : §c" + data.getStat("deaths"));
	    replaceScore(objective, 7, "§3");
	    replaceScore(objective, 6, "§7Ratio : §3" + ffaData.getRatio());
	    replaceScore(objective, 5, "§9");
	    replaceScore(objective, 4, "§7Joueurs: §e" + FFASkywars.getInstance().getPlayersNotMod() + "§7/§e" + CoreMain.getInstance().getServerManager().getServer().getMaxPlayers());
	    replaceScore(objective, 3, "§7");
	    replaceScore(objective, 2, "§4➠ §cAlliances interdites");
	    replaceScore(objective, 1, "§d");
	    replaceScore(objective, 0, CoreMain.getInstance().getScoreboardAnimator().getFooter());
	    
	    if(objective.getDisplaySlot() != DisplaySlot.SIDEBAR) objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	    p.setScoreboard(score);
	}
	
	public static String getEntryFromScore(Objective o, int score) {
	    if(o == null) return null;
	    if(!hasScoreTaken(o, score)) return null;
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
	    }
	    return null;
	}

	public static boolean hasScoreTaken(Objective o, int score) {
	    for (String s : o.getScoreboard().getEntries()) {
	        if(o.getScore(s).getScore() == score) return true;
	    }
	    return false;
	}

	public static void replaceScore(Objective o, int score, String name) {
	    if(hasScoreTaken(o, score)) {
	        if(getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
	        if(!(getEntryFromScore(o, score).equalsIgnoreCase(name))) o.getScoreboard().resetScores(getEntryFromScore(o, score));
	    }
	    o.getScore(name).setScore(score);
	}
	
}
