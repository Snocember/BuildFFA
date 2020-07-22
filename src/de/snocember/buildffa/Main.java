// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.snocember.buildffa.background.NoDamage;
import de.snocember.buildffa.background.NoDrop;
import de.snocember.buildffa.background.NoHunger;
import de.snocember.buildffa.background.PlaceBlock;
import de.snocember.buildffa.commands.GameCommand;
import de.snocember.buildffa.foreground.BreakBlock;
import de.snocember.buildffa.foreground.PlayerDeath;
import de.snocember.buildffa.foreground.PlayerJoinLeave;
import de.snocember.buildffa.foreground.PlayerMovement;
import de.snocember.buildffa.foreground.RemoveBlockQueue;
import de.snocember.buildffa.kits.ConfigKits;
import de.snocember.buildffa.stats.StatsCommand;

public class Main extends JavaPlugin {
	
	public static String DebugOn = "0";
	
	@Override
	public void onEnable() {
		new Config(this);
		new ConfigKits(this);
		
		new GameCommand(this);
		new StatsCommand(this);
		
		new PlayerJoinLeave(this);
		new PlayerDeath(this);
		
		new PlayerMovement(this);
		
		new BreakBlock(this);
		new PlaceBlock(this);
		new NoDrop(this);
		new RemoveBlockQueue(this);
		
		new NoDamage(this);
		new NoHunger(this);
		
		Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		for(Player p : playerlist) {
			p.getInventory().clear();
			p.teleport(Config.wspawn);
			p.setHealth(20);
		}
				
	}
}
