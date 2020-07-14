package de.snocember.buildffa.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsSystem {
	public static Map<UUID, int[]> deathMap = new HashMap<UUID, int[]>();
	// deathMap.get(uuid), [0] Stats all, [1] Stats 30 days, [2] Stats 1 day
	public static Map<UUID, int[]> killMap = new HashMap<UUID, int[]>();
	// killMap.get(uuid), [0] Stats all, [1] Stats 30 days, [2] Stats 1 day
	public static Map<UUID, int[]> killStreakMap = new HashMap<UUID, int[]>();
	// killStreakMap.get(uuid), [0] killStreak, [1] killStreakRecord
	
	public static void loadProfile(UUID uuid) {
		// TODO load deaths
		// TODO load kills
		// TODO load killStreak and Record
		deathMap.put(uuid, new int[] {0, 0, 0});
		killMap.put(uuid, new int[] {0, 0, 0});
		killStreakMap.put(uuid, new int[] {0, 0});
		
	}
	
	public static boolean unloadProfile(UUID uuid) {
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
	
	public static boolean saveProfile(UUID uuid) {
		// TODO save deaths
		// TODO save kills
		// TODO save killStreakRecord
		return true;
	}
	
	public static int[] getDeaths(UUID uuid) {
		int[] deaths = deathMap.get(uuid);
		return deaths;
	}
	
	public static int[] getKills(UUID uuid) {
		int[] kills = killMap.get(uuid);
		return kills;
	}
	
	public static int[] getKillStreak(UUID uuid) {
		int[] killStreak = killStreakMap.get(uuid);
		return killStreak;
	}
	
	public static boolean addDeath(UUID uuid) {
		int[] deaths = getDeaths(uuid);
		deathMap.put(uuid, new int[] {deaths[0]+1, deaths[1]+1, deaths[2]+1});
		int killStreakRecord = getKillStreak(uuid)[1];
		killStreakMap.put(uuid, new int[] {0, killStreakRecord});
		return true;
	}
	
	public static boolean addKill(UUID uuid) {
		int[] kills = getKills(uuid);
		killMap.put(uuid, new int[] {kills[0]+1, kills[1]+1, kills[2]+1});
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
}
