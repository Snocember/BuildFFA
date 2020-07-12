// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.snocember.buildffa.Main;

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
	
	public static Double SpawnCoordX;
	public static Double SpawnCoordY;
	public static Double SpawnCoordZ;
	
	public static Double MaxBuildHeight;
	
	public static Boolean GameruleKeepInventory;
	
	public static String DeathMsgBeforePlayerName;
	public static String DeathMsgAfterPlayerName;
	
	public static String KillDeathMsgBeforeKillerName;
	public static String KillDeathMsgBetweenNames;
	public static String KillDeathMsgAfterPlayerName;
	
	public static String SrvVersionString;
	public static String SrvVersion;

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

		SpawnCoordX = cfg.getDouble("BuildFFA.Spawn.CoordX");
		SpawnCoordY = cfg.getDouble("BuildFFA.Spawn.CoordY");
		SpawnCoordZ = cfg.getDouble("BuildFFA.Spawn.CoordZ");
		
		MaxBuildHeight = cfg.getDouble("BuildFFA.MaxBuildHeight");
		
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
		
		w = Bukkit.getServer().getWorld("world");
		wspawn = new Location(w, SpawnCoordX, SpawnCoordY, SpawnCoordZ);
		
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