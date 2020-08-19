package net.VytskaLT.LoginSystem;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class LoginListener implements Listener {

    private final LoginSystemPlugin plugin;

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(plugin.getMessage("welcome"));
        plugin.login(p);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        LoginPlayer loginPlayer = plugin.getPlayer(e.getPlayer());
        if (loginPlayer != null) {
            loginPlayer.reset();
            plugin.getLoggedIn().remove(loginPlayer);
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        LoginPlayer loginPlayer = plugin.getPlayer(p);
        if (loginPlayer == null)
            return;
        String pass = plugin.getPassword(p);
        if (pass == null) {
            if (e.getMessage().contains(" ")) {
                p.sendMessage(plugin.getMessage("cannot-have-spaces"));
                return;
            }
            plugin.setPassword(p, e.getMessage());
            p.sendMessage(plugin.getMessage("register-success"));
        } else {
            if (!pass.equals(e.getMessage())) {
                p.sendMessage(plugin.getMessage("incorrect-password"));
                return;
            }
            p.sendMessage(plugin.getMessage("login-success"));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                loginPlayer.reset();
            }
        }.runTask(plugin);
        plugin.getLoggedIn().remove(loginPlayer);
    }
}
