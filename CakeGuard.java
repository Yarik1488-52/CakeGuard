package me.yarik.cakeguard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

public class CakeGuard extends JavaPlugin implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage().toLowerCase();
        UUID uuid = p.getUniqueId();

        if (p.getName().equalsIgnoreCase("Yarikznaeprava")) return;

        // Кулдаун 3 секунди
        if (cooldowns.containsKey(uuid)) {
            long timeLeft = ((cooldowns.get(uuid) / 1000) + 3) - (System.currentTimeMillis() / 1000);
            if (timeLeft > 0) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Зачекайте ще " + timeLeft + " сек.");
                return;
            }
        }
        cooldowns.put(uuid, System.currentTimeMillis());

        // Тільки жорсткі образи рідних та адмінів
        if (msg.matches(".*(мать|маму|батя|отчим|mamy|mamu|mq|админ лох|сервер говно|мать ебал).*")) {
            e.setCancelled(true);
            String n = p.getName();
            
            Bukkit.getScheduler().runTask(this, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempmute " + n + " 12h 3.1");
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPUNISH &8» &fГравець &e" + n + " &fзамучений за образу рідних."));
            });
        }
    }
}
