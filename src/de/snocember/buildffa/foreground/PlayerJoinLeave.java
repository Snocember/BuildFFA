// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import static de.snocember.buildffa.background.Config.TitleCaption;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.background.Config;

/** Beim Joinen wird eine Überschrift gezeigt und eine Nachricht gesendet
 * und das Inventar aktualisiert.*/
public class PlayerJoinLeave implements Listener {

	World w = Bukkit.getServer().getWorld("world");
	Location wspawn = new Location(w, Config.SpawnCoordX, Config.SpawnCoordY, Config.SpawnCoordZ);
	
	@SuppressWarnings("unused")
	private Main plugin;

	public PlayerJoinLeave(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] PlayerJoinLeave loaded.");
	}
	/** Beim <i>PlayerJoinEvent</i> wird dem Spieler eine Überschrift mit dem Spielmodus gezeigt
	 * und eine Joinnachricht im Chat angezeigt. Das Inventar wird geleert.*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
	    if (Main.DebugOn.equals("1")) {System.out.println("[Coins] DEBUG: Joinevent!"); }
	    Player p = event.getPlayer();
	    event.setJoinMessage("");
	    p.getInventory().clear();
	    for(int i=0; i<11; i++) {
			p.sendMessage("");
		}
	    if(Config.ShowTitleWhenJoin) {
	    	p.sendTitle(Config.TitleHeadline, TitleCaption);
	    }
	    p.sendMessage(Config.PluginPrefix+" §cTeams sind auf diesem Server §4verboten§c!");
	    p.teleport(wspawn);
	    //p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2000000, 4, false, false)); //TODO
	  
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event) {
		event.setQuitMessage("");
	}
}