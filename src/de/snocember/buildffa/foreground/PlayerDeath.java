// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.Main;
import de.snocember.buildffa.stats.StatsSystem;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;


public class PlayerDeath implements Listener {
	
	private Main plugin;

	public PlayerDeath(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] PlayerDeath loaded.");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Player k = p.getKiller();
        try {
        	String killer = k.getName();
        	event.setDeathMessage(Config.KillDeathMsgBeforeKillerName+ killer + Config.KillDeathMsgBetweenNames + p.getName() +Config.KillDeathMsgAfterPlayerName);
        	k.sendTitle("", "§2+ Kill");
//        	k.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3, 10, false, false));
        	k.setHealth(20);
        	StatsSystem.addKill(k.getUniqueId());
        	k.playSound(k.getLocation(), Sound.ORB_PICKUP, (float) 100, (float) 0.8);
        	
        	p.getInventory().clear();
        	p.setGameMode(GameMode.ADVENTURE);
        	Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
  			  @Override
  			  public void run() {
  				  (( CraftPlayer ) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
  				  // same as p.spigot().respawn();
  				  p.teleport(Config.wspawn);
  			  }
        	}, Long.valueOf(4) );
        	StatsSystem.addDeath(p.getUniqueId());
        }	
    	catch (NullPointerException err) { 
    		event.setDeathMessage(Config.DeathMsgBeforePlayerName +p.getName() +Config.DeathMsgAfterPlayerName);
    		p.getInventory().clear();
    		p.getInventory().setArmorContents(null);
    		p.setGameMode(GameMode.ADVENTURE);
    		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
    			  @Override
    			  public void run() {
    				  (( CraftPlayer ) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    				  // same as p.spigot().respawn();
    				  p.teleport(Config.wspawn);
    			  }
    		}, Long.valueOf(4) );
    		
    		StatsSystem.addDeath(p.getUniqueId());
        }
        if(Config.Death_ShowTitleWhenJoin) {
	    	p.sendTitle(Config.Death_TitleHeadline, Config.Death_TitleCaption);
	    }
	}
}
