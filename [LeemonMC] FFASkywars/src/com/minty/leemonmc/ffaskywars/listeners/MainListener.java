package com.minty.leemonmc.ffaskywars.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.minty.leemonmc.basics.core.Rank;
import com.minty.leemonmc.basics.core.ServerType;
import com.minty.leemonmc.basics.core.cache.Account;
import com.minty.leemonmc.core.CoreMain;
import com.minty.leemonmc.core.events.CoreInitEvent;
import com.minty.leemonmc.core.events.dataLoadedEvent;
import com.minty.leemonmc.ffaskywars.FFASkywars;
import com.minty.leemonmc.ffaskywars.core.FFAData;
import com.minty.leemonmc.ffaskywars.core.FFAIsland;
import com.minty.leemonmc.ffaskywars.core.FFAState;
import com.minty.leemonmc.ffaskywars.core.InvinsibilityState;
import com.minty.leemonmc.ffaskywars.core.MyBlock;
import com.minty.leemonmc.ffaskywars.core.PlayerDieCause;
import com.minty.leemonmc.ffaskywars.gui.WantedGui;
import com.minty.leemonmc.ffaskywars.handlers.FFADataHandler;
import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;
import com.minty.leemonmc.ffaskywars.task.TimerTaskBreakLog;
import com.minty.leemonmc.ffaskywars.task.TimerTaskCreateBlock;

public class MainListener implements Listener {

	FFASkywars main;
	public CoreMain leemonmc;
	
