package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import gnu.trove.map.hash.THashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;

public class PlayerTriggerPressurePlate extends VTMap<Object, Object> implements AR {

	private VariableTriggers main;
	
	public PlayerTriggerPressurePlate(VariableTriggers i){
		main = i;
		makePath("./plugins/VariableTriggers/events/player", "PlayerTriggerPressurePlate.yml");
		load();
	}
	
	@EventHandler (ignoreCancelled = false)
	public void onClick(PlayerInteractEvent e){
		
		if (getList("Worlds").contains(e.getPlayer().getWorld().getName())){
			if (getLong("ActiveCooldown") <= System.currentTimeMillis()){
				if (getBool("Cancelled")){
					e.setCancelled(true);
				}
				if (getList("main").size() > 0 && e.getAction() == Action.PHYSICAL){
					new VTParser(main, "PlayerTriggerPressurePlate.yml", "main", getList("main"), e.getClickedBlock().getLocation(), getCustoms(e), e.getPlayer().getName()).start();
					cooldown();
				}
			}
		}
	}
	
	private THashMap<String, String> getCustoms(PlayerInteractEvent e){
		
		Vector loc = e.getClickedBlock().getLocation().toVector();
		THashMap<String, String> map = new THashMap<String, String>();
		map.put("<clicktype>", e.getAction().name());
		map.put("<blockid>", e.getClickedBlock().getType().getId() + "");
		map.put("<blockdata>", e.getClickedBlock().getData() + "");
		map.put("<blocktype>", e.getClickedBlock().getType().getId() + ":" + e.getClickedBlock().getData());
		map.put("<blockmaterial>", e.getClickedBlock().getType().name());
		map.put("<blocklocation>", e.getClickedBlock().getLocation().getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
		
		return map;
	}
	
	public void loadAll(){
		load();
	}
	
	public void saveAll(){
		save();
	}
}