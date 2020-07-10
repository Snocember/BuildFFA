// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa;

import org.bukkit.plugin.java.JavaPlugin;

import de.snocember.buildffa.background.Config;
import de.snocember.buildffa.background.ConfigKits;
import de.snocember.buildffa.background.NoDrop;
import de.snocember.buildffa.background.NoFallDamage;
import de.snocember.buildffa.background.PlaceBlock;
import de.snocember.buildffa.commands.GameCommand;
import de.snocember.buildffa.foreground.BreakBlock;
import de.snocember.buildffa.foreground.GetKit;
import de.snocember.buildffa.foreground.PlayerDeath;
import de.snocember.buildffa.foreground.PlayerJoinLeave;
import de.snocember.buildffa.foreground.RemoveBlockQueue;

public class Main extends JavaPlugin {
	
	public static String DebugOn = "0";
	
	@Override
	public void onEnable() {
		new Config(this);
		new ConfigKits(this);
		
		new GameCommand(this);
		
		new PlayerJoinLeave(this);
		new PlayerDeath(this);
		
		new GetKit(this);
		
		new BreakBlock(this);
		new PlaceBlock(this);
		new NoDrop(this);
		new RemoveBlockQueue(this);
		
		new NoFallDamage(this);
		
		
	}

}
