// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.snocember.buildffa.Main;

public class ConfigDB {
	
	private static File file;
	public static FileConfiguration cfg;
	
	public static String  Type;
	public static String  Host;
	public static Integer Port;
	public static String  DatabaseName;
	public static String  Username;
	public static String  Password;
	public static String  StatsTableName;
	
	@SuppressWarnings("unused")
	private Main plugin;

	public ConfigDB(Main plugin) {
		this.plugin = plugin;
		file = new File("plugins/BuildFFA","database.yml");
		cfg = YamlConfiguration.loadConfiguration(file);
		loadConfiguration();
		
		Type = 	cfg.getString		("Database.Type");
		Host = 	cfg.getString		("Database.Host");
		Port = 	cfg.getInt			("Database.Port");
		if (Main.DebugOn == true) { System.out.println("[BuildFFA] DEBUG: Port: "+Port); }
		DatabaseName = cfg.getString("Database.DatabaseName");
		Username = cfg.getString	("Database.Username");
		Password = cfg.getString	("Database.Password");
		StatsTableName = cfg.getString("Database.Stats.TableName");
  
		System.out.println("[BuildFFA] ConfigDB loaded.");
	}
	
	public static void loadConfiguration() {
		String path0 = "Database.Type_Comment";
	    cfg.addDefault(path0, "mysql or sqlite (sqlite, if there is no mysql server)");
	    String path1 = "Database.Type";
	    cfg.addDefault(path1, "mysql");
	    String path2 = "Database.Host";
	    cfg.addDefault(path2, "localhost");
	    String path3 = "Database.Port";
	    cfg.addDefault(path3, "3306");
	    String path4 = "Database.DatabaseName";
	    cfg.addDefault(path4, "BuildFFA");
	    String path5 = "Database.Username_Password_Comment";
	    cfg.addDefault(path5, "Is not necessary if the type is 'sqlite'.");
	    String path6 = "Database.Username";
	    cfg.addDefault(path6, "root");
	    String path7 = "Database.Password";
	    cfg.addDefault(path7, "");
	    String path8 = "Database.Stats.TableName";
	    cfg.addDefault(path8, "Stats");
	    
	    cfg.options().copyDefaults(true);
	    try {
			cfg.save(file);
		} catch (IOException e) { 
			System.err.print("[BuildFFA] DBConfig file couldn't be loaded.");
		}
	}
}