package com.minty.leemonmc.ffaskywars.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minty.leemonmc.ffaskywars.handlers.SpecialItemsHandler;

public class IslandChestTable implements ChestLootTable {

	@Override
	public List<ItemStack> content() {
		List<ItemStack> result = new ArrayList<>();
		Random r = new Random();
		
		result.add(this.getGoldenApple());
		result.add(this.getGoldenApple());
		
		result.add(this.getWood());
		result.add(this.getWood());
		result.add(this.getStone());
		result.add(this.getStone());
		result.add(this.getHay());
		result.add(this.getHay());
		result.add(this.getGlass());
		result.add(this.getGlass());
		
		result.add(SpecialItemsHandler.getSuperAnvil());
		result.add(SpecialItemsHandler.getSuperAnvil());
		result.add(SpecialItemsHandler.getThor());
		result.add(SpecialItemsHandler.getBridgeEgg());
		result.add(SpecialItemsHandler.getBridgeEgg());
		result.add(SpecialItemsHandler.getBridgeEgg());
		result.add(SpecialItemsHandler.getBridgeEgg());
		result.add(SpecialItemsHandler.getTnt(r.nextInt(4 - 1) + 1));
		result.add(SpecialItemsHandler.getLuckyBlock());
		
		result.add(this.getEnderPearl());
		result.add(this.getStoneSwordT2());
		result.add(this.getDiamondSword());
		result.add(this.getDiamondSwordS1());
		result.add(this.getIronSwordT2());
		result.add(this.getFishingRod());
		result.add(this.getWoodenSword());
		result.add(this.getWoodenSwordT1());
		result.add(this.getBow());
		result.add(this.get16Egg());
		result.add(this.get8Egg());
		result.add(this.get16SnowBall());
		result.add(this.get8SnowBall());
		result.add(this.getDiamondHelmet());
		result.add(this.getDiamondBoots());
		result.add(this.getLeatherChestPlate());
		result.add(this.getLeatherChestPlateP1());
		result.add(this.getLeatherLeggingsP1());
		result.add(this.getLeatherLeggingsP2());
		result.add(this.getChainmailLeggingsP2());
		result.add(this.getChainmailHelmetP2());
		result.add(this.getChainmailChestPlateP2());
		result.add(this.getChainmailBootsP2());
		result.add(this.getIronChestPlateP1());
		result.add(this.getLeatherChestPlateP2());
		result.add(this.getArrow());
		result.add(this.getBowP1());
		result.add(this.getWeb());
		result.add(this.getStoneSword());
		result.add(this.getStoneSwordT1());
		result.add(this.getHealthPotion2());
		result.add(this.getFireResistancePotion2());
		result.add(this.getPoisonPotion());
		result.add(this.getSpeedPotion1());
		result.add(this.getSpeedPotion2());
		result.add(this.getFlintAndSteal());
		result.add(this.getChainmailBootsP1());
		result.add(this.getChainmailLeggingsP1());
		result.add(this.getChainmailLeggings());
		result.add(this.getGoldenHelmet());
		result.add(this.getGoldenChestPlate());
		result.add(this.getGoldenLeggings());
		result.add(this.getGoldenBoots());
		result.add(this.getGoldenHelmetP1());
		result.add(this.getGoldenChestPlateP1());
		result.add(this.getGoldenLeggingsP1());
		result.add(this.getGoldenBootsP1());
		result.add(this.getGoldenHelmetP2());
		result.add(this.getGoldenChestPlateP2());
		result.add(this.getGoldenLeggingsP2());
		result.add(this.getGoldenBootsP2());
		result.add(SpecialItemsHandler.getWaterBucket());
		result.add(SpecialItemsHandler.getLavaBucket());
		result.add(this.getWoodenPickaxe());
		result.add(this.getStonePickaxe());
		result.add(this.getIronPickaxe());
		result.add(this.getDiamondPickaxe());
		result.add(this.getWoodenPickaxeE1());
		result.add(this.getStonePickaxeE1());
		result.add(this.getIronPickaxeE1());
		result.add(this.getDiamondPickaxeE1());
		result.add(this.getWoodenAxe());
		result.add(this.getStoneAxe());
		result.add(this.getIronAxe());
		result.add(this.getDiamondAxe());
		result.add(this.getWoodenAxeE1());
		result.add(this.getStoneAxeE1());
		result.add(this.getIronAxeE1());
		result.add(this.getDiamondAxeE1());
		return result;
	}

