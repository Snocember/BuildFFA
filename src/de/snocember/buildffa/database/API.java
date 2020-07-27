// (c) Snocember(#8770 auf Discord), 2019
package de.snocember.buildffa.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.snocember.buildffa.Main;

@SuppressWarnings("deprecation")
public class API {
	public void main(){
		// NOTHING
	}
	public static boolean initConnection() {
		DBController.initDBConnection();
		return true;
	}
	public static boolean CheckIfInDB(String pid) {
		boolean status = false;
		try {
			Statement stmt = DBController.connection.createStatement();
			System.out.println("[BuildFFA] DEBUG: (API.CheckIfInDB) SELECT statsAllKills FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+pid+"';");
			ResultSet rs1 = stmt.executeQuery("SELECT statsAllKills FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+pid+"';");
			rs1.next();
			// falls kein Fehler auftritt, ist status = true.	
			@SuppressWarnings("unused")
			String test = rs1.getString("statsAllKills");
			if (Main.DebugOn.equals(1)) { System.out.println("[BuildFFA] (database.API) Kein Fehler bei Check ob Spieler in DB ist."); }
			status = true;
		} catch (SQLException e) {
			if (Main.DebugOn.equals(1)) { 
				if (Main.DebugOn.equals(1)) {System.err.println("[BuildFFA] (database.API) Spieler nicht in DB.");}
				if (Main.DebugOn.equals(1)) {e.printStackTrace();}
				status = false;
			}
		}
		return status;
		// TODO vielleicht return String?
	}
	public static Integer[] GetStatsAll(String playername) {
		Integer[] answer = null;
		Integer kills = null;
		Integer deaths = null;
		OfflinePlayer theplayer = Bukkit.getOfflinePlayer(playername);
		String thepid = String.valueOf(theplayer.getUniqueId());
		if(CheckIfInDB(thepid)) {
			try {
				Statement stmt1 = DBController.connection.createStatement();
				System.out.println("[BuildFFA] DEBUG: (API:GetStatsAll): SELECT statsAllKills, statsAllDeaths FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+thepid+"';");
				ResultSet rs1 = stmt1.executeQuery("SELECT statsAllKills, statsAllDeaths FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+thepid+"';");
				rs1.next();
				kills = rs1.getInt("statsAllKills");
				deaths = rs1.getInt("statsAllDeaths");
				System.out.println("[BuildFFA] DEBUG: (API:GetStatsAll): "+kills +" "+ deaths);
				rs1.close();
				stmt1.close();
				answer = new Integer[] {kills, deaths};
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("[BuildFFA] DEBUG: (API:GetStatsAll): SQLException");
				answer = new Integer[] {kills, deaths};
			}
		}
		return answer;
		
	}
	public static boolean SetStatsAll(String player, int kills, int deaths) {
		boolean status = false;
		OfflinePlayer theplayer = Bukkit.getOfflinePlayer(player);
		String thepid = String.valueOf(theplayer.getUniqueId());
		if(CheckIfInDB(thepid)) {
			try {
		    	Integer thekills = Integer.valueOf(kills);
		    	Integer thedeaths = Integer.valueOf(deaths);
		    	if (Main.DebugOn.equals(1)) { System.out.println("[Coins] DEBUG: "+String.valueOf(thepid)); }
		        DBController.q_action.add("SET");
		        DBController.q_pid.add(thepid);
		        DBController.q_kills.add(thekills);
		        DBController.q_deaths.add(thedeaths);
		        status = true;
		    }
		    catch (IndexOutOfBoundsException e1) {
		    	status = false;
		    }
		    catch (NumberFormatException e2) {
		    	status = false;
		    }	
		}
		return status;
		
	}
	public static boolean UpdateStatsAll(String player, String kills, String deaths) {
		boolean status = false;
		OfflinePlayer theplayer = Bukkit.getOfflinePlayer(player);
		String thepid = String.valueOf(theplayer.getUniqueId());
		if(CheckIfInDB(thepid)) {
			try {
				Integer thekills = Integer.valueOf(kills);
		    	Integer thedeaths = Integer.valueOf(deaths);
				if (Main.DebugOn.equals(1)) { System.out.println("[BuildFFA] DEBUG: "+String.valueOf(thepid)); }
				DBController.q_action.add("UPDATE");
				DBController.q_pid.add(thepid);
		        DBController.q_kills.add(thekills);
		        DBController.q_deaths.add(thedeaths);
				status = true;
			}
			catch (IndexOutOfBoundsException e) {
				status = false;
			}
			catch (NumberFormatException e2) {
				status = false;
			}
		}
		return status;
		
	}
}
