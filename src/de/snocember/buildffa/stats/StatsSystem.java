// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.snocember.buildffa.Main;
import de.snocember.buildffa.database.API;

@SuppressWarnings("unused")
public class StatsSystem {
	public static Map<UUID, int[]> deathMap = new HashMap<UUID, int[]>();
	// deathMap.get(uuid), [0] Stats all, [1] Stats 30 days, [2] Stats 1 day
	public static Map<UUID, int[]> killMap = new HashMap<UUID, int[]>();
	// killMap.get(uuid), [0] Stats all, [1] Stats 30 days, [2] Stats 1 day
	public static Map<UUID, int[]> killStreakMap = new HashMap<UUID, int[]>();
	// killStreakMap.get(uuid), [0] killStreak, [1] killStreakRecord
	
	public static void LoadProfile(UUID uuid) {
		try {
			Integer[] stats = API.GetStatsAll(Bukkit.getPlayer(uuid).getName());
			if (Main.DebugOn == true) { System.out.println("[BuildFFA] DEBUG: (StatsSystem:LoadProfile): "+stats[0]+" "+stats[1]); }
			// TODO load killStreak and Record
			killMap.put(uuid, new int[] {stats[0], 0, 0});
			deathMap.put(uuid, new int[] {stats[1], 0, 0});
			killStreakMap.put(uuid, new int[] {0, 0});
			// TODO falls keine Connection -> Ersatzwerte, da sonst unendlich schleife
		}
		catch (NullPointerException e) {
			if (Main.DebugOn == true) { System.err.println("[BuildFFA] Fehler beim Laden des Stats-Profils."); }
		}
		
	}
	
	public static boolean UnloadProfile(UUID uuid) {
		try {
			if(saveProfile(uuid)) {
				deathMap.remove(uuid);
				killMap.remove(uuid);
				killStreakMap.remove(uuid);
				return true;
			}
			else {
				return false;
			}
		}
		catch (NullPointerException e) {
			return false;
		}
	}
	
	public static boolean saveProfile(UUID uuid) {
		try {
			API.SetStatsAll(String.valueOf(uuid), killMap.get(uuid)[0], deathMap.get(uuid)[0]);
			// TODO save killStreakRecord
			return true;
		}
		catch (NullPointerException e) {
			return false;
		}
	}
	
	public static int[] getDeaths(UUID uuid) {
		try {
			int test = deathMap.get(uuid)[0];
			int[] deaths = deathMap.get(uuid);
			return deaths;
		}
		catch (NullPointerException e) {
			LoadProfile(uuid);
			return deathMap.get(uuid);
		}
	}
	
	public static int[] getKills(UUID uuid) {
		try {
			int test = killMap.get(uuid)[0];
			int[] kills = killMap.get(uuid);
			return kills;
		}
		catch (NullPointerException e) {
			LoadProfile(uuid);
			return killMap.get(uuid);
		}
	}
	
	public static int[] getKillStreak(UUID uuid) {
		try {
			int test = killStreakMap.get(uuid)[0];
			int[] killStreak = killStreakMap.get(uuid);
			return killStreak;
		}
		catch (NullPointerException e) {
			LoadProfile(uuid);
			return killStreakMap.get(uuid);
		}
	}
	
	public static boolean addDeath(UUID uuid) {
		int[] deaths = new int[] {0, 0, 0};
		try {
			int test = deathMap.get(uuid)[0];
			deaths = getDeaths(uuid);
		}
		catch (NullPointerException e) {
			LoadProfile(uuid);
			deaths = getDeaths(uuid);
		}
		try {
			deathMap.put(uuid, new int[] {deaths[0]+1, deaths[1]+1, deaths[2]+1});
			API.SetStatsAll(Bukkit.getPlayer(uuid).getName(), killMap.get(uuid)[0], deathMap.get(uuid)[0]);
			int killStreakRecord = getKillStreak(uuid)[1];
			killStreakMap.put(uuid, new int[] {0, killStreakRecord});
			return true;
		}
		catch (NullPointerException e) {
			System.err.println("[BuildFFA] "+"\033[91m"+"StatsError | Der Death konnte nicht gespeichert werden. SQL-Verbindung?"+"\033[0m");
			return false;
			
		}
	}
	
	public static boolean addKill(UUID uuid) {
		int[] kills = new int[] {0, 0, 0};
		try {
			int test = killMap.get(uuid)[0];
			kills = getKills(uuid);
		}
		catch (NullPointerException e) {
			LoadProfile(uuid);
			kills = getKills(uuid);
		}
		try {
			killMap.put(uuid, new int[] {kills[0]+1, kills[1]+1, kills[2]+1});
			API.SetStatsAll(Bukkit.getPlayer(uuid).getName(), killMap.get(uuid)[0], deathMap.get(uuid)[0]);
			int killStreak = getKillStreak(uuid)[0];
			int killStreakRecord = getKillStreak(uuid)[1];
		
			if(killStreak+1 > killStreakRecord) {
				killStreakMap.put(uuid, new int[] {killStreak+1, killStreak+1});
			}
			else {
				killStreakMap.put(uuid, new int[] {killStreak+1, killStreakRecord});
			}
			return true;
		}
		catch (NullPointerException e) {
			System.err.println("[BuildFFA] "+"\033[91m"+"StatsError | Der Kill konnte nicht gespeichert werden. SQL-Verbindung?"+"\033[0m");
			return false;
		}
	}
}
