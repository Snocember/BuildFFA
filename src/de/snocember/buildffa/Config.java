// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	private static File file;
	public static FileConfiguration cfg;
	
	public static String PluginPrefix;
	
	public static Boolean Join_ShowTitleWhenJoin;
	public static String Join_TitleHeadline;
	public static String Join_TitleCaption;
	
	public static Boolean Death_ShowTitleWhenJoin;
	public static String Death_TitleHeadline;
	public static String Death_TitleCaption;
	
	public static Boolean Kill_ShowTitleWhenJoin;
	public static String Kill_TitleHeadline;
	public static String Kill_TitleCaption;
	
	public static Boolean UnlimitedBlocks;	
	public static Double MaxBuildHeight;
	
	public static Boolean GameruleKeepInventory;
	
	public static String DeathMsgBeforePlayerName;
	public static String DeathMsgAfterPlayerName;
	
	public static String KillDeathMsgBeforeKillerName;
	public static String KillDeathMsgBetweenNames;
	public static String KillDeathMsgAfterPlayerName;
	
	public static String SrvVersionString;
	public static String SrvVersion;

	public static Double SpawnCoordX;
	public static Double SpawnCoordY;
	public static Double SpawnCoordZ;
	
	public static Integer WorldsNumber;
	public static Integer CurrentWorldsNumber;
	public static ArrayList<Object[]> worlds = new ArrayList<>(); //0=Name,1=Location,2=MaxBuildHeight
	
	public static Integer AutoMapChangePeriod;
	public static Integer AutoKitChangePeriod;
	
	public static World w;
	public static Location wspawn;
	
	@SuppressWarnings("unused")
	private Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
		file = new File("plugins/BuildFFA","config.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		loadConfiguration();
		
		PluginPrefix = cfg.getString("BuildFFA.PluginPrefix").replaceAll("&", "§");
		
		Join_ShowTitleWhenJoin = cfg.getBoolean("BuildFFA.Titles.JoinTitle.ShowTitleWhenJoin");
		Join_TitleHeadline = cfg.getString("BuildFFA.Titles.JoinTitle.Headline").replaceAll("&", "§");
		Join_TitleCaption = cfg.getString("BuildFFA.Titles.JoinTitle.Caption").replaceAll("&", "§");
		
		Death_ShowTitleWhenJoin = cfg.getBoolean("BuildFFA.Titles.DeathTitle.ShowTitleWhenDeath");
		Death_TitleHeadline = cfg.getString("BuildFFA.Titles.DeathTitle.Headline").replaceAll("&", "§");
		Death_TitleCaption = cfg.getString("BuildFFA.Titles.DeathTitle.Caption").replaceAll("&", "§");
		
		Kill_ShowTitleWhenJoin = cfg.getBoolean("BuildFFA.Titles.KillTitle.ShowTitleWhenKill");
		Kill_TitleHeadline = cfg.getString("BuildFFA.Titles.KillTitle.Headline").replaceAll("&", "§");
		Kill_TitleCaption = cfg.getString("BuildFFA.Titles.KillTitle.Caption").replaceAll("&", "§");
		
		UnlimitedBlocks = cfg.getBoolean("BuildFFA.UnlimitedBlocks");
		
		GameruleKeepInventory = cfg.getBoolean("BuildFFA.KeepInventory");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doFireTick "+GameruleKeepInventory);
		
		DeathMsgBeforePlayerName = cfg.getString("BuildFFA.Death.MsgBeforePlayerName").replaceAll("&", "§");
		DeathMsgAfterPlayerName = cfg.getString("BuildFFA.Death.MsgAfterPlayerName").replaceAll("&", "§");
		
		KillDeathMsgBeforeKillerName = cfg.getString("BuildFFA.KillDeath.MsgBeforeKillerName").replaceAll("&", "§");
		KillDeathMsgBetweenNames = cfg.getString("BuildFFA.KillDeath.MsgBetweenNames").replaceAll("&", "§");
		KillDeathMsgAfterPlayerName = cfg.getString("BuildFFA.KillDeath.MsgAfterPlayerName").replaceAll("&", "§");
		
		// TODO World Name
		
		SrvVersionString = Bukkit.getServer().getVersion();
		System.out.println("[BuildFFA] Server-Version: "+SrvVersionString);
		if(SrvVersionString.contains("Bukkit")) {
			SrvVersion = "Bukkit";
			System.err.print("[BuildFFA] WARN: You need Spigot (1.8.8) to have access to all functionalities of the plugin.");
		}
		if(SrvVersionString.contains("Spigot")) {
			SrvVersion = "Spigot";
		}
		
		WorldsNumber = cfg.getInt("BuildFFA.Worlds.Number");

		for(int i=0; i< WorldsNumber; i++) {
			String name = cfg.getString("BuildFFA.Worlds.world"+i+".Name");
			Double spawnCoordX = cfg.getDouble("BuildFFA.Worlds.world"+i+".SpawnCoordX");
			Double spawnCoordY = cfg.getDouble("BuildFFA.Worlds.world"+i+".SpawnCoordY");
			Double spawnCoordZ = cfg.getDouble("BuildFFA.Worlds.world"+i+".SpawnCoordZ");
			Integer maxBuildHeight = cfg.getInt("BuildFFA.Worlds.world"+i+".MaxBuildHeight");
			
			Bukkit.getServer().createWorld(new WorldCreator(name));
			System.out.println("[BuildFFA] DEBUG: Welt '"+name+"' geladen.");
			Location loc = new Location(w, spawnCoordX, spawnCoordY, spawnCoordZ);
			worlds.add(new Object[] {name, loc, maxBuildHeight});
		}
		System.out.println("[BuildFFA] DEBUG: worlds: "+worlds);
		
		CurrentWorldsNumber = 0;
		wspawn = (Location) worlds.get(CurrentWorldsNumber)[1];
		SpawnCoordX = Double.valueOf(wspawn.getX());
		SpawnCoordY = Double.valueOf(wspawn.getY());
		SpawnCoordZ = Double.valueOf(wspawn.getZ());
		MaxBuildHeight = Double.valueOf( (Integer) worlds.get(CurrentWorldsNumber)[2] );
		
		w = Bukkit.getServer().getWorld((String) worlds.get(CurrentWorldsNumber)[0]);
		wspawn = new Location(w, SpawnCoordX, SpawnCoordY, SpawnCoordZ);

	    AutoMapChangePeriod = cfg.getInt("BuildFFA.AutoMapChangePeriodInSec");
		AutoKitChangePeriod = cfg.getInt("BuildFFA.AutoKitChangePeriodInSec");
	    
		System.out.println("[BuildFFA] Config loaded.");
	}
	
	public static void loadConfiguration() {
		String path = "BuildFFA.PluginPrefix";
	    cfg.addDefault(path, "§8[§dBuildFFA§8]");
		String path0 = "BuildFFA.Titles.JoinTitle.ShowTitleWhenJoin";
	    cfg.addDefault(path0, true);
	    String path1 = "BuildFFA.Titles.JoinTitle.Headline";
	    cfg.addDefault(path1, "&dBuildFFA");
	    String path2 = "BuildFFA.Titles.JoinTitle.Caption";
	    cfg.addDefault(path2, "&edev.snocember.de");
	    
	    String path3b = "BuildFFA.Titles.DeathTitle.ShowTitleWhenDeath";
	    cfg.addDefault(path3b, true);
	    String path4b = "BuildFFA.Titles.DeathTitle.Headline";
	    cfg.addDefault(path4b, "");
	    String path5b = "BuildFFA.Titles.DeathTitle.Caption";
	    cfg.addDefault(path5b, "&c+ Death");
	    
	    String path3c = "BuildFFA.Titles.KillTitle.ShowTitleWhenKill";
	    cfg.addDefault(path3c, true);
	    String path4c = "BuildFFA.Titles.KillTitle.Headline";
	    cfg.addDefault(path4c, "");
	    String path5c = "BuildFFA.Titles.KillTitle.Caption";
	    cfg.addDefault(path5c, "&2+ Kill");
	    
	    String path3 = "BuildFFA.Spawn.CoordX";
	    cfg.addDefault(path3, 0); //1.5
	    String path4 = "BuildFFA.Spawn.CoordY";
	    cfg.addDefault(path4, 60); //121.0
	    String path5 = "BuildFFA.Spawn.CoordZ";
	    cfg.addDefault(path5, 0); //0.5
	    
	    String path5ac = "BuildFFA.UnlimitedBlocks";
	    cfg.addDefault(path5ac, false);
	    String path5aa = "BuildFFA.MaxBuildHeight";
	    cfg.addDefault(path5aa, 100);
	    
	    String path5ab = "BuildFFA.GameruleKeepInventory";
	    cfg.addDefault(path5ab, true);
	    
	    String path6 = "BuildFFA.Death.MsgBeforePlayerName";
	    cfg.addDefault(path6, "§8>> §7✝ §c");
	    String path7 = "BuildFFA.Death.MsgAfterPlayerName";
	    cfg.addDefault(path7, "");
	    
	    String path8 = "BuildFFA.KillDeath.MsgBeforeKillerName";
	    cfg.addDefault(path8, "§8>> §2");
	    String path9 = "BuildFFA.KillDeath.MsgBetweenNames";
	    cfg.addDefault(path9, " §7> ✝ §c");
	    String path10 = "BuildFFA.KillDeath.MsgAfterPlayerName";
	    cfg.addDefault(path10, "");
	    
	    String path11 = "BuildFFA.Stats.recordStats";
	    cfg.addDefault(path11, false);    

	    String path11a = "BuildFFA.AutoMapChangePeriodInSec";
	    cfg.addDefault(path11a, 0);
	    String path11b = "BuildFFA.AutoKitChangePeriodInSec";
	    cfg.addDefault(path11b, 0);
	    
	    String path12a = "BuildFFA.Worlds.Number";
	    cfg.addDefault(path12a, 1);
	    String path12b = "BuildFFA.Worlds.world0.Name";
	    cfg.addDefault(path12b, "world");
	    String path12c = "BuildFFA.Worlds.world0.SpawnCoordX";
	    cfg.addDefault(path12c, 0);
	    String path12d = "BuildFFA.Worlds.world0.SpawnCoordY";
	    cfg.addDefault(path12d, 60);
	    String path12e = "BuildFFA.Worlds.world0.SpawnCoordZ";
	    cfg.addDefault(path12e, 0);
	    String path12f = "BuildFFA.Worlds.world0.MaxBuildHeight";
	    cfg.addDefault(path12f, 50);
	    
	    cfg.options().copyDefaults(true);
	    try {
			cfg.save(file);
		} catch (IOException e) { }
	}
	public static boolean setSpawn(Double x, Double y, Double z) {
		cfg.set("BuildFFA.Spawn.CoordZ", x);
		cfg.set("BuildFFA.Spawn.CoordY", y);
		cfg.set("BuildFFA.Spawn.CoordZ", z);
		try {
			cfg.save(file);
			SpawnCoordX = x;
			SpawnCoordY = y;
			SpawnCoordZ = z;
			wspawn = new Location(w, SpawnCoordX, SpawnCoordY, SpawnCoordZ);
			return true;
		} 
		catch (IOException e) { 
			return false;
		}
	}
}