package de.snocember.buildffa.background;

import org.bukkit.scheduler.BukkitRunnable;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.Main;

public class AutoGameChangeQueue {
	
	@SuppressWarnings("unused")
	private Main plugin;

	public AutoGameChangeQueue(Main plugin) {
		this.plugin = plugin;
		if(Config.AutoMapChangePeriod != 0) {
			startAutoMapChangeQueue(plugin);
		}
		if(Config.AutoKitChangePeriod != 0) {
			startAutoKitChangeQueue(plugin);
		}
	}

	public static void startAutoMapChangeQueue(Main plugin) {
		new BukkitRunnable() {
			
			public void run() {
		        GameChange.changeWorld();
		    }
		}.runTaskTimer(plugin, 0L, (20L*Config.AutoMapChangePeriod)); // 20L = 1 sek
		
	}
	
	public static void startAutoKitChangeQueue(Main plugin) {
		new BukkitRunnable() {
			
			public void run() {
				GameChange.changeDefaultKit();
		    }
		}.runTaskTimer(plugin, 0L, (20L*Config.AutoKitChangePeriod)); // 20L = 1 sek
		
	}
}
