package me.nohbdyexe.lukesWhimsy.commands;

import me.nohbdyexe.lukesWhimsy.LukesWhimsy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class OnePunchCommand implements CommandExecutor, Listener {

    private String PLUGIN_PREFIX;
    private final LukesWhimsy plugin;

    public OnePunchCommand(LukesWhimsy plugin) {
        this.plugin = plugin;
        this.PLUGIN_PREFIX = plugin.getPluginPrefix();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("onepunchman")) {
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(PLUGIN_PREFIX+"You must be an operator to use this command!");
                return true;
            }

            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();
            boolean isEnabled = plugin.getOnePunchPlayers().getOrDefault(playerId, false);

            if (isEnabled) {
                // Disable one-punch mode
                plugin.getOnePunchPlayers().remove(playerId);
                player.sendMessage(PLUGIN_PREFIX+"One-punch mode disabled.");
            } else {
                // Enable one-punch mode
                plugin.getOnePunchPlayers().put(playerId, true);
                player.sendMessage(PLUGIN_PREFIX+"One-punch mode enabled. You are now invincible.");
            }

            return true;
        }
        return false;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            UUID playerId = player.getUniqueId();
            Entity damagedEntity = event.getEntity();
            if (plugin.getOnePunchPlayers().containsKey(playerId)) {
                if(!(damagedEntity instanceof Player)) {
                    // Deal damage equal to one-shot (this could be set to a high value)
                    Vector direction = damagedEntity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                    // If one-punch mode is enabled, set damage to a high value
                    event.setDamage(9999);
                    direction.multiply(5.0);
                    direction.setY(1.5);
                    damagedEntity.setVelocity(direction);
                }
            }
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerId = player.getUniqueId();
            if (plugin.getOnePunchPlayers().containsKey(playerId)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            UUID playerId = player.getUniqueId();
            if (plugin.getOnePunchPlayers().containsKey(playerId)) {
                // Cancel the event to prevent hunger loss
                event.setCancelled(true);
                player.setFoodLevel(20); // Ensure food level stays at maximum
                player.setSaturation(20); // Ensure saturation is also maxed
            }
        }
    }
}
