// (c) Snocember (#8770 auf Discord), 2020
// dev.snocember.de | dev@snocember.de
package de.snocember.buildffa.foreground;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import de.snocember.buildffa.Main;

public class RemoveBlockQueue {
	
	public static ArrayList<Block> block_list = new ArrayList<>();
	public static ArrayList<Location> loc_list = new ArrayList<>();
	public static ArrayList<Double> delay_list = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private Main plugin;

	public RemoveBlockQueue(Main plugin) {
		this.plugin = plugin;
		startQueue(plugin);
	}	
	
	public static void addBlockQueue(Block block) {
		block_list.add(block);
		loc_list.add(block.getLocation());
		if(block.getType().equals(Material.SANDSTONE) ) {
			delay_list.add(6.0);
		}
		else {
			delay_list.add(10.0);
		}
	}

	public static void startQueue(Main plugin) {
		new BukkitRunnable() {
			
		    @SuppressWarnings("deprecation")
			public void run() {
		        Integer list_size = loc_list.size();
		        for(int i=0; i<list_size; i++) {
		        	Block block = block_list.get(i);
		        	Double delay = delay_list.get(i);
		        	
		        	delay_list.set(i, delay-0.5);
		        	delay = delay-0.5;
		        	
		        	if(delay.equals(4.0)) {
		        		if(block.getType().equals(Material.SANDSTONE)) {
		        			block.setTypeIdAndData(179, (byte) 2, true); //179 = red sandstone, 2 = smooth sandstone
		        		}
		        	}
		        	if(delay.equals(2.0)) {
		        		if(block.getType().equals(Material.SANDSTONE) || block.getType().equals(Material.RED_SANDSTONE)) {
		        			block.setTypeIdAndData(179, (byte) 0, true); //179 = red sandstone, 0 = normal
		        		}
		        	}
		        	if(delay.equals(0.0)) {
		        		block.setType(Material.AIR);
		        	}	        	
		        }	
		    }
		}.runTaskTimer(plugin, 0L, 10L); // 20L = 1 sek		
	}
}
