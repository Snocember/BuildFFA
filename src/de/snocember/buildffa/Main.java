// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa;

import org.bukkit.plugin.java.JavaPlugin;

import de.snocember.buildffa.background.AutoGameChangeQueue;
import de.snocember.buildffa.background.GameChange;
import de.snocember.buildffa.background.NoDamage;
import de.snocember.buildffa.background.NoDrop;
import de.snocember.buildffa.background.NoHunger;
import de.snocember.buildffa.background.PlaceBlock;
import de.snocember.buildffa.commands.GameCommand;
import de.snocember.buildffa.database.ConfigDB;
import de.snocember.buildffa.database.DBController;
import de.snocember.buildffa.foreground.BreakBlock;
import de.snocember.buildffa.foreground.PlayerDeath;
import de.snocember.buildffa.foreground.PlayerJoinLeave;
import de.snocember.buildffa.foreground.PlayerMovement;
import de.snocember.buildffa.foreground.RemoveBlockQueue;
import de.snocember.buildffa.kits.ConfigKits;
import de.snocember.buildffa.stats.StatsCommand;

public class Main extends JavaPlugin {
	
	public static String DebugOn = "1";
	public static Boolean aliveService = true;
	
	@Override
	public void onEnable() {
		new Config(this);
		new ConfigKits(this);
		new ConfigDB(this);
		
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
		
		new AutoGameChangeQueue(this);
		
		GameChange.clearTeleportHeal();
		DBController.initDBConnection();
		
				
	}
	public void onDisable() {
		aliveService = false;
	}
}
