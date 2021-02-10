package com.minty.leemonmc.ffaskywars;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.basics.core.GameState;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.ffaskywars.cmd.CommandFFAGive;
import com.minty.leemonmc.ffaskywars.cmd.CommandWanted;
import com.minty.leemonmc.ffaskywars.core.ChestType;
import com.minty.leemonmc.ffaskywars.core.FFAChest;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAIsland;
import com.minty.leemonmc.ffaskywars.core.FFAMap;
import com.minty.leemonmc.ffaskywars.gui.WantedGui;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;
import com.minty.leemonmc.ffaskywars.handlers.GameManager;
import com.minty.leemonmc.ffaskywars.handlers.SbHandler;
import com.minty.leemonmc.ffaskywars.handlers.SuperAnvilHandler;
import com.minty.leemonmc.ffaskywars.listeners.FireBallListener;
import com.minty.leemonmc.ffaskywars.listeners.MainListener;
import com.minty.leemonmc.ffaskywars.listeners.SpecialItemListener;
import com.minty.leemonmc.ffaskywars.listeners.SpectatorListener;
import com.minty.leemonmc.ffaskywars.task.TimerTaskCreateMap;
import com.minty.leemonmc.ffaskywars.task.TimerTaskGoodChest;
import com.minty.leemonmc.ffaskywars.task.TimerTaskKillStreak;
import com.minty.leemonmc.ffaskywars.task.TimerTaskNormalChest;
import com.minty.leemonmc.ffaskywars.task.TimerTaskSuperAnvil;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FFASkywars extends JavaPlugin {
	
	public CoreMain leemonmc;
	public FFAMap map = null;
	public ArrayList<FFAMap> mapList = new ArrayList<FFAMap>();
	public Location lobbyLocation;
	private GameManager gameManager = new GameManager(this);
	private static FFASkywars instance;
	public ArrayList<FFAData> playerDataList;
	public TimerTaskNormalChest normalChestTask;
	public TimerTaskGoodChest goodChestTask;
	private SuperAnvilHandler superAnvilHandler;
	FFASkywars plugin = this;
	public int mapChangeid = 0;
	public boolean canGoInGame = true;
	public int secondBeforeChangeMap;
	public ArrayList<Player> wantedList = new ArrayList<Player>();
	
	
	@Override
	public void onEnable() {
		instance = this;
		leemonmc = (CoreMain) Bukkit.getPluginManager().getPlugin("LeemonCore");
		
		saveDefaultConfig();
		
		registerReferences();
		registerListeners();
		registerRunnables();
		registerCommands();
		registerMenus();
		
		Bukkit.setDefaultGameMode(GameMode.ADVENTURE);
		
		System.out.println("[FFASkywars] Plugin actif !");
        
        List<String> stats = new ArrayList<>();
        stats.add("kills"); stats.add("deaths"); stats.add("ksrecord");
		CoreMain.getInstance().getStatsHandler().init("ffaskywars", stats); //Init with no caps minigame name, ALWAYS the same

	}
	
	private void registerReferences()
	{
		//Set the lobby location
		lobbyLocation = new Location(Bukkit.getWorld("world"),this.getConfig().getDouble("lobby.x"),this.getConfig().getDouble("lobby.y"),this.getConfig().getDouble("lobby.z"));
		lobbyLocation.setPitch((float) this.getConfig().getDouble("lobby.pitch"));
		lobbyLocation.setYaw((float) this.getConfig().getDouble("lobby.yaw"));
		
		gameManager = new GameManager(this);
		playerDataList = new ArrayList<FFAData>();
		superAnvilHandler = new SuperAnvilHandler();
	}
	
	private void registerListeners()
	{
		getServer().getPluginManager().registerEvents(new MainListener(this), this);
		getServer().getPluginManager().registerEvents(getSuperAnvilHandler(), this);
		getServer().getPluginManager().registerEvents(new SpectatorListener(),this);
		getServer().getPluginManager().registerEvents(new FireBallListener(),this);
		getServer().getPluginManager().registerEvents(new SpecialItemListener(this),this);
	}
	
	private void registerRunnables()
	{
        TimerTaskCreateMap task = new TimerTaskCreateMap(this);
        task.runTaskTimer(this, 10*20, 5);
        
        
        TimerTaskNormalChest normalChest = new TimerTaskNormalChest(this);
        normalChest.runTaskTimer(this, 0, 20);
        normalChestTask = normalChest;
        TimerTaskGoodChest goodChest = new TimerTaskGoodChest(this);
        goodChest.runTaskTimer(this, 0, 20);
        goodChestTask = goodChest;
        
        TimerTaskKillStreak killStreakTask = new TimerTaskKillStreak();
        killStreakTask.runTaskTimer(this, 0, 60);
		
        new TimerTaskSuperAnvil().runTaskTimer(this, 0, 30);
        
        new BukkitRunnable() {
        	@Override
        	public void run()
        	{
        		updateServerPlayer();
        		
        		for(Player player : Bukkit.getOnlinePlayers()) {
            		SbHandler.updateScoreboard(player);
            		
        		}
        		FFASkywars.getInstance().getGameManager().hideSpectatorPlayers();
        		FFASkywars.getInstance().getGameManager().showPlayerNonSpecator();
        	}
        }.runTaskTimer(this, 20, 20);
        
        new BukkitRunnable() {
        	@Override
        	public void run() {
        		if(FFASkywars.getInstance().getGameManager().getPlayingPlayer() == null) return;
        		for(Player player : FFASkywars.getInstance().getGameManager().getPlayingPlayer())
        		{
        			player.getInventory().remove(Material.BUCKET);
        		}
        	}
        }.runTaskTimer(this, 0, 5);
        
        new BukkitRunnable() {
        	boolean mapOrNot = true;
			@Override
			public void run() {
				if(mapOrNot) {
					mapOrNot = false;
					sendMapToLobbyPlayers(true);
				}
				else {
					mapOrNot = true;
					sendMapToLobbyPlayers(false);
				}
				resetOfflinePlayerInWanted();
			}
        	
        }.runTaskTimer(this, 0, 40);
        
        new BukkitRunnable() {
			
			@Override
			public void run() {
				FFASkywars.getInstance().spawnParticlesTarget();
			}
		}.runTaskTimer(this, 0, 10);
	}
	
	private void registerCommands()
	{
		getCommand("ffagive").setExecutor(new CommandFFAGive());
		getCommand("wanted").setExecutor(new CommandWanted());
	}
	
	private void registerMenus()
	{
		getAPI().getGuiManager().addMenu(new WantedGui(getAPI().getGuiUtils()));
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("[FFASkywars] Plugin inactif !");
		getAPI().getServerManager().removeServer();
	}
	
	private void updateServerPlayer()
	{
		this.getAPI().getServerManager().getServer().setPlayingPlayers(getPlayersNotMod());
		this.getAPI().getServerManager().getServer().setGameState(GameState.WAITING);
		this.getAPI().getServerManager().getServer().setMaxPlayers(100);
	}
	
	private void sendMapToLobbyPlayers(boolean mapOrNot) {
		if(mapOrNot) {
			for(Player player : this.getGameManager().getLobbyPlayers()) {
				if(this.map == null) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cLa carte n'est pas encore générée!... Patience !"));
				}
				else {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Carte actuelle : §6" + this.map.getName()));
				}
			}
		}
		else {
			DecimalFormat format = new DecimalFormat("00");
		    String secondMapChange = format.format(this.getSecondBeforeChangingMap() % 60);
		    String minuteMapChange = format.format(this.getSecondBeforeChangingMap() / 60);
			
			
			for(Player player : this.getGameManager().getLobbyPlayers()) {
				if(this.map == null) {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cLa carte n'est pas encore générée!... Patience !"));
				}
				else {
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Changement de carte dans §6" + minuteMapChange + ":" + secondMapChange + " §7!"));
				}
			}
		}
	}
	
	public int getPlayersNotMod() {
		int playingNonMod = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			Account account = this.getAPI().getAccountManager().getAccount(player.getUniqueId().toString());
			if(!account.isModEnabled()) playingNonMod++;
		}
		return playingNonMod;
	}
	
	public ItemStack itemStackGenerator(Material mat,int amount,String customName,Enchantment ench,int level) {
		ItemStack it = new ItemStack(mat);
		ItemMeta itMeta = it.getItemMeta();
		it.setAmount(amount);
		if(ench != null) it.addEnchantment(ench, level);
		if(!customName.equals("")) itMeta.setDisplayName(customName);
		it.setItemMeta(itMeta);
		
		return it;
	}
	
	public void spawnParticulesKillStreak(Player player)
	{
		
		new BukkitRunnable() {
			double t;
			Location loc = player.getLocation();
			FFAData data = FFADataHandler.getPlayerData(player);
			int killStreak = data.getKillStreak();

			@Override
			public void run() {
				t = t + 0.1*Math.PI;
				int r = killStreak * 10;
				if(r > 255) r = 255;
				if(r < 0) r = 0;
				for(double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/8) {
					double x = t*Math.cos(theta);
					double y = Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(theta);
					loc.add(x,y,z);
					loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, r,0,0);
					//r,127,33
					loc.subtract(x,y,z);
				}
				if(t > killStreak) {
					this.cancel();
				}
				
			}
		}.runTaskTimer(this, 0, 1);
		
	}
	
	public void createHelix(Location location) {
        Location loc = location;
        int radius = 2;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            for(Player online : Bukkit.getOnlinePlayers()) {
                this.map.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,(float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            } 
        }
	}
	
	public GameManager getGameManager() {
		return gameManager;
	}
	
	public static FFASkywars getInstance() {
		return instance;
	}
	
	public CoreMain getAPI() {
		return leemonmc;
	}
	
	public SuperAnvilHandler getSuperAnvilHandler() {
		return superAnvilHandler;
	}
	
	public void createAllMaps() {
		int id = 0;
		for (String key : this.getConfig().getConfigurationSection("Maps").getKeys(false)) {
		    // get the section for the individual key
		    ConfigurationSection map = getConfig().getConfigurationSection("Maps." + key);
		    ConfigurationSection specSpawn = getConfig().getConfigurationSection("Maps." + key + ".specSpawn");
		    System.out.println("Generating " + key + "...");
		    World world = Bukkit.getWorld(map.getString("worldName"));
		    ArrayList<FFAIsland> islands = getAllIslands(key,world);
		    String arenaName = map.getString("arenaName");
		    String worldName = map.getString("worldName");
		    
		    int x = specSpawn.getInt("x");
			int y = specSpawn.getInt("y");
			int z = specSpawn.getInt("z");
			Location loc = new Location(world,x,y,z);
		    
		    FFAMap configMap = new FFAMap(this,id,islands,arenaName,worldName,loc);
		    this.mapList.add(configMap);
		    id++;
		}
	}
	
	private ArrayList<FFAIsland> getAllIslands(String key,World world) {
		ArrayList<FFAIsland> islandsList = new ArrayList<FFAIsland>();
		int id = 0;
		
		for(String keyForInslands : this.getConfig().getConfigurationSection("Maps." + key + ".islands").getKeys(false)) {
			ConfigurationSection island = getConfig().getConfigurationSection("Maps." + key + ".islands." + keyForInslands);
			ArrayList<FFAChest> chestList = this.getAllChestOfAnIsland(island.getCurrentPath(),world);
			boolean isMidle = island.getBoolean("isMidle");
			ConfigurationSection spawnPointIsland = getConfig().getConfigurationSection("Maps." + key + ".islands." + keyForInslands + ".spawnpoint");

			int x = spawnPointIsland.getInt("x");
			int y = spawnPointIsland.getInt("y");
			int z = spawnPointIsland.getInt("z");
			Location loc = new Location(world,x,y,z);
			
			FFAIsland is = new FFAIsland(this,id,loc,chestList,isMidle);
			islandsList.add(is);
			id++;
		}
		
		return islandsList;
	}
	
	private ArrayList<FFAChest> getAllChestOfAnIsland(String key,World world) {
		ArrayList<FFAChest> chestList = new ArrayList<FFAChest>();
		
		for(String keyForChest : this.getConfig().getConfigurationSection(key + ".Chest").getKeys(false)) {
			ConfigurationSection chest = getConfig().getConfigurationSection(key + ".Chest." + keyForChest);
			int x = chest.getInt("x");
			int y = chest.getInt("y");
			int z = chest.getInt("z");
			Location loc = new Location(world,x,y,z);
			ChestType type = ChestType.valueOf(chest.getString("type"));
			BlockFace face = null;
			if(type == ChestType.GOOD) {
				face = BlockFace.valueOf(chest.getString("face"));
			}
			FFAChest ffaChest = new FFAChest(loc,this,type,face);
			chestList.add(ffaChest);
		}
		
		return chestList;
	}
	
	public int getSecondBeforeChangingMap() {
		return secondBeforeChangeMap;
	}
	
	public ArrayList<Player> getWantedList() {
		return this.wantedList;
	}
	
	public void spawnParticlesTarget() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			FFAData data = FFADataHandler.getPlayerData(player);
			if(data != null) {
				if(data.targetedPlayerList != null && data.targetedPlayerList.size() > 0) {
					for(Player p : data.targetedPlayerList) {
						for(double y = 0; y <= 50; y+=0.25) {
							Location loc = p.getLocation();
							loc.setY(loc.getY() + y);
							player.spawnParticle(Particle.REDSTONE, loc, 0, 255,0,0);
						}
					}
				}
			}
		}
	}
	
	public void resetOfflinePlayerInWanted() {
		for(Player player : this.wantedList) {
			if(!player.isOnline()) {
				this.wantedList.remove(player);
			}
		}
	}
}
