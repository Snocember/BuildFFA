// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;


import de.snocember.buildffa.Main;

public class ConfigKits {
	
	private File file;
	public static FileConfiguration cfg;

	public static Map<String, Object> kitMap = new HashMap<String, Object>();
	public static ArrayList<Object> kits = new ArrayList<>();
	
	public static ArrayList<Object> kits_armour = new ArrayList<>();

	
	

	@SuppressWarnings("unused")
	private Main plugin;

	@SuppressWarnings("deprecation")
	public ConfigKits(Main plugin) {
		this.plugin = plugin;
		file = new File("plugins/BuildFFA","kits.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		loadConfiguration();
		
		Integer KitsNumber = cfg.getInt("Kits.Number");
		
		for(int i=0; i< KitsNumber; i++) {
			ArrayList<ItemStack> einzelKit = new ArrayList<>();
			ArrayList<ItemStack> einzelKit_armour = new ArrayList<>();
			
			String ItemStackMaterial = cfg.getString("Kits.kit"+i+".ItemStackMaterial");
			kitMap.put("Kits.kit"+i+".ItemStackMaterial", Material.getMaterial(ItemStackMaterial));
			String DisplayName = cfg.getString("Kits.kit"+i+".DisplayName").replaceAll("&", "§");
			kitMap.put("Kits.kit"+i+".DisplayName", DisplayName);
			
			Boolean HasDescription = cfg.getBoolean("Kits.kit"+i+".Description.hasDescription");
			kitMap.put("Kits.kit"+i+".Description.hasDescription", HasDescription);
			if(HasDescription) {
				String Description1 = cfg.getString("Kits.kit"+i+".Description.Description1").replaceAll("&", "§");
				kitMap.put("Kits.kit"+i+".Description.Description1", Description1);
				String Description2 = cfg.getString("Kits.kit"+i+".Description.Description2").replaceAll("&", "§");
				kitMap.put("Kits.kit"+i+".Description.Description2", Description2);
			}
			Integer ItemsFromslot0 = cfg.getInt("Kits.kit"+i+".ItemsFromslot0");
			kitMap.put("Kits.kit"+i+".ItemsFromslot0", ItemsFromslot0);
			
			for(int u=0; u<ItemsFromslot0; u++) {
				System.out.print("[BuildFFA] DEBUG: ConfigKits: i="+i+",u="+u);
				String itemMaterial = cfg.getString("Kits.kit"+i+".slot"+u+".ItemStackMaterial");
				System.out.print("[BuildFFA] DEBUG: ConfigKits: itemMaterial="+itemMaterial);
				Integer itemAmount = cfg.getInt("Kits.kit"+i+".slot"+u+".StackAmount");
				ItemStack item = new ItemStack(Material.getMaterial(itemMaterial), itemAmount);
				ItemMeta itemM = item.getItemMeta();
				
				String itemDisplayName = cfg.getString("Kits.kit"+i+".slot"+u+".DisplayName").replaceAll("&", "§");
				System.out.print("[BuildFFA] DEBUG: ConfigKits: itemDisplayName="+itemDisplayName);
				itemM.setDisplayName(itemDisplayName);

				Boolean itemHasDescription = cfg.getBoolean("Kits.kit"+i+".slot"+u+".Description.hasDescription");
				if(itemHasDescription) {
					String itemDescription1 = cfg.getString("Kits.kit"+i+".slot"+u+".Description.Description1").replaceAll("&", "§");
					System.out.print("[BuildFFA] DEBUG: ConfigKits: itemDescription1="+itemDescription1+"=");
					String itemDescription2 = cfg.getString("Kits.kit"+i+".slot"+u+".Description.Description2").replaceAll("&", "§");
					ArrayList<String> description = new ArrayList<String>();
					if(!itemDescription1.equalsIgnoreCase("None")) {
						description.add(itemDescription1);
						if(!itemDescription2.equalsIgnoreCase("None")) {
							description.add(itemDescription2);
						}
						itemM.setLore(description); //TODO BUG
					}
				}
				Short itemDurability = (short) cfg.getInt("Kits.kit"+i+".slot"+u+".ShortDurability");
				item.setDurability((short) itemDurability);
				
				Boolean itemHasEnchantments = cfg.getBoolean("Kits.kit"+i+".slot"+u+".Enchantments.hasEnchantments");
				if(itemHasEnchantments) {
					Integer itemNumberEnchantments = cfg.getInt("Kits.kit"+i+".slot"+u+".Enchantments.numberEnchantments");
					for(int k=0; k < itemNumberEnchantments; k++) {
						Integer enchId = cfg.getInt("Kits.kit"+i+".slot"+u+".Enchantments.ench"+k+".id");
						Enchantment ench = Enchantment.getById(enchId);
						Integer level = cfg.getInt("Kits.kit"+i+".slot"+u+".Enchantments.ench"+k+".level");
						System.out.print("[BuildFFA] DEBUG: ConfigKits: ench="+ench+",level="+level);
						itemM.addEnchant(ench, level, true);
					}
				}
				
				Boolean itemHideEnchantments = cfg.getBoolean("Kits.kit"+i+".slot"+u+".Enchantments.hideEnchantments");
				if(itemHideEnchantments) {
					itemM.addItemFlags(ItemFlag.valueOf("HIDE_ENCHANTS"));
				}
				Boolean itemHideAttributes = cfg.getBoolean("Kits.kit"+i+".slot"+u+".hideAttributes");
				if(itemHideAttributes) {
					itemM.addItemFlags(ItemFlag.valueOf("HIDE_ATTRIBUTES"));
					
				}
				Boolean itemHideUnbreakable = cfg.getBoolean("Kits.kit"+i+".slot"+u+".hideUnbreakable");
				if(itemHideUnbreakable) {
					itemM.addItemFlags(ItemFlag.valueOf("HIDE_UNBREAKABLE"));
				}

				item.setItemMeta(itemM);
				
				Integer StackAmount = cfg.getInt("Kits.kit"+i+".slot"+u+".StackAmount");
				kitMap.put("Kits.kit"+i+".slot"+u+".StackAmount", StackAmount);
				
				kitMap.put("Kits.kit"+i+".slot."+u+".item", item);
				einzelKit.add(item);
				
			}
			kits.add(einzelKit);
			
			String[] armourL = new String[] {"boots", "leggins", "chestplate", "helmet"};
			for (int k=0; k<4; k++) {
				String armourMaterial = cfg.getString("Kits.kit"+i+"."+armourL[k]+".ItemStackMaterial");
				System.out.print("[BuildFFA] DEBUG: ArmourItemStackMaterial: "+armourMaterial);
				if(!armourMaterial.equalsIgnoreCase("AIR")) {
					if(armourMaterial.contains("LEATHER")) {					
						ItemStack armour = new ItemStack(Material.getMaterial(armourMaterial), 1);
						LeatherArmorMeta itemM = (LeatherArmorMeta)armour.getItemMeta();
						
						// TODO Lederrüstung farbe
						String colors = cfg.getString("Kits.kit"+i+"."+armourL[k]+".LeatherColorRGB");
						List<String> colorList = Arrays.asList(colors.split("\\s*,\\s*"));
						System.out.print("[BuildFFA] DEBUG: LeatherColorRGB: "+colors);
						System.out.print("[BuildFFA] DEBUG: colorList[1]: "+colorList.get(1));
						Integer colorR = Integer.parseInt(colorList.get(0));
						Integer colorG = Integer.parseInt(colorList.get(1));
						Integer colorB = Integer.parseInt(colorList.get(2));
						System.out.print("[BuildFFA] DEBUG: R: "+colorR+", G: "+colorG+", B: "+colorB);
						// TODO BUG wenn colorR, G, B unten eingesetzt (NullPointerException)
						itemM.setColor( Color.fromRGB(colorR, colorG, colorB) );
						
						String armourDisplayName = cfg.getString("Kits.kit"+i+"."+armourL[k]+".DisplayName").replaceAll("&", "§");;
						itemM.setDisplayName(armourDisplayName);
					
						Boolean armourHasDescription = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Description.hasDescription");
						if(armourHasDescription) {
							String armourDescription1 = cfg.getString("Kits.kit"+i+"."+armourL[k]+".Description.Description1").replaceAll("&", "§");;
							String armourDescription2 = cfg.getString("Kits.kit"+i+"."+armourL[k]+".Description.Description2").replaceAll("&", "§");;
							ArrayList<String> description = new ArrayList<String>();
							if(!armourDescription1.equalsIgnoreCase("None")) { //TODO BUG
								description.add(armourDescription1);
								if(!armourDescription2.equalsIgnoreCase("None")) { //TODO BUG
									
								}
								itemM.setLore(description);
							}
						}

						Boolean armourHasEnchantments = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Enchantments.hasEnchantments");
						if(armourHasEnchantments) {
							Integer armourNumberEnchantments = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.numberEnchantments");
							for(int l=0; l < armourNumberEnchantments; l++) {
								Integer enchId = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.ench"+l+".id");
								Enchantment ench = Enchantment.getById(enchId);
								Integer level = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.ench"+l+".level");
								System.out.print("[BuildFFA] DEBUG: ConfigKits: ench="+ench+",level="+level);
								itemM.addEnchant(ench, level, true);
							}
						}
						
					    Boolean itemHideEnchantments = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Enchantments.hideEnchantments");
						if(itemHideEnchantments) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_ENCHANTS"));
						}
						Boolean itemHideAttributes = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".hideAttributes");
						if(itemHideAttributes) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_ATTRIBUTES"));
						}
						Boolean itemHideUnbreakable = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".hideUnbreakable");
						if(itemHideUnbreakable) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_UNBREAKABLE"));
						}
						
						armour.setItemMeta(itemM);
						einzelKit_armour.add(armour);
					
					}
					else {
						ItemStack armour = new ItemStack(Material.getMaterial(armourMaterial));
						ItemMeta itemM = armour.getItemMeta();
						
						String armourDisplayName = cfg.getString("Kits.kit"+i+"."+armourL[k]+".DisplayName").replaceAll("&", "§");;
						itemM.setDisplayName(armourDisplayName);
						
						Boolean armourHasDescription = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Description.hasDescription");
						if(armourHasDescription) {
							String armourDescription1 = cfg.getString("Kits.kit"+i+"."+armourL[k]+".Description.Description1").replaceAll("&", "§");;
							String armourDescription2 = cfg.getString("Kits.kit"+i+"."+armourL[k]+".Description.Description2").replaceAll("&", "§");;
							ArrayList<String> description = new ArrayList<String>();
							if(!armourDescription1.equalsIgnoreCase("None")) {
								description.add(armourDescription1);
								if(!armourDescription2.equalsIgnoreCase("None")) {
									description.add(armourDescription2);
								}
								itemM.setLore(description); //TODO BUG
							}
						}
	
						Boolean armourHasEnchantments = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Enchantments.hasEnchantments");
						if(armourHasEnchantments) {
							Integer armourNumberEnchantments = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.numberEnchantments");
							for(int l=0; l < armourNumberEnchantments; l++) {
								Integer enchId = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.ench"+l+".id");
								Enchantment ench = Enchantment.getById(enchId);
								Integer level = cfg.getInt("Kits.kit"+i+"."+armourL[k]+".Enchantments.ench"+l+".level");
								System.out.print("[BuildFFA] DEBUG: ConfigKits: ench="+ench+",level="+level);
								itemM.addEnchant(ench, level, true);
							}
						}
						
					    Boolean itemHideEnchantments = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".Enchantments.hideEnchantments");
						if(itemHideEnchantments) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_ENCHANTS"));
						}
						Boolean itemHideAttributes = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".hideAttributes");
						if(itemHideAttributes) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_ATTRIBUTES"));
						}
						Boolean itemHideUnbreakable = cfg.getBoolean("Kits.kit"+i+"."+armourL[k]+".hideUnbreakable");
						if(itemHideUnbreakable) {
							itemM.addItemFlags(ItemFlag.valueOf("HIDE_UNBREAKABLE"));
						}
						
						armour.setItemMeta(itemM);
						einzelKit_armour.add(armour);
						
					}
					
				}
				else {
					ItemStack armour = new ItemStack(Material.AIR);
					einzelKit_armour.add(armour);
				}
				
			}
			kits_armour.add(einzelKit_armour);
