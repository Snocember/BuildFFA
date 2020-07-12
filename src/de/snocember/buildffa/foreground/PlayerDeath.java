// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.background.Config;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

@SuppressWarnings("unused")
public class PlayerDeath implements Listener {
	
	World w = Bukkit.getServer().getWorld("world");
	Location wspawn = new Location(w, Config.SpawnCoordX, Config.SpawnCoordY, Config.SpawnCoordZ);
	
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
        	//(( CraftPlayer ) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
        	//p.spigot().respawn();
        	k.sendTitle("", "§2+ Kill");
        	k.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3, 10, false, false));
        	p.getInventory().clear();
        	p.setGameMode(GameMode.ADVENTURE);
        	p.teleport(wspawn);
        	//TODO STATS Player
        	//TODO STATS Killer
        }	
    	catch (NullPointerException err) { 
    		event.setDeathMessage(Config.DeathMsgBeforePlayerName +p.getName() +Config.DeathMsgAfterPlayerName);
    		//(( CraftPlayer ) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
    		//p.spigot().respawn();
    		p.getInventory().clear();
    		p.getInventory().setArmorContents(null);
    		p.setGameMode(GameMode.ADVENTURE);
    		p.teleport(wspawn);
    		//TODO STATS
        }
        if(Config.Death_ShowTitleWhenJoin) {
	    	p.sendTitle(Config.Death_TitleHeadline, Config.Death_TitleCaption);
	    }
	}
}
