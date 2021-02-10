package com.minty.leemonmc.ffaskywars.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;

public class MiddleChestTable implements ChestLootTable {

	@Override
	public List<ItemStack> content() {
		List<ItemStack> result = new ArrayList<>();
		Random r = new Random();
		
		result.add(SpecialItemsHandler.getThor());
		
		result.add(this.getEnderPearl());
		result.add(this.getDiamondSwordS1());
		result.add(this.getArrow());
		result.add(SpecialItemsHandler.getWaterBucket());
		result.add(SpecialItemsHandler.getLavaBucket());
		result.add(SpecialItemsHandler.getTnt(r.nextInt(8 - 1) + 1));
		result.add(SpecialItemsHandler.getSuperAnvil());
		result.add(SpecialItemsHandler.getLuckyBlock());
		result.add(this.getBowP2());
		result.add(this.getIronSwordT3());
		result.add(this.getDiamondChestPlateP3());
		result.add(this.getWood());
		result.add(this.getWood());
		result.add(this.getStone());
		result.add(this.getStone());
		result.add(this.getIronChestPlateP3());
		result.add(this.getEgg());
		result.add(this.getSnowBall());
		result.add(this.getIronLeggingsP2());
		result.add(this.getIronHelmetP2());
		result.add(this.getIronBootsP2());
		result.add(this.getDiamondLeggingsP3());
		result.add(this.getDiamondBootsP2());
		result.add(this.getDiamondHelmetP2());
		result.add(this.getWoodenAxeE2());
		result.add(this.getStoneAxeE2());
		result.add(this.getIronAxeE2());
		result.add(this.getDiamondAxeE2());
		result.add(this.getWoodenPickaxeE2());
		result.add(this.getStonePickaxeE2());
		result.add(this.getIronPickaxeE2());
		result.add(this.getDiamondPickaxeE2());
		return result;
	}

	@Override
	public int getMaxItems() {
		return 4;
	}

	@Override
	public int getMinItems() {
		return 1;
	}
	
	
	private ItemStack getEnderPearl()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.ENDER_PEARL, r.nextInt(3 - 1) + 1);
		return it;
	}
	
	private ItemStack getDiamondSwordS1()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getArrow()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.ENDER_PEARL, r.nextInt(32 - 16) + 16);
		return it;
	}
	
	private ItemStack getBowP2()
	{
		ItemStack it = new ItemStack(Material.BOW, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronSwordT3()
	{
		ItemStack it = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondChestPlateP3()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getWood()
	{
		ItemStack it = new ItemStack(Material.WOOD, 64);
		return it;
	}
	
	private ItemStack getStone()
	{
		ItemStack it = new ItemStack(Material.STONE, 64);
		return it;
	}
	
	private ItemStack getIronChestPlateP3()
	{
		ItemStack it = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getEgg()
	{
		ItemStack it = new ItemStack(Material.EGG, 16);
		return it;
	}
	
	private ItemStack getSnowBall()
	{
		ItemStack it = new ItemStack(Material.SNOW_BALL, 16);
		return it;
	}
	
	private ItemStack getIronLeggingsP2()
	{
		ItemStack it = new ItemStack(Material.IRON_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronHelmetP2()
	{
		ItemStack it = new ItemStack(Material.IRON_HELMET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronBootsP2()
	{
		ItemStack it = new ItemStack(Material.IRON_BOOTS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondLeggingsP3()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondBootsP2()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondHelmetP2()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getWoodenAxeE2()
	{
		ItemStack it = new ItemStack(Material.WOOD_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getStoneAxeE2()
	{
		ItemStack it = new ItemStack(Material.STONE_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronAxeE2()
	{
		ItemStack it = new ItemStack(Material.IRON_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondAxeE2()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getWoodenPickaxeE2()
	{
		ItemStack it = new ItemStack(Material.WOOD_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getStonePickaxeE2()
	{
		ItemStack it = new ItemStack(Material.STONE_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronPickaxeE2()
	{
		ItemStack it = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondPickaxeE2()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}

}