	@Override
	public int getMaxItems() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int getMinItems() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
	private ItemStack getGoldenApple()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.GOLDEN_APPLE, r.nextInt(4 - 1) + 1);
		return it;
	}
	
	private ItemStack getWood()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.WOOD, r.nextInt(64 - 32) + 32);
		return it;
	}
	
	private ItemStack getStone()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.STONE, r.nextInt(64 - 16) + 16);
		return it;
	}
	
	private ItemStack getHay()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.HAY_BLOCK, r.nextInt(32 - 16) + 16);
		return it;
	}
	
	private ItemStack getGlass()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.GLASS, r.nextInt(32 - 16) + 16);
		return it;
	}
	
	private ItemStack getEnderPearl()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.ENDER_PEARL, r.nextInt(3 - 1) + 1);
		return it;
	}
	
	private ItemStack getStoneSwordT2()
	{
		ItemStack it = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondSword()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_SWORD, 1);
		return it;
	}
	
	private ItemStack getDiamondSwordS1()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronSwordT2()
	{
		ItemStack it = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getFishingRod()
	{
		ItemStack it = new ItemStack(Material.FISHING_ROD, 1);
		return it;
	}
	
	private ItemStack getWoodenSword()
	{
		ItemStack it = new ItemStack(Material.WOOD_SWORD, 1);
		return it;
	}
	
	private ItemStack getWoodenSwordT1()
	{
		ItemStack it = new ItemStack(Material.WOOD_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getBow()
	{
		ItemStack it = new ItemStack(Material.BOW, 1);
		return it;
	}
	
	private ItemStack get16Egg()
	{
		ItemStack it = new ItemStack(Material.EGG, 16);
		return it;
	}
	
	private ItemStack get8Egg()
	{
		ItemStack it = new ItemStack(Material.EGG, 8);
		return it;
	}
	
	private ItemStack get16SnowBall()
	{
		ItemStack it = new ItemStack(Material.SNOW_BALL, 16);
		return it;
	}
	
	private ItemStack get8SnowBall()
	{
		ItemStack it = new ItemStack(Material.SNOW_BALL, 8);
		return it;
	}
	
	private ItemStack getDiamondHelmet()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_HELMET, 1);
		return it;
	}
	
	private ItemStack getDiamondBoots()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_BOOTS, 1);
		return it;
	}
	
	private ItemStack getLeatherChestPlate()
	{
		ItemStack it = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		return it;
	}
	
	private ItemStack getLeatherChestPlateP1()
	{
		ItemStack it = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getLeatherLeggingsP1()
	{
		ItemStack it = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getLeatherLeggingsP2()
	{
		ItemStack it = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailLeggingsP2()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailHelmetP2()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_HELMET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailChestPlateP2()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailBootsP2()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronChestPlateP1()
	{
		ItemStack it = new ItemStack(Material.IRON_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getLeatherChestPlateP2()
	{
		ItemStack it = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getArrow()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.ARROW, r.nextInt(16 - 8) + 8);
		return it;
	}
	
	private ItemStack getBowP1()
	{
		ItemStack it = new ItemStack(Material.BOW, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getWeb()
	{
		Random r = new Random();
		ItemStack it = new ItemStack(Material.WEB, r.nextInt(8 - 2) + 2);
		return it;
	}
	
	private ItemStack getStoneSword()
	{
		ItemStack it = new ItemStack(Material.STONE_SWORD, 1);
		return it;
	}
	
	private ItemStack getStoneSwordT1()
	{
		ItemStack it = new ItemStack(Material.STONE_SWORD, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getHealthPotion2(){
		ItemStack it = new ItemStack(Material.SPLASH_POTION);
		PotionMeta itm = (PotionMeta) it.getItemMeta();

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		itm.addCustomEffect(potionEffect,true);
		itm.setDisplayName("§fPotion de soins II");

		it.setItemMeta(itm);
		
		return it;
	}
	
	private ItemStack getFireResistancePotion2(){
		ItemStack it = new ItemStack(Material.SPLASH_POTION);
		PotionMeta itm = (PotionMeta) it.getItemMeta();

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 1);
		itm.addCustomEffect(potionEffect,true);
		itm.setDisplayName("§fPotion de resistance au feu II (60 secondes)");

		it.setItemMeta(itm);
		
		return it;
	}
	
	private ItemStack getPoisonPotion(){
		ItemStack it = new ItemStack(Material.SPLASH_POTION);
		PotionMeta itm = (PotionMeta) it.getItemMeta();

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.POISON, 600, 0);
		itm.addCustomEffect(potionEffect,true);
		itm.setDisplayName("§fPotion de poison I (30 secondes)");

		it.setItemMeta(itm);
		
		return it;
	}
	
	private ItemStack getSpeedPotion1(){
		ItemStack it = new ItemStack(Material.SPLASH_POTION);
		PotionMeta itm = (PotionMeta) it.getItemMeta();

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 600, 0);
		itm.addCustomEffect(potionEffect,true);
		itm.setDisplayName("§fPotion de rapidité I (30 secondes)");

		it.setItemMeta(itm);
		
		return it;
	}
	
	private ItemStack getSpeedPotion2(){
		ItemStack it = new ItemStack(Material.SPLASH_POTION);
		PotionMeta itm = (PotionMeta) it.getItemMeta();

		PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, 300, 1);
		itm.addCustomEffect(potionEffect,true);
		itm.setDisplayName("§fPotion de rapidité II (15 secondes)");

		it.setItemMeta(itm);
		
		return it;
	}
	
	private ItemStack getFlintAndSteal()
	{
		ItemStack it = new ItemStack(Material.FLINT_AND_STEEL, 1);
		return it;
	}
	
	private ItemStack getChainmailBootsP1()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailLeggingsP1()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getChainmailLeggings()
	{
		ItemStack it = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
		return it;
	}
	
	private ItemStack getGoldenHelmet()
	{
		ItemStack it = new ItemStack(Material.GOLD_HELMET, 1);
		return it;
	}
	
	private ItemStack getGoldenChestPlate()
	{
		ItemStack it = new ItemStack(Material.GOLD_CHESTPLATE, 1);
		return it;
	}
	
	private ItemStack getGoldenLeggings()
	{
		ItemStack it = new ItemStack(Material.GOLD_LEGGINGS, 1);
		return it;
	}
	
	private ItemStack getGoldenBoots()
	{
		ItemStack it = new ItemStack(Material.GOLD_BOOTS, 1);
		return it;
	}

	private ItemStack getGoldenHelmetP1()
	{
		ItemStack it = new ItemStack(Material.GOLD_HELMET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenChestPlateP1()
	{
		ItemStack it = new ItemStack(Material.GOLD_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenLeggingsP1()
	{
		ItemStack it = new ItemStack(Material.GOLD_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenBootsP1()
	{
		ItemStack it = new ItemStack(Material.GOLD_BOOTS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenHelmetP2()
	{
		ItemStack it = new ItemStack(Material.GOLD_HELMET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenChestPlateP2()
	{
		ItemStack it = new ItemStack(Material.GOLD_CHESTPLATE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenLeggingsP2()
	{
		ItemStack it = new ItemStack(Material.GOLD_LEGGINGS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getGoldenBootsP2()
	{
		ItemStack it = new ItemStack(Material.GOLD_BOOTS, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		
		it.setItemMeta(meta);
		return it;
	}

	
	private ItemStack getWoodenPickaxe() {
		ItemStack it = new ItemStack(Material.WOOD_PICKAXE,1);
		return it;
	}
	
	private ItemStack getStonePickaxe() {
		ItemStack it = new ItemStack(Material.STONE_PICKAXE,1);
		return it;
	}
	
	private ItemStack getIronPickaxe() {
		ItemStack it = new ItemStack(Material.IRON_PICKAXE,1);
		return it;
	}
	
	private ItemStack getDiamondPickaxe() {
		ItemStack it = new ItemStack(Material.DIAMOND_PICKAXE,1);
		return it;
	}
	
	private ItemStack getWoodenPickaxeE1()
	{
		ItemStack it = new ItemStack(Material.WOOD_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getStonePickaxeE1()
	{
		ItemStack it = new ItemStack(Material.STONE_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronPickaxeE1()
	{
		ItemStack it = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondPickaxeE1()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getWoodenAxe() {
		ItemStack it = new ItemStack(Material.WOOD_AXE,1);
		return it;
	}
	
	private ItemStack getStoneAxe() {
		ItemStack it = new ItemStack(Material.STONE_AXE,1);
		return it;
	}
	
	private ItemStack getIronAxe() {
		ItemStack it = new ItemStack(Material.IRON_AXE,1);
		return it;
	}
	
	private ItemStack getDiamondAxe() {
		ItemStack it = new ItemStack(Material.DIAMOND_AXE,1);
		return it;
	}
	
	private ItemStack getWoodenAxeE1()
	{
		ItemStack it = new ItemStack(Material.WOOD_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getStoneAxeE1()
	{
		ItemStack it = new ItemStack(Material.STONE_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getIronAxeE1()
	{
		ItemStack it = new ItemStack(Material.IRON_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private ItemStack getDiamondAxeE1()
	{
		ItemStack it = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
		
		it.setItemMeta(meta);
		return it;
	}
}
