// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.stats;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.background.Config;

public class StatsCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public StatsCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("stats").setExecutor(this);
		System.out.println("[BuildFFA] Stats loaded.");	
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[BuildFFA] Nur Spieler können diesen Command ausführen.");
			return true;
		}
		Player p = (Player) sender;
		
		if ((cmd.getName().equalsIgnoreCase("stats")) && (args.length == 0)) {
			p.sendMessage(Config.PluginPrefix+" §eStats.");
			// TODO Stats
	    	return true;

	    }
		else if ((cmd.getName().equalsIgnoreCase("stats")) && (args.length != 0)) {
			if (p.hasPermission("buildffa.admin")) {
		    	if(args[0].equalsIgnoreCase("stats"))
		    	{
		    		p.sendMessage(Config.PluginPrefix+" §eStats eines anderen Spielers.");
		    		// TODO Stats eines anderen Spielers.
		    	}
		    	
			}
			else {
				p.sendMessage(Config.PluginPrefix+" §cDu hast keine Berechtigungen, diesen Command auszuführen.");
			}
		}
		return false;
	}
}