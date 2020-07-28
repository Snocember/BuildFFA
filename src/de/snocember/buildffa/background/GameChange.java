// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.foreground.PlayerMovement;
import de.snocember.buildffa.kits.ConfigKits;

public class GameChange {

	public static void changeWorld() {
		if (Config.CurrentWorldsNumber+1 != Config.WorldsNumber) {
			Config.CurrentWorldsNumber = Config.CurrentWorldsNumber +1;			
			
			Config.wspawn = (Location) Config.worlds.get(Config.CurrentWorldsNumber)[1];
			Config.SpawnCoordX = Double.valueOf(Config.wspawn.getX());
			Config.SpawnCoordY = Double.valueOf(Config.wspawn.getY());
			Config.SpawnCoordZ = Double.valueOf(Config.wspawn.getZ());
			Config.MaxBuildHeight = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[2] );
			Config.MaxBuildWidthX = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[3] );
			Config.MaxBuildWidthZ = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[4] );
			
			Config.w = Bukkit.getServer().getWorld((String) Config.worlds.get(Config.CurrentWorldsNumber)[0]);
			Config.wspawn = new Location(Config.w, Config.SpawnCoordX, Config.SpawnCoordY, Config.SpawnCoordZ);
			System.out.println("[BuildFFA] Map zu '"+Config.w.getName()+"'gewechselt.");
		}
		else if (Config.CurrentWorldsNumber+1 == Config.WorldsNumber) {
			Config.CurrentWorldsNumber = 0;
			
			Config.wspawn = (Location) Config.worlds.get(Config.CurrentWorldsNumber)[1];
			Config.SpawnCoordX = Double.valueOf(Config.wspawn.getX());
			Config.SpawnCoordY = Double.valueOf(Config.wspawn.getY());
			Config.SpawnCoordZ = Double.valueOf(Config.wspawn.getZ());
			Config.MaxBuildHeight = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[2] );
			Config.MaxBuildWidthX = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[3] );
			Config.MaxBuildWidthZ = Double.valueOf( (Integer) Config.worlds.get(Config.CurrentWorldsNumber)[4] );
			
			Config.w = Bukkit.getServer().getWorld((String) Config.worlds.get(Config.CurrentWorldsNumber)[0]);
			Config.wspawn = new Location(Config.w, Config.SpawnCoordX, Config.SpawnCoordY, Config.SpawnCoordZ);
			System.out.println("[BuildFFA] Map zu Anfangsmap gewechselt. ('"+Config.w.getName()+"')");
		}
		clearTeleportHeal();
	}
	
	public static void changeDefaultKit() {
		Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();

		if (ConfigKits.CurrentKitNumber+1 != ConfigKits.KitsNumber) {
			ConfigKits.CurrentKitNumber = ConfigKits.CurrentKitNumber +1;
			for(Player p : playerlist) {
				if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
					if(p.getLocation().getY() <= Config.SpawnCoordY -4.0) {
						PlayerMovement.addKit(p, ConfigKits.CurrentKitNumber);
					}
				}
			}
			System.out.println("[BuildFFA] Kit zu Kit Nr."+(ConfigKits.CurrentKitNumber+1)+" gewechselt.");
		}
		else if (ConfigKits.CurrentKitNumber+1 == ConfigKits.KitsNumber) {
			ConfigKits.CurrentKitNumber = 0;
			for(Player p : playerlist) {
				if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
					if(p.getLocation().getY() <= Config.SpawnCoordY -4.0) {
						PlayerMovement.addKit(p, ConfigKits.CurrentKitNumber);
					}
				}
			}
			System.out.println("[BuildFFA] Kit zu Kit Nr."+(ConfigKits.CurrentKitNumber+1)+" gewechselt.");
		}
	}
	public static void clear() {
		Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		for(Player p : playerlist) {
			if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
				p.getInventory().clear();
			}
		}
	}
	public static void clearTeleportHeal() {
		Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
		for(Player p : playerlist) {
			if(p.getGameMode() == GameMode.ADVENTURE | p.getGameMode() == GameMode.SURVIVAL) {
				p.getInventory().clear();
			}
			p.teleport(Config.wspawn);
			p.setHealth(20);
		}
	}
}
