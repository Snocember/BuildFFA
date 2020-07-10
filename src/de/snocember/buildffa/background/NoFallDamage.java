// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.snocember.buildffa.Main;

@SuppressWarnings("unused")
public class NoFallDamage implements Listener {
	private Main plugin;

	public NoFallDamage(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] NoFallDamage geladen");
	}
	
	@EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        if (e.getCause() == DamageCause.FALL) {
        	e.setCancelled(true);
        }
	}
}
