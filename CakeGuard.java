package me.yarik.cakeguard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.regex.Pattern;

public class CakeGuard extends JavaPlugin implements Listener {

    private final Pattern filter = Pattern.compile(".*(м[.|_]*а[.|_]*т[.|_]*ь|м[.|_]*а[.|_]*м|б[.|_]*а[.|_]*т[.|_]*я|о[.|_]*т[.|_]*ч[.|_]*и[.|_]*м|m[.|_]*q|к[.|_]*у).*");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p.getName().equalsIgnoreCase("Yarikznaeprava")) return;

        String msg = e.getMessage().toLowerCase().replaceAll("\\s+", "");

        if (filter.matcher(msg).matches()) {
            e.setCancelled(true);
            String n = p.getName();
            Bukkit.getScheduler().runTask(this, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempmute " + n + " 12h 3.1 (Образа рідних)");
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPUNISH &8» &fГравець &e" + n + " &fзамучений за образу рідних!"));
            });
        }
    }
}