//			einzelKit_armour.clear();
//			einzelKit.clear();
		}
		
		System.out.println("[BuildFFA] ConfigKits geladen.");
		System.out.println("[BuildFFA] Anzahl Kits: "+String.valueOf(kits.size()));
	}

	public void loadConfiguration() {
		String path = "Kits.Number";
	    cfg.addDefault(path, 1);
	    String path0 = "Kits.kit0.ItemStackMaterial";
	    cfg.addDefault(path0, "LEATHER_CHESTPLATE");
	    String path1 = "Kits.kit0.DisplayName";
	    cfg.addDefault(path1, "&7Standard-Kit");
	    String pathDes = "Kits.kit0.Description.hasDescription";
	    cfg.addDefault(pathDes, false);
	    String path2 = "Kits.kit0.Description.Description1";
	    cfg.addDefault(path2, "");
	    String path3 = "Kits.kit0.Description.Description2";
	    cfg.addDefault(path3, "");
	    String path5 = "Kits.kit0.ItemsFromslot0";
	    cfg.addDefault(path5, 3);    
	    
	    String kitPath00 = "Kits.kit0.slot0.ItemStackMaterial";
	    cfg.addDefault(kitPath00, "STONE_SWORD");
	    String kitPath01 = "Kits.kit0.slot0.DisplayName";
	    cfg.addDefault(kitPath01, "&eSchwert");
	    String pathDes0 = "Kits.kit0.slot0.Description.hasDescription";
	    cfg.addDefault(pathDes0, false);
	    String kitPath02 = "Kits.kit0.slot0.Description.Description1";
	    cfg.addDefault(kitPath02, "");
	    String kitPath03 = "Kits.kit0.slot0.Description.Description2";
	    cfg.addDefault(kitPath03, "");
	    String kitPath04 = "Kits.kit0.slot0.Enchantments.hasEnchantments";
	    cfg.addDefault(kitPath04, false);
	    String kitPath05 = "Kits.kit0.slot0.ShortDurability";
	    cfg.addDefault(kitPath05, 0);
	    String kitPath05_hideAttr = "Kits.kit0.slot0.hideAttributes";
	    cfg.addDefault(kitPath05_hideAttr, false);
	    String kitPath05_hideUnbr = "Kits.kit0.slot0.hideUnbreakable";
	    cfg.addDefault(kitPath05_hideUnbr, false);
	    String kitPath06 = "Kits.kit0.slot0.StackAmount";
	    cfg.addDefault(kitPath06, 1);    

	    String kitPath10 = "Kits.kit0.slot1.ItemStackMaterial";
	    cfg.addDefault(kitPath10, "STICK");
	    String kitPath11 = "Kits.kit0.slot1.DisplayName";
	    cfg.addDefault(kitPath11, "&eKnockback-Stick");
	    String pathDes1 = "Kits.kit0.slot1.Description.hasDescription";
	    cfg.addDefault(pathDes1, true);
	    String kitPath12 = "Kits.kit0.slot1.Description.Description1";
	    cfg.addDefault(kitPath12, "Knock sie alle weg!");
	    String kitPath13 = "Kits.kit0.slot1.Description.Description2";
	    cfg.addDefault(kitPath13, "");
	    String kitPath14 = "Kits.kit0.slot1.Enchantments.hasEnchantments";
	    cfg.addDefault(kitPath14, true);
	    String kitPath14_1 = "Kits.kit0.slot1.Enchantments.numberEnchantments";
	    cfg.addDefault(kitPath14_1, 1);
	    String kitPath14_hideEnch = "Kits.kit0.slot1.Enchantments.hideEnchantments";
	    cfg.addDefault(kitPath14_hideEnch, false); 
	    String kitPath14_2 = "Kits.kit0.slot1.Enchantments.ench0.id";
	    cfg.addDefault(kitPath14_2, 19); 
	    String kitPath14_3 = "Kits.kit0.slot1.Enchantments.ench0.level";
	    cfg.addDefault(kitPath14_3, 2); 
	    String kitPath15 = "Kits.kit0.slot1.ShortDurability";
	    cfg.addDefault(kitPath15, 0);
	    String kitPath15_hideAttr = "Kits.kit0.slot1.hideAttributes";
	    cfg.addDefault(kitPath15_hideAttr, false);
	    String kitPath15_hideUnbr = "Kits.kit0.slot1.hideUnbreakable";
	    cfg.addDefault(kitPath15_hideUnbr, false);     
	    String kitPath16 = "Kits.kit0.slot1.StackAmount";
	    cfg.addDefault(kitPath16, 1);

	    String kitPath20 = "Kits.kit0.slot2.ItemStackMaterial";
	    cfg.addDefault(kitPath20, "SANDSTONE");
	    String kitPath21 = "Kits.kit0.slot2.DisplayName";
	    cfg.addDefault(kitPath21, "&7Blöcke");
	    String pathDes2 = "Kits.kit0.slot2.Description.hasDescription";
	    cfg.addDefault(pathDes2, false);
	    String kitPath22 = "Kits.kit0.slot2.Description.Description1";
	    cfg.addDefault(kitPath22, "");
	    String kitPath23 = "Kits.kit0.slot2.Description.Description2";
	    cfg.addDefault(kitPath23, "");
	    String kitPath24 = "Kits.kit0.slot2.Enchantments.hasEnchantments";
	    cfg.addDefault(kitPath24, false);
	    String kitPath25 = "Kits.kit0.slot2.ShortDurability";
	    cfg.addDefault(kitPath25, 2);
	    String kitPath25_hideAttr = "Kits.kit0.slot2.hideAttributes";
	    cfg.addDefault(kitPath25_hideAttr, false);
	    String kitPath25_hideUnbr = "Kits.kit0.slot2.hideUnbreakable";
	    cfg.addDefault(kitPath25_hideUnbr, false);
	    String kitPath26 = "Kits.kit0.slot2.StackAmount";
	    cfg.addDefault(kitPath26, 32);
	    
	    String kitPathBoots0 = "Kits.kit0.boots.ItemStackMaterial";
	    cfg.addDefault(kitPathBoots0, "AIR");
	    
	    String kitPathLeggins0 = "Kits.kit0.leggins.ItemStackMaterial";
	    cfg.addDefault(kitPathLeggins0, "LEATHER_LEGGINGS");
	    String kitPathLeggins1 = "Kits.kit0.leggins.DisplayName";
	    cfg.addDefault(kitPathLeggins1, "&7Lederhose");
	    String pathDesL = "Kits.kit0.leggins.Description.hasDescription";
	    cfg.addDefault(pathDesL, false);
	    String kitPathLeggins2 = "Kits.kit0.leggins.Description.Description1";
	    cfg.addDefault(kitPathLeggins2, "");
	    String kitPathLeggins3 = "Kits.kit0.leggins.Description.Description2";
	    cfg.addDefault(kitPathLeggins3, "");
	    String kitPathLeggins4 = "Kits.kit0.leggins.Enchantments.hasEnchantments";
	    cfg.addDefault(kitPathLeggins4, false);
	    String kitPathLeggins5 = "Kits.kit0.leggins.LeatherColorRGB";
	    cfg.addDefault(kitPathLeggins5, "0,255,255");
	    String kitPathLeggins_hideAttr = "Kits.kit0.leggins.hideAttributes";
	    cfg.addDefault(kitPathLeggins_hideAttr, false);
	    String kitPathLeggins_hideUnbr = "Kits.kit0.leggins.hideUnbreakable";
	    cfg.addDefault(kitPathLeggins_hideUnbr, false);
	    
	    String kitPathChestplate0 = "Kits.kit0.chestplate.ItemStackMaterial";
	    cfg.addDefault(kitPathChestplate0, "LEATHER_CHESTPLATE");
	    String kitPathChestplate1 = "Kits.kit0.chestplate.DisplayName";
	    cfg.addDefault(kitPathChestplate1, "&7Lederhose");
	    String pathDesC = "Kits.kit0.chestplate.Description.hasDescription";
	    cfg.addDefault(pathDesC, false);
	    String kitPathChestplate2 = "Kits.kit0.chestplate.Description.Description1";
	    cfg.addDefault(kitPathChestplate2, "");
	    String kitPathChestplate3 = "Kits.kit0.chestplate.Description.Description2";
	    cfg.addDefault(kitPathChestplate3, "");
	    String kitPathChestplate4 = "Kits.kit0.chestplate.Enchantments.hasEnchantments";
	    cfg.addDefault(kitPathChestplate4, false);
	    String kitPathChestplate5 = "Kits.kit0.chestplate.LeatherColorRGB";
	    cfg.addDefault(kitPathChestplate5, "0,255,255");
	    String kitPathChestplate_hideAttr = "Kits.kit0.chestplate.hideAttributes";
	    cfg.addDefault(kitPathChestplate_hideAttr, false);
	    String kitPathChestplate_hideUnbr = "Kits.kit0.chestplate.hideUnbreakable";
	    cfg.addDefault(kitPathChestplate_hideUnbr, false);
	    
	    String kitPathHelmet0 = "Kits.kit0.helmet.ItemStackMaterial";
	    cfg.addDefault(kitPathHelmet0, "AIR");
	    
	    cfg.options().copyDefaults(true);
	    try {
			cfg.save(file);
		} catch (IOException e) { }
	}
}