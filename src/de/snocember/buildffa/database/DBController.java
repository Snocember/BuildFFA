// (c) Snocember (#8770 auf Discord), 2019-20
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.database;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import de.snocember.buildffa.Main; 

/** <h2>DBController</h2>
 *  <p>Dies ist die Main MySQL Class für das BuildFFA Plugin.
 *  Es wird die Verbindung und zur SQL-Datenbank hergestellt.
 *  Dann werden in einer Schleife neue Datensätze hinzugefügt oder verändert.</p>
*/
public class DBController extends JavaPlugin {
	public static ArrayList<String> q_action = new ArrayList<>();
	public static ArrayList<String> q_pid = new ArrayList<>();
	public static ArrayList<Integer> q_kills = new ArrayList<>();
	public static ArrayList<Integer> q_deaths = new ArrayList<>();
	
	/** Die <i>Datenbank</i> <code>testdb.db</code> und der <i>Datenbanktreiber</i> werden geladen*/
	
	public static Connection connection; 

    //----#----#----#----#----#----#----#----#----#----#----#----#----#----
    /** Die Verbindung zur Datenbank wird hergestellt. Eine Shutdown-hook schließt später
     * die Verbindung.*/
    public static void initDBConnection() { 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (Main.DebugOn == true) { System.out.println("[BuildFFA] DEBUG: connection: jdbc:mysql://"+ ConfigDB.Host + ":" + ConfigDB.Port + "/" +
            		ConfigDB.DatabaseName +","+ ConfigDB.Username +","+ ConfigDB.Password); }
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://"+ ConfigDB.Host + ":" + ConfigDB.Port + "/" +
            		ConfigDB.DatabaseName, ConfigDB.Username, ConfigDB.Password);
            System.out.println("[BuildFFA] "+"\033[32m"+"Datenbank erfolgreich geöffnet."+"\033[0m");
        }	
    	catch (SQLException e) { 
    		System.err.println("[BuildFFA] "+"\033[91m"+"SQLException Errors | Verbindung zur Datenbank konnte nicht hergestellt werden."+"\033[0m");
    		if (Main.DebugOn == true) { e.printStackTrace(); }
    		Main.aliveService = false;
    		return;
        } catch (ClassNotFoundException e) {
        	System.err.println("[BuildFFA] "+"\033[91m"+"ClassNotFoundException Error"+"\033[0m");
        	Main.aliveService = false;
        	return;
		} 
    	
        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run() { 
                try { 
                    if (!connection.isClosed() && connection != null) { 
                        connection.close(); 
                        if (connection.isClosed()) 
                            System.out.println("[BuildFFA] "+"\033[32m"+"Verbindung zur Datenbank geschlossen."+"\033[0m"); 
                    } 
                } catch (SQLException e) { 
                	if (Main.DebugOn == true) { e.printStackTrace(); }
                } 
            } 
        });
        try {
        	Statement stmt = connection.createStatement();
        	@SuppressWarnings("unused")
			ResultSet rs1 = stmt.executeQuery("SELECT * FROM "+ConfigDB.StatsTableName);
        }
        catch (SQLException e1) {
        	System.err.println("[BuildFFA] "+"\033[91m"+"Fehler: Tabelle '"+ConfigDB.StatsTableName+"' nicht verfügbar. "+"\033[93m"+"Wird neu erstellt..."+"\033[0m");
        	//e1.printStackTrace();
        	try {
        	Statement stmt = connection.createStatement();
        	stmt.executeUpdate("CREATE TABLE "+ConfigDB.StatsTableName+" (playerid VARCHAR(200), statsAllKills INT(255), statsAllDeaths INT(255));"); // TODO 30days stats
        	System.err.println("[BuildFFA] "+"\033[32m"+"Tabelle '"+ConfigDB.StatsTableName+"' wurde neu erstellt."+"\033[0m");
        	}
        	catch (SQLException e2) {
        		System.err.println("[BuildFFA] "+"\033[91m"+"Fehler beim Erstellen fehlender Tabelle '"+ConfigDB.StatsTableName+"'."+"\033[0m"); 
        		if (Main.DebugOn == true) { e2.printStackTrace(); }
        	}
        }
    }
    //----#----#----#----#----#----#----#----#----#----#----#----#----#----
    /** Datensätze werden hinzugefügt oder verändert und die Verbindung geschlossen.</br>
     * Danach werden alle Datensätze der Tabelle ausgegeben.*/
    public static void handleDB() {
    	if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] q_action:"+q_action+", q_pid:"+q_pid+", q_kills:"+q_kills+", q_deaths:"+q_deaths);}
    	if (Main.aliveService) {
    		try {
        	Statement stmt = connection.createStatement(); 
             
            PreparedStatement psUpdate = connection
            		.prepareStatement("UPDATE "+ConfigDB.StatsTableName+" SET statsAllKills = ?, statsAllDeaths = ? WHERE playerid = ?;");  
            PreparedStatement psNew = connection
            		.prepareStatement("INSERT INTO "+ConfigDB.StatsTableName+" VALUES (?, ?, ?);");
 
            /** Schleifenblock, der alle Vorgänge ausführt.*/
            while (!q_pid.isEmpty()) {
            	if (Main.DebugOn.equals("1")) {System.out.println("[BuildFFA] DEBUG: Whoooo NEUE SCHLEIFE"); }
            	if (q_action.get(0).equalsIgnoreCase("NEW")){
            		psNew.setString(1, q_pid.get(0));
            		psNew.setInt(2, q_kills.get(0));
            		psNew.setInt(3, q_deaths.get(0));
            		psNew.addBatch();
            		
            		try {
            			removeIndex0();
            		} catch (IndexOutOfBoundsException e2){
            			System.err.println("[BuildFFA] FEHLER beim Löschen der Arraylisteinträge.");
            		}
            	}
            	else if (q_action.get(0).equalsIgnoreCase("UPDATE") ){
            		ResultSet rs1 = stmt.executeQuery("SELECT statsAllKills, statsAllDeaths FROM "+ConfigDB.StatsTableName+" WHERE playerid = '"+q_pid.get(0)+"';");
                    rs1.next();
                    if (Main.DebugOn == true) { System.out.println("[BuildFFA] Vorher: Kills:"+rs1.getString("statsAllKills")+", Deaths:"+rs1.getString("statsAllDeaths"));}
                	Integer resultKills = Integer.valueOf(rs1.getString("statsAllKills"));
                    Integer resultDeaths = Integer.valueOf(rs1.getString("statsAllDeaths"));
                    
                    Integer addKills = q_kills.get(0);
                    Integer addDeaths = q_deaths.get(0);
                    
                    psUpdate.setInt(1, resultKills+addKills);
                    psUpdate.setInt(2, resultDeaths+addDeaths);
                    psUpdate.setString(3, q_pid.get(0));
                    psUpdate.addBatch();
                    rs1.close();
                	
                    try {
                    	removeIndex0();
                    } catch (IndexOutOfBoundsException e2){
            			System.err.println("[BuildFFA] FEHLER beim Löschen der Arraylisteinträge.");
            		}
            	}
            	else if (q_action.get(0).equalsIgnoreCase("SET") ){	
                    psUpdate.setInt(1, q_kills.get(0));
                    psUpdate.setInt(2, q_deaths.get(0));
                    psUpdate.setString(3, q_pid.get(0));
                    psUpdate.addBatch();
                    if (Main.DebugOn == true) { System.out.println("[BuildFFA] Jetzt: Kills:"+q_kills.get(0)+", Deaths:"+q_deaths.get(0));}
                    try {
                    	removeIndex0();
                    } catch (IndexOutOfBoundsException e2){
            			System.err.println("[BuildFFA] FEHLER beim Löschen der Arraylisteinträge.");
            		}
            	}
            }
            
            connection.setAutoCommit(false); 
            psUpdate.executeBatch(); 
            psNew.executeBatch();
            connection.setAutoCommit(true); 
            stmt.close();
            psNew.close();
            psUpdate.close();
           
              
        } catch (SQLException e) { 
            System.err.println("[BuildFFA] "+"\033[91m"+"Couldn't handle DB-Query (SQLError)§r"+"\033[0m"); 
            if (Main.DebugOn == true) { e.printStackTrace(); }
            removeIndex0();
            System.err.println("[BuildFFA] "+"\033[91m"+"Letzter Auftrag wurde verworfen."+"\033[0m");
        }
        catch (Exception e2) { 
            System.err.println("[BuildFFA] "+"\033[91m"+"Couldn't handle DB-Query (Exception)"+"\033[0m"); 
            if (Main.DebugOn == true) { e2.printStackTrace(); }
            removeIndex0();
            System.err.println("[BuildFFA] "+"\033[91m"+"Letzter Auftrag wurde verworfen.§r"+"\033[0m");
        }
    }
    }
    public static void removeIndex0() {
    	q_action.remove(0);
    	q_pid.remove(0);
    	q_kills.remove(0);
    	q_deaths.remove(0);
    	
    }
} 