	public MainListener(FFASkywars main) {
		this.main = main;
		leemonmc = (CoreMain) Bukkit.getServer().getPluginManager().getPlugin("LeemonCore");
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType().equals(Material.CHEST) || e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
				FFAData data = FFADataHandler.getPlayerData(e.getPlayer());
				if(data.getState() == FFAState.SPECTATOR) {
					e.setCancelled(true);
					return;
				}
				main.getGameManager().lootChest(e.getClickedBlock().getLocation(),e.getPlayer());
				e.setCancelled(true);
			    for (int j = 0; j <= 3; j++) {
			    	e.getClickedBlock().getLocation().getWorld().playEffect(e.getClickedBlock().getLocation(),Effect.MOBSPAWNER_FLAMES,j);
			    }
				
			}
		}
		if(e.hasItem()) {
			if(e.getItem().hasItemMeta()) {
				if(e.getItem().getItemMeta().hasDisplayName()) {
					
					// Jouer
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lJouer§r§7 ▪ Clic-droit") && e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lJouer§r§7 ▪ Clic-droit") && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						if(main.map == null) {
							e.getPlayer().sendMessage("§6§lFFASkywars §f» §cLa carte n'est pas encore génerée!... Patience !");
							return;
						}
						if(!main.canGoInGame) {
							e.getPlayer().sendMessage("§6§lFFASkywars §f» §cLa carte n'est pas encore génerée!... Patience !");
							return;
						}
						FFAIsland island = null;
						for(int i = 0; i < 2; i++) {
							Random r = new Random();
							int index = r.nextInt(main.map.getIslands().size());
							island = main.map.getIslands().get(index);
							if(!island.isOccupated()) {
								if(!island.isMidle()) {
									main.getGameManager().teleportIsland(e.getPlayer(), island);
									break;
								}
							}
						}
						if(island == null) {
							e.getPlayer().sendMessage("§cAucune île n'est disponible. Recommencer dans quelques seconde.");
						}	
					}
					
					
					// Wanted
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lWanted§r§7 ▪ Clic-droit")) {
						main.getAPI().getGuiManager().open(e.getPlayer(), WantedGui.class);
					}
					
					// Lobby
					if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CoreMain.getInstance().getLeemonUtils().getLobbyItem().getItemMeta().getDisplayName())) {
						CoreMain.getInstance().sendPlayerToHub(e.getPlayer());
					}
				}
			}
		}
	}
	

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		FFAData data = FFADataHandler.getPlayerData(player);
		
		if(data.getState() != FFAState.INGAME && e.getCause() == TeleportCause.ENDER_PEARL)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDataLoaded(dataLoadedEvent e) {
		FFAData playerData = new FFAData(e.getPlayer());
		main.getGameManager().setLobby(e.getPlayer(), 1);
		main.playerDataList.add(playerData);
		
		Account account = CoreMain.getInstance().getAccountManager().getAccount(e.getUuid());
		if(account.getNickedRank().getPower() >= Rank.VIP_PLUS.getPower())
		{
			if(account.isModEnabled() == false)
			{	
				Bukkit.broadcastMessage("§6§lFFASkywars §f» " + main.getAPI().getPlayerDisplayNameChat(e.getPlayer()) + " §6a rejoint le FFA Skywars !");
			} else
			{
				for(Player players : Bukkit.getOnlinePlayers())
				{
					players.hidePlayer(e.getPlayer());
				}
			}
		}
		
	}
	
	
	public void onCoreInit(CoreInitEvent e) {
		CoreMain leemonmcMain = e.getMain();
		leemonmcMain.init(ServerType.FFA);
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			FFAData data = FFADataHandler.getPlayerData(player);
			
			if(data.getState().equals(FFAState.INLOBBY) || data.getState().equals(FFAState.SPECTATOR)) {
				e.setCancelled(true);
				return;
			}
			if(data.getInvisibilityState().equals(InvinsibilityState.INVICIBLE)) {
				e.setCancelled(true);
				return;
			}
			
			if(e.getCause() == DamageCause.ENTITY_EXPLOSION) {
				e.setDamage(4);
				return;
			}
			if(e.getCause() == DamageCause.ENTITY_ATTACK) {
				Player damager;
				EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) e;
				damager = (Player) entityEvent.getDamager();
				FFAData entityData = FFADataHandler.getPlayerData(player);
				entityData.setLastDamager(damager);
			}
			
			//----------------------------------------------------------------------------------------
			// Die cause
			if(player.getHealth() - e.getFinalDamage() <= 0) {
				Entity damager;
				Player killer = null;
				data.setState(FFAState.INLOBBY);
				
				// Die cause player kill
				if(e.getCause() == DamageCause.ENTITY_ATTACK) {
					EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) e;
					damager = (Entity) entityEvent.getDamager();
					killer = (Player) damager;
					if(killer != null) {
						main.getGameManager().playerDie(player,PlayerDieCause.PLAYER_KILL);
					}
					else {
						main.getGameManager().playerDie(player,PlayerDieCause.OTHER);
					}
				}
				
				// Die cause explosion
				else if(e.getCause() == DamageCause.ENTITY_EXPLOSION) {
					main.getGameManager().playerDie(player, PlayerDieCause.EXPLOSION);
				}
				
				// Die cause thor
				else if(e.getCause() == DamageCause.LIGHTNING) {
					main.getGameManager().playerDie(player, PlayerDieCause.ELECTRICITY);
				}
				
				// Die cause fire
				else if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
					main.getGameManager().playerDie(player, PlayerDieCause.FIRE);
				}
				
				//Die cause other
				else {
					main.getGameManager().playerDie(player, PlayerDieCause.OTHER);
				}
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			FFAData data = FFADataHandler.getPlayerData(player);
			
			if(data == null) return;
					
			if(data.getInvisibilityState().equals(InvinsibilityState.INVICIBLE) || data.getState() == FFAState.SPECTATOR) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		String UUID = e.getPlayer().getUniqueId().toString();
		Account account = main.getAPI().getAccountManager().getAccount(UUID);
		FFAData data = FFADataHandler.getPlayerData(e.getPlayer());
		
		if(account.isModEnabled()) return;
		
		if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return;
		if(e.getPlayer().getLocation().getY() <= main.getConfig().getInt("dieZone") && data.getState() == FFAState.INGAME) {
			data.setState(FFAState.INLOBBY);
			EntityDamageEvent event = e.getPlayer().getLastDamageCause();
			if(event != null) {
				if(event.getCause().equals(DamageCause.PROJECTILE)) {
					main.getGameManager().playerDie(e.getPlayer(), PlayerDieCause.PROJECTILE);
					return;
				}
			}
			main.getGameManager().playerDie(e.getPlayer(),PlayerDieCause.VOID);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		FFAData data = FFADataHandler.getPlayerData(player);
		
		if(data.getState().equals(FFAState.INLOBBY) || data.getState().equals(FFAState.SPECTATOR)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		if(e.getEntity() instanceof Player) {
			Player player = e.getEntity();
			player.spigot().respawn();
			if(player.getKiller() != null) {
				main.getGameManager().playerDie(player,PlayerDieCause.PLAYER_KILL);
			}
			else {
				main.getGameManager().playerDie(player,PlayerDieCause.OTHER);
			}
			
		}
	}
	
	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {
		FFAData data = FFADataHandler.getPlayerData(e.getPlayer());
		if(data.getState() == FFAState.SPECTATOR) {
			e.setCancelled(true);
			return;
		}
		
		// Place tnt
		if(e.getBlock().getType().equals(Material.TNT)) {
			e.getBlock().setType(Material.AIR);
			Location loc = e.getBlock().getLocation();
			loc.setX(loc.getX() + 0.5);
			loc.setZ(loc.getZ() + 0.5);
			e.getBlock().getLocation().getWorld().spawn(loc, TNTPrimed.class);
			return;
		}
		
		if(data.getState().equals(FFAState.INGAME) || !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if(e.getBlock().getLocation().getY() >= main.getConfig().getInt("hightBuildLimit")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§6§lFFASkywars §f» §cTu ne peux pas construire plus haut !");
				return;
			}
			if(!main.getGameManager().isBlockInList(e.getBlock())) {
				MyBlock myBlock = new MyBlock(e.getBlock());
				main.getGameManager().addBlockInList(myBlock);
			}
			else {
				MyBlock block = main.getGameManager().getBlockWithLocation(e.getBlock().getLocation());
				main.getGameManager().deleteBlockInList(block);
				MyBlock myBlock = new MyBlock(e.getBlock());
				main.getGameManager().addBlockInList(myBlock);
			}
		}
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		FFAData data = FFADataHandler.getPlayerData(e.getPlayer());
		if(data.getState() == FFAState.SPECTATOR) {
			e.setCancelled(true);
			return;
		}
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
		if(!main.getGameManager().isBlockInListWithLocation(e.getBlock().getLocation())) {
			// Le block cassé est du bois
			if(e.getBlock().getType().equals(Material.LOG)) {
				TimerTaskBreakLog task = new TimerTaskBreakLog(main,e.getBlock());
				task.runTaskTimer(main, 0, 20);
				
				// Drop plus de bois
				Collection<ItemStack> drops = e.getBlock().getDrops();
				for(int i = 0;i < 3;i++) {
					for(ItemStack drop : drops) {
						e.getPlayer().getInventory().addItem(drop);
					}
				}
				
				//Get coconut
				Random r = new Random();
				
				//40% de chance d'avoir une coconut
				if(r.nextInt(99) < 40) {
					e.getPlayer().getInventory().addItem(SpecialItemsHandler.getCoconut());
					
				}
				
			}
			e.setCancelled(true);
		}
		else {
			// Le block cassé a été posé par un joueur
			MyBlock myBlock = main.getGameManager().getBlockWithLocation(e.getBlock().getLocation());
			main.getGameManager().deleteBlockInList(myBlock);
		}
	}
	
	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event)
	{
		FFAData data = FFADataHandler.getPlayerData(event.getPlayer());
		if(data.getState() == FFAState.SPECTATOR) {
			event.setCancelled(true);
			return;
		}
		
		int x = event.getBlockClicked().getX();
		int y = event.getBlockClicked().getY();
		int z = event.getBlockClicked().getZ();
		BlockFace blockFace = event.getBlockFace();
		Location loc = new Location(event.getBlockClicked().getWorld(),x,y,z);
		 
		if(event.getBucket() == Material.LAVA_BUCKET) 
		{
			// Get the location of the lava
			if(blockFace.equals(BlockFace.UP)) y++;
			if(blockFace.equals(BlockFace.DOWN)) y--;
			if(blockFace.equals(BlockFace.SOUTH)) z++;
			if(blockFace.equals(BlockFace.NORTH)) z--;
			if(blockFace.equals(BlockFace.EAST)) x++;
			if(blockFace.equals(BlockFace.WEST)) x--;
			
			loc = new Location(event.getBlockClicked().getWorld(),x,y,z);
			
			
		}
		 
		if(event.getBucket() == Material.WATER_BUCKET) 
		{
			// Get the location of the water
			if(blockFace.equals(BlockFace.UP)) y++;
			if(blockFace.equals(BlockFace.DOWN)) y--;
			if(blockFace.equals(BlockFace.SOUTH)) z++;
			if(blockFace.equals(BlockFace.NORTH)) z--;
			if(blockFace.equals(BlockFace.EAST)) x++;
			if(blockFace.equals(BlockFace.WEST)) x--;
			
			loc = new Location(event.getBlockClicked().getWorld(),x,y,z);
		}
		
		if(loc.getY() >= main.getConfig().getInt("hightBuildLimit")) {
			loc.getBlock().setType(Material.AIR);
			event.getPlayer().sendMessage("§cTu ne peux pas construire plus haut !");
			return;
		}
		
		TimerTaskCreateBlock task = new TimerTaskCreateBlock(main,loc);
		task.runTaskTimer(main, 10, 5);
	}
	  
	
	@EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        if (event.getSpawnReason() == SpawnReason.EGG)
        {
            event.setCancelled(true);
        }
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
		
		Player player = e.getPlayer();
		
		player.sendMessage("§7§m---------------------------------------");
		player.sendMessage("");
		player.sendMessage("              §6§lFFASkywars");
		player.sendMessage("             §7By LeemonMC");
		player.sendMessage("");
		player.sendMessage("§f➠ §7But du jeu :");
		player.sendMessage("§7Expulez les adversaires de leurs îles, et attaquez");
		player.sendMessage("§7le centre pour du meilleur équipement !");
		player.sendMessage("");
		player.sendMessage("§7§m---------------------------------------");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
		main.getGameManager().resetLastAllDamagerForAPlayer(e.getPlayer());
		new BukkitRunnable() {
			@Override
			public void run() {
				main.getGameManager().removePlayerData(e.getPlayer());
			}
		}.runTaskLater(main, 20);
	}
	
	
	@EventHandler
	public void onMoveItem(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			FFAData data = FFADataHandler.getPlayerData(player);
			if(data.getState() == FFAState.SPECTATOR) {
				e.setCancelled(true);
				return;
			}
			if(data.getState().equals(FFAState.INLOBBY) && player.getGameMode().equals(GameMode.ADVENTURE)) {
				e.setCancelled(true);
			}
			if(e.getCurrentItem() != null) {
				if(e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().hasDisplayName()) {
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lRetour au lobby")) {
							main.getGameManager().setLobby(player, 1);
						}
					}
				}
			}
		}

	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e)
	{
		Entity target = e.getEntity();
		
		if(target instanceof Player)
		{
			if(e.getDamager() instanceof Egg)
			{
				Egg egg = (Egg) e.getDamager();
				if(egg.getShooter() instanceof Player) {
					Player shooter = (Player) egg.getShooter();
					Player targetPlayer = (Player) target;
					FFAData data = FFADataHandler.getPlayerData(targetPlayer);
					data.setLastDamager(shooter);
				}
			}
			
			if(e.getDamager() instanceof Snowball)
			{
				Snowball snowball = (Snowball) e.getDamager();
				if(snowball.getShooter() instanceof Player) {
					Player shooter = (Player) snowball.getShooter();
					Player targetPlayer = (Player) target;
					FFAData data = FFADataHandler.getPlayerData(targetPlayer);
					data.setLastDamager(shooter);
				}
			}
		}
	}
	
	@EventHandler
	public void onExplosion(EntityExplodeEvent event) {
		ArrayList<Block> blockListToNotExplode = new ArrayList<Block>();
		for(Block block : event.blockList()) {
			if(!main.getGameManager().isBlockInListWithLocation(block.getLocation())){
				blockListToNotExplode.add(block);
			}
			else {
				main.getGameManager().deleteBlockInList(main.getGameManager().getBlockWithLocation(block.getLocation()));
			}
		}
		event.blockList().removeAll(blockListToNotExplode);
	}
	
	@EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
		// Le liquide ne peux pas se propager
        if(e.getBlock().isLiquid()) {
            e.setCancelled(true);
            e.getToBlock().setType(Material.AIR);
        }
    }
	
    @EventHandler
    public void onObsidianForm(BlockFormEvent event) {
    	// Empeche l'obsi de se former avec de la lave et de l'eau
        if(event.getBlock().getType() == Material.OBSIDIAN) {
        	 event.setCancelled(true);
        }
    }

}
