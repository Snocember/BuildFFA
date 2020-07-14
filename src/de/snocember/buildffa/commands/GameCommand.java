// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Config; 
import de.snocember.buildffa.Main;

public class GameCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GameCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("buildffa").setExecutor(this);
		System.out.println("[BuildFFA] Gamecommand loaded.");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[BuildFFA] Nur Spieler können diesen Command ausführen.");
			return true;
		}
		Player p = (Player) sender;
		
		if ((cmd.getName().equalsIgnoreCase("buildffa")) && (args.length == 0)) {
			if (p.hasPermission("buildffa.admin")) {
				p.sendMessage(Config.PluginPrefix+" §cSchreibe einfach \"/buildffa help\".");
			}
			else {
				p.sendMessage(Config.PluginPrefix+" §cDu hast keine Berechtigungen, diesen Command auszuführen.");
			}
	    	return true;

	    }
		else if ((cmd.getName().equalsIgnoreCase("buildffa")) && (args.length != 0)) {
			if(args[0].equalsIgnoreCase("info")) {
		    	p.sendMessage(Config.PluginPrefix+" §7by Snocember.");
		    	// Diese Notiz oberhalb darf nicht entfernt werden.
		    	return true;
		    }
			if(args[0].equalsIgnoreCase("help")) {
		    	p.sendMessage(Config.PluginPrefix+" §eHilfe.");
		    	return true;
		    }
			else if (p.hasPermission("buildffa.admin")) {
		    	if(args[0].equalsIgnoreCase("setspawn"))
		    	{
		    		if(Config.setSpawn(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]))) {
		    			p.sendMessage(Config.PluginPrefix+" §eSpawn geändert.");
			    		return true;
		    		}
		    	}
		    	
			}
		}
		else {
			p.sendMessage(Config.PluginPrefix+" §cSchreibe einfach \"/buildffa help\".");
			return true;
		}
		return false;
	}

}
