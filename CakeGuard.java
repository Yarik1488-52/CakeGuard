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
    private final HashMap<UUID, Long> cd = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        String m = e.getMessage().toLowerCase();

        if (p.getName().equalsIgnoreCase("Yarikznaeprava")) return;

        if (cd.containsKey(u) && (System.currentTimeMillis() - cd.get(u)) < 3000) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "Зачекайте 3 сек!");
            return;
        }
        cd.put(u, System.currentTimeMillis());

        if (m.matches(".*(мать|маму|батя|mq|админ лох|сервер говно).*")) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTask(this, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempmute " + p.getName() + " 12h 3.1");
                Bukkit.broadcastMessage(ChatColor.RED + "Гравець " + p.getName() + " замучений за образи!");
            });
        }
    }
}
