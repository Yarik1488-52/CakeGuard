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

        // Ігнор для тебе
        if (p.getName().equalsIgnoreCase("Yarikznaeprava")) return;

        // --- КУЛДАУН 3 СЕКУНДИ ---
        if (cooldowns.containsKey(uuid)) {
            long timeLeft = ((cooldowns.get(uuid) / 1000) + 3) - (System.currentTimeMillis() / 1000);
            if (timeLeft > 0) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Зачекайте ще " + timeLeft + " сек.");
                return;
            }
        }
        cooldowns.put(uuid, System.currentTimeMillis());

        // --- ПЕРЕВІРКА НА ОБРАЗИ (Рідні, Адмін, Проекти) ---
        // Тут тільки те, за що реально треба карати
        if (msg.matches(".*(мать|маму|батя|отчим|mamy|mamu|mq|админ лох|сервер говно|мать ебал).*")) {
            e.setCancelled(true);
            String n = p.getName();
            
            Bukkit.getScheduler().runTask(this, () -> {
                // Команда муту
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempmute " + n + " 12h 3.1 (Образа рідних/адміністрації)");
                
                // Оголошення в чат
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m---------------------------------------"));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', " &c&lPUNISH &8» &fГравець &e" + n + " &fзамучений!"));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', " &c&lPUNISH &8» &fПричина: &7Неадекватна поведінка (3.1)"));
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m---------------------------------------"));
            });
        }
    }
}
