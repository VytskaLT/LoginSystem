package net.VytskaLT.LoginSystem;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class LoginListener implements Listener {

    private final LoginSystemPlugin plugin;

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(plugin.getMessage("welcome"));
        p.sendMessage(plugin.getPassword(p) == null ? plugin.getMessage("register") : plugin.getMessage("login"));
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(plugin.getLoggedIn().contains(p))
            return;
        String pass = plugin.getPassword(p);
        if(pass == null) {
            if(e.getMessage().contains(" ")) {
                p.sendMessage(plugin.getMessage("cannot-have-spaces"));
                return;
            }
            plugin.setPassword(p, e.getMessage());
            p.sendMessage(plugin.getMessage("register-success"));
        } else {
            if(!pass.equals(e.getMessage())) {
                p.sendMessage(plugin.getMessage("incorrect-password"));
                return;
            }
            p.sendMessage(plugin.getMessage("login-success"));
        }
        plugin.getLoggedIn().add(p);
    }
}
