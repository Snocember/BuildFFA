// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Main;

public class GameCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GameCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("buildffa").setExecutor(this);
		System.out.println("[BuildFFA] Gamecommand geladen");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Nur Spieler sollten diesen Command ausfuehren!");
			return true;
		}
		Player p = (Player) sender;
		
		if (p.hasPermission("hello.use")) {
			p.sendMessage("§8[§dBuildFFA§8] yo");
			return true;
		} else {
			p.sendMessage("Du hast keine Berechtigungen, diesen Command auszufuehren!");
		}	
		return false;
	}
	
	
	

}
