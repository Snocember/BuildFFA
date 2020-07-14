// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.kits;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.Main;

public class KitsCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public KitsCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("kit").setExecutor(this);
		System.out.println("[BuildFFA] KitsCommand loaded.");	
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[BuildFFA] Nur Spieler können diesen Command ausführen.");
			return true;
		}
		Player p = (Player) sender;
		
		if ((cmd.getName().equalsIgnoreCase("kit")) && (args.length == 0)) {
			p.sendMessage(Config.PluginPrefix+" §eKitauswahl.");
			// TODO Kitauswahl
	    	return true;

	    }
		else if ((cmd.getName().equalsIgnoreCase("kit")) && (args.length != 0)) {
			// TODO Kitauswahl über Chat
		}
		return false;
	}
}