// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.foreground.RemoveBlockQueue;


public class PlaceBlock implements Listener {
	@SuppressWarnings("unused")
	private Main plugin;

	public PlaceBlock(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] PlaceBlock loaded.");
	}
	
	@EventHandler
    public void onPlaceBlockEvent(BlockPlaceEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }

        if(e.getPlayer().getGameMode() == GameMode.ADVENTURE | e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
        	Block block = e.getBlock();
            RemoveBlockQueue.addBlockQueue(block);
            Location loc = block.getLocation();
            
            if (loc.getY() >= Config.MaxBuildHeight.doubleValue()) {
            	if (loc.getY() == Config.MaxBuildHeight.doubleValue()) {
            		block.setType(Material.SPONGE);
            	}
            	else {
            		e.setCancelled(true);
            	}
            }
        }
	}
}
