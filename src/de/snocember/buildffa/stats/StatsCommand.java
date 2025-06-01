// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.stats;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
			try {
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
				return true;
			}
	    	catch (NullPointerException e) {
	    		if (Config.RecordStats.equals(false)) {
	    			return true;
	    		}
	    		else {
	    			if (Main.DebugOn == true) { System.err.println("[BuildFFA] Fehler beim Abrufen der Statistiken."); }
	    			p.sendMessage(Config.PluginPrefix+" §rDie Statistiken konnten nicht abgerufen werden.");
	    			return false;
	    		}
			}

	    }
		else if ((cmd.getName().equalsIgnoreCase("stats")) && (args.length != 0)) {
			try {
				if (p.hasPermission("buildffa.otherstats")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer thep = Bukkit.getOfflinePlayer(args[0]);
					UUID theuuid = thep.getUniqueId();
					int allKills = StatsSystem.getKills(theuuid)[0];
					int allDeaths = StatsSystem.getDeaths(theuuid)[0];
					@SuppressWarnings("unused")
					int test = allKills+1;
					if (allKills == 0) {
						p.sendMessage("§7-= §eStatistiken von §6"+thep.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+0+"\n§7- - - - - - - - - - - - - -");
					}
					else if (allDeaths != 0) {
						p.sendMessage("§7-= §eStatistiken von §6"+thep.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+Math.round((Double.valueOf(allKills)/Double.valueOf(allDeaths))*100.0)/100.0+"\n§7- - - - - - - - - - - - - -");
					}
					else {
						p.sendMessage("§7-= §eStatistiken von §6"+thep.getName()+" §e(Alltime) §7=-\n  §7Kills: §e"+allKills+"\n  §7Deaths: §e"+allDeaths+"\n  §7K/D: §e"+allKills+"\n§7- - - - - - - - - - - - - -");
					}
				}
				else {
					p.sendMessage(Config.PluginPrefix+" §cDu hast keine Berechtigungen, Stats anderer Spieler zu sehen.");
				}
			}
			catch (NullPointerException e) {
				p.sendMessage(Config.PluginPrefix+" §cEs gibt keine Statistiken von diesem Spieler.");
	    		System.err.println("[BuildFFA] Fehler beim Abrufen der Statistiken.");
	    		return false;
			}
		}
		return false;
	}
}