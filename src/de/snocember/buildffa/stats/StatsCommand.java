// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.stats;

import java.util.UUID;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.Main;

public class StatsCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public StatsCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("stats").setExecutor(this);
		System.out.println("[BuildFFA] StatsCommand loaded.");	
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[BuildFFA] Nur Spieler können diesen Command ausführen.");
			return true;
		}
		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		
		if ((cmd.getName().equalsIgnoreCase("stats")) && (args.length == 0)) {
			//p.sendMessage("§7-= §eStatistiken von §6"+p.getName()+" §e(Alltime) §7=-");
			int allKills = StatsSystem.getKills(uuid)[0];
			int allDeaths = StatsSystem.getDeaths(uuid)[0];
			if (allKills == 0) {
				p.sendMessage("§7-= §eStatistiken von §6"+p.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+0+"\n§7- - - - - - - - - - - - - -");
			}
			else if (allDeaths != 0) {
				p.sendMessage("§7-= §eStatistiken von §6"+p.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+Math.round((Double.valueOf(allKills)/Double.valueOf(allDeaths))*100.0)/100.0+"\n§7- - - - - - - - - - - - - -");
			}
			else {
				p.sendMessage("§7-= §eStatistiken von §6"+p.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+allKills+"\n§7- - - - - - - - - - - - - -");
			}
			// TODO Stats
	    	return true;

	    }
		else if ((cmd.getName().equalsIgnoreCase("stats")) && (args.length != 0)) {
			if (p.hasPermission("buildffa.otherstats")) {
		    	p.sendMessage(Config.PluginPrefix+" §eStats eines anderen Spielers.");
		    	// TODO Stats eines anderen Spielers.    	
			}
			else {
				p.sendMessage(Config.PluginPrefix+" §cDu hast keine Berechtigungen, Stats anderer Spieler zu sehen.");
			}
		}
		return false;
	}
}