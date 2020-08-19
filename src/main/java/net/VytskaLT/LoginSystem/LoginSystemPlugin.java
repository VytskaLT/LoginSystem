package net.VytskaLT.LoginSystem;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class LoginSystemPlugin extends JavaPlugin {

    @Getter
    private final List<Player> loggedIn = new ArrayList<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(getPassword(p) == null ? getMessage("register") : getMessage("login")));


    }

    public void setPassword(Player player, String password) {
        getConfig().set("accounts." + player.getName(), password);
        saveConfig();
    }

    public String getPassword(Player player) {
        return getConfig().getString("accounts." + player.getName());
    }

    public String getMessage(String message) {
        String msg = getConfig().getString("messages." + message);
        return msg == null ? null : ChatColor.translateAlternateColorCodes('&', msg);
    }
}
