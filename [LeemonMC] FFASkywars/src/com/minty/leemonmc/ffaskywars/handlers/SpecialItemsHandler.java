package com.minty.leemonmc.ffaskywars.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.minty.leemonmc.ffaskywars.FFASkywars;

public class SpecialItemsHandler {

	public static ItemStack getThor()
	{
		ItemStack it = new ItemStack(Material.STONE_AXE, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		meta.setDisplayName("§6Hache de Thor §r§7 ▪ Clic-souris");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Cet objet te permet de faire §eapparaître un éclair");
		lore.add("§7à l'endroit §eauquel tu vises §7!");
		lore.add("");
		lore.add("§6» §eClic-gauche ou droit pour utiliser");
		meta.setLore(lore);
		
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getSuperAnvil() {
		ItemStack head =new ItemStack(Material.ANVIL, 1, (byte) 0);
		ItemMeta meta = head.getItemMeta();
		
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		
		meta.setDisplayName("§6Table de réparation§r§7 ▪ Clic-droit");
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Cet objet te permet, une fois placé de combiner");
		lore.add("§edeux objets §7de façon unique !");
		lore.add("");
		lore.add("§6» §eClic-droit pour placer");
		
		meta.setLore(lore);
		head.setItemMeta(meta);
		return head;
	}
	
	public static ItemStack getCoconut() {
		ItemStack head = FFASkywars.getInstance().getAPI().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/69139a5b1f91693835e34903ce34be86c926e9a1dffcb377cc5ed8f33d99821");
		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName("§6Noix de coco§r§7 ▪ Clic-droit");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Cet objet te permet, une fois utilisé de");
		lore.add("§7régénerer §edeux coeurs §7!");
		lore.add("");
		lore.add("§6» §eClic-droit pour utiliser");
		meta.setLore(lore);
		head.setItemMeta(meta);
		return head;
	}
	
	public static ItemStack getBridgeEgg() {
		ItemStack it = new ItemStack(Material.EGG, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6Super-oeuf§r§7 ▪ Clic-droit");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Cet objet te permet de, une fois lancé");
		lore.add("§7construire un §epont solide §7en face");
		lore.add("§7de toi ! Pratique pour se déplacer rapidement.");
		lore.add("");
		lore.add("§6» §eClic-droit pour utiliser");
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getWaterBucket()
	{
		ItemStack it = new ItemStack(Material.WATER_BUCKET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§bSeau d'eau");
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("§7Une fois placé disparaît au bout");
		list.add("§7de §e30 seconddes §7!");
		meta.setLore(list);
		
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getLavaBucket()
	{
		ItemStack it = new ItemStack(Material.LAVA_BUCKET, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6Seau de lave");
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("§7Une fois placé ne §ecoule pas §7et disparît au bout");
		list.add("§7de §e5 seconddes §7!");
		meta.setLore(list);
		
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getTnt(int amount)
	{
		ItemStack it = getTntRaw();
		it.setAmount(amount);
		return it;
	}
	
	private static ItemStack getTntRaw() {
		Random r = new Random();
		ItemStack it = new ItemStack(Material.TNT, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§cTNT");
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("§7Une fois placé §eexplose §7 !");
		meta.setLore(list);
		
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getFireBall() {
		ItemStack it = new ItemStack(Material.FIREBALL, 1);
		ItemMeta meta = it.getItemMeta();
		
		meta.setDisplayName("§6Boule de feu§r§7 ▪ Clic-droit");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Tu peux §elancer §7cet objet pour faire des");
		lore.add("§7dégâts §eaux joueurs §7et à §el'environnement");
		lore.add("§7et tout cela §eà distance §7!");
		lore.add("");
		lore.add("§6» §eClic-droit pour utiliser");
		
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack getLuckyBlock() {
		Random r = new Random();
		if(r.nextInt(1) == 1) {
			return getGoodLuckyBlock();
		}
		else {
			return getNormalLuckyBlock();
		}
	}
	
	public static ItemStack getGoodLuckyBlock() {
		ItemStack head = FFASkywars.getInstance().getAPI().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/89541aeb5b402dc253372eb40dcadaf32ef92cca831bd35d32b41997130cb2ee");
		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName("§5§lLucky Block§r§7 ▪ Clic-droit");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7En effectuant un §eclic-droit §7avec cet objet");
		lore.add("§7tu reçeveras un objet aléatoire des §ecoffres du milieu §7!");
		lore.add("");
		lore.add("§7Rareté : §e★★");
		lore.add("");
		lore.add("§6» §eClic-droit pour utiliser ");
		meta.setLore(lore);
		head.setItemMeta(meta);
		return head;
	}
	
	public static ItemStack getNormalLuckyBlock() {
		ItemStack head = FFASkywars.getInstance().getAPI().getLeemonUtils().getSkull("http://textures.minecraft.net/texture/4b92cb43333aa621c70eef4ebf299ba412b446fe12e341ccc582f3192189");
		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName("§eLucky Block§r§7 ▪ Clic-droit");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7En effectuant un §eclic-droit §7avec cet objet");
		lore.add("§7tu reçeveras un objet aléatoire des §ecoffres des îles §7!");
		lore.add("");
		lore.add("§7Rareté : §e★§7★");
		lore.add("");
		lore.add("§6» §eClic-droit pour utiliser ");
		meta.setLore(lore);
		head.setItemMeta(meta);
		return head;
	}
	
}
