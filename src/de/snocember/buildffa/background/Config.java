// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.background;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.snocember.buildffa.Main;

public class Config {
	
	private File file;
	public static FileConfiguration cfg;
	
	public static String PluginPrefix;
	
	public static Boolean ShowTitleWhenJoin;
	public static String TitleHeadline;
	public static String TitleCaption;
	
	public static Double SpawnCoordX;
	public static Double SpawnCoordY;
	public static Double SpawnCoordZ;
	
	public static Boolean GameruleKeepInventory;
	
	public static String DeathMsgBeforePlayerName;
	public static String DeathMsgAfterPlayerName;
	
	public static String KillDeathMsgBeforeKillerName;
	public static String KillDeathMsgBetweenNames;
	public static String KillDeathMsgAfterPlayerName;

	@SuppressWarnings("unused")
	private Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
		file = new File("plugins/BuildFFA","config.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		loadConfiguration();
		
		PluginPrefix = cfg.getString("BuildFFA.PluginPrefix").replaceAll("&", "§");
		
		ShowTitleWhenJoin = cfg.getBoolean("BuildFFA.Title.ShowTitleWhenJoin");
		TitleHeadline = cfg.getString("BuildFFA.Title.Headline").replaceAll("&", "§");
		TitleCaption = cfg.getString("BuildFFA.Title.Caption").replaceAll("&", "§");

		SpawnCoordX = cfg.getDouble("BuildFFA.Spawn.CoordX");
		SpawnCoordY = cfg.getDouble("BuildFFA.Spawn.CoordY");
		SpawnCoordZ = cfg.getDouble("BuildFFA.Spawn.CoordZ");
		
		GameruleKeepInventory = cfg.getBoolean("BuildFFA.KeepInventory");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doFireTick "+GameruleKeepInventory);
		
		DeathMsgBeforePlayerName = cfg.getString("BuildFFA.Death.MsgBeforePlayerName").replaceAll("&", "§");
		DeathMsgAfterPlayerName = cfg.getString("BuildFFA.Death.MsgAfterPlayerName").replaceAll("&", "§");
		
		KillDeathMsgBeforeKillerName = cfg.getString("BuildFFA.KillDeath.MsgBeforeKillerName").replaceAll("&", "§");
		KillDeathMsgBetweenNames = cfg.getString("BuildFFA.KillDeath.MsgBetweenNames").replaceAll("&", "§");
		KillDeathMsgAfterPlayerName = cfg.getString("BuildFFA.KillDeath.MsgAfterPlayerName").replaceAll("&", "§");
				
		System.out.println("[BuildFFA] Config geladen.");
	}

	public void loadConfiguration() {
		String path = "BuildFFA.PluginPrefix";
	    cfg.addDefault(path, "§8[§dBuildFFA§8]");
		String path0 = "BuildFFA.Title.ShowTitleWhenJoin";
	    cfg.addDefault(path0, true);
	    String path1 = "BuildFFA.Title.Headline";
	    cfg.addDefault(path1, "&dBuildFFA");
	    String path2 = "BuildFFA.Title.Caption";
	    cfg.addDefault(path2, "&edev.snocember.de");
	    
	    String path3 = "BuildFFA.Spawn.CoordX";
	    cfg.addDefault(path3, 0); //1.5
	    String path4 = "BuildFFA.Spawn.CoordY";
	    cfg.addDefault(path4, 60); //121.0
	    String path5 = "BuildFFA.Spawn.CoordZ";
	    cfg.addDefault(path5, 0); //0.5
	    
	    String path5a = "BuildFFA.GameruleKeepInventory";
	    cfg.addDefault(path5a, true);
	    
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
}