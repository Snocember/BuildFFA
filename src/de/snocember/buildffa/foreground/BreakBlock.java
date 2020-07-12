// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.background.Config;

public class BreakBlock implements Listener {
	@SuppressWarnings("unused")
	private Main plugin;

	public BreakBlock(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] BreakBlock loaded.");
	}
	
	@EventHandler
    public void onPlaceBlockEvent(BlockBreakEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getPlayer();
        if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
        	e.setCancelled(true);
        	//p.sendMessage(Config.PluginPrefix+" §cDu darfst keine Blöcke abbauen!"); TODO in config an/aus
        }
	}
}
