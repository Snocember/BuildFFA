package de.snocember.buildffa.background;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.snocember.buildffa.Main;

public class NoDrop implements Listener {
	@SuppressWarnings("unused")
	private Main plugin;

	public NoDrop(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] NoDrop loaded.");
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
			e.setCancelled(true);
		}
	}
}
