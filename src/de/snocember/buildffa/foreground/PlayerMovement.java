// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import de.snocember.buildffa.Config;
//import de.snocember.buildffa.Kits;
import de.snocember.buildffa.Main;
import de.snocember.buildffa.kits.ConfigKits;


public class PlayerMovement implements Listener {
	@SuppressWarnings("unused")
	private Main plugin;

	public PlayerMovement(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] PlayerMovement loaded.");
	}

	@EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Location ploc = p.getLocation();
        Double ploc_y = ploc.getY();
        if(ploc_y <= Config.SpawnCoordY -3 && ploc_y >= Config.SpawnCoordY -4.0) {
        	if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
        		//p.sendMessage("§7DEBUG: Unter Spawn.");
            	addKit(p, 0);
            	p.setGameMode(GameMode.SURVIVAL);
        	}
        }
        if(ploc_y <= -1) {
        	p.damage(40.0);
        }
	}
	
	@SuppressWarnings("unchecked")
	void addKit(Player p, int KitNr) {
		ArrayList<ItemStack> kit = (ArrayList<ItemStack>) ConfigKits.kits.get(KitNr);
		ArrayList<ItemStack> kit_armour = (ArrayList<ItemStack>) ConfigKits.kits_armour.get(KitNr);
		Integer arraySize = kit.size();
		
		for(int i=0; i<arraySize; i++) {
			p.getInventory().setItem(i, kit.get(i));
		}
		p.getInventory().setBoots(kit_armour.get(0));
		p.getInventory().setLeggings(kit_armour.get(1));
		p.getInventory().setChestplate(kit_armour.get(2));
		p.getInventory().setHelmet(kit_armour.get(3));

	}
}
