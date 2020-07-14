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
			p.sendMessage(Config.PluginPrefix+" §eAlltime-Stats:");
			try {
				int allKills = StatsSystem.getKills(uuid)[0];
				int allDeaths = StatsSystem.getDeaths(uuid)[0];
				if(allDeaths != 0) {
					p.sendMessage("    §2Kills§7: §2"+allKills+" §7- §cDeaths§7: §c"+allDeaths+" §7- §eK/D§7: §e"+allKills/allDeaths);
				}
				else {
					p.sendMessage("    §2Kills§7: §2"+allKills+" §7- §cDeaths§7: §c"+allDeaths+" §7- §eK/D§7: §e"+allKills);
				}
			}
			catch (NullPointerException e) {
				p.sendMessage("    §cKeine Stats vorhanden.");
				StatsSystem.loadProfile(uuid);
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