// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.snocember.buildffa.Config;
import de.snocember.buildffa.Main;
import de.snocember.buildffa.stats.StatsSystem;
import de.snocember.buildffa.database.ConfigDB;
import de.snocember.buildffa.database.DBController;

/** Beim Joinen wird eine Überschrift gezeigt und eine Nachricht gesendet
 * und das Inventar aktualisiert.*/
public class PlayerJoinLeave implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;

	public PlayerJoinLeave(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		System.out.println("[BuildFFA] PlayerJoinLeave loaded.");
	}
	/** Beim <i>PlayerJoinEvent</i> wird dem Spieler eine Überschrift mit dem Spielmodus gezeigt
	 * und eine Joinnachricht im Chat angezeigt. Das Inventar wird geleert.*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
	    if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] DEBUG: Joinevent!"); }
	    Player p = event.getPlayer();
	    event.setJoinMessage("");
	    p.setExp((float) 0);
	    p.getInventory().clear();
	    for(int i=0; i<6; i++) {
			p.sendMessage("");
		}
	    if(Config.Join_ShowTitleWhenJoin) {
	    	p.sendTitle(Config.Join_TitleHeadline, Config.Join_TitleCaption);
	    }
	    p.sendMessage(Config.PluginPrefix+" §cTeams sind auf diesem Server §4verboten§c!");
	    p.teleport(Config.wspawn);
	    
	    try {
		    if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] DEBUG: Test: ist DB-Eintrag vorhanden?");}
    		Statement stmt1 = DBController.connection.createStatement();
    		String thepid2 = String.valueOf(event.getPlayer().getUniqueId());
			ResultSet rs1 = stmt1.executeQuery("SELECT statsAllKills FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+thepid2+"';");
			rs1.next();
			@SuppressWarnings("unused")
			Integer test = rs1.getInt("statsAllKills");
			rs1.close();
			stmt1.close();
		} catch (SQLException e) {
			if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] DEBUG: hat keinen DB-Eintrag; Neuer wird gemacht.");}
			try {
				String thepid3 = String.valueOf(event.getPlayer().getUniqueId());
//				Statement stmt1 = DBController.connection.createStatement();
				PreparedStatement psNew = DBController.connection
	            		.prepareStatement("INSERT INTO "+ConfigDB.StatsTableName+" VALUES (?, ?, ?);");
				System.out.println("[BuildFFA] DEBUG: (PlayerJoinLeave.onPlayerJoinEvent:catchSQL): INSERT INTO "+ConfigDB.StatsTableName+" VALUES ('"+thepid3+"', 0, 0);");
//				stmt1.executeUpdate("INSERT INTO "+ConfigDB.StatsTableName+" VALUES ("+thepid3+", 0, 0);");
				psNew.setString(1, thepid3);
        		psNew.setInt(2, 0);
        		psNew.setInt(3, 0);
        		psNew.addBatch(); 
        		psNew.executeBatch();
				psNew.close();
				if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] "+event.getPlayer().getName()+" gejoined. Wurde der DB hinzugefügt.");}
			} catch (SQLException e1) {	
				if (Main.DebugOn.equals("1")) {
					System.out.println("[BuildFFA] Fehler beim hinzufügen des Spielers.");
					e1.printStackTrace(); 			
				}
			}
	        
		}
	    StatsSystem.LoadProfile(p.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event) {
		event.setQuitMessage("");
		StatsSystem.UnloadProfile(event.getPlayer().getUniqueId());
	}
}