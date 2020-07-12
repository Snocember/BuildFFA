// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.snocember.buildffa.Main;

@SuppressWarnings("unused")
public class NoDamage implements Listener {
	private Main plugin;

	public NoDamage(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] NoDamage loaded.");
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
        Location ploc = p.getLocation();
        Double ploc_y = ploc.getY();
        if(ploc_y >= Config.SpawnCoordY -4.0 && ploc_y >= Config.SpawnCoordY) {
        	e.setCancelled(true);
        }
	}
	
	@EventHandler
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        Location ploc = p.getLocation();
        Double ploc_y = ploc.getY();
        System.out.println("[BuildFFA] DEBUG: PVP: Y: "+ploc_y);
        if(ploc_y >= Config.SpawnCoordY -4.0) {
        	System.out.println("[BuildFFA] DEBUG: PVP cancelled.");
        	e.setCancelled(true);
        }
	}
}
