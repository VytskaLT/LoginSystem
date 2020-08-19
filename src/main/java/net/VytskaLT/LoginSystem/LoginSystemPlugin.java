package net.VytskaLT.LoginSystem;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSystemPlugin extends JavaPlugin {

    @Getter
    private final List<Player> loggedIn = new ArrayList<>();
    @Getter
    private final Map<Player, Location> locationMap = new HashMap<>();
    @Getter
    private World loginWorld;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(getPassword(p) == null ? getMessage("register") : getMessage("login")));

        loginWorld = new WorldCreator("login").type(WorldType.FLAT).generator(new EmptyWorldGenerator()).createWorld();
        loginWorld.setSpawnLocation(new Location(loginWorld, 0, 100, 0));
    }

    @Override
    public void onDisable() {
        locationMap.forEach(Entity::teleport);
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

    public void login(Player player) {

    }
}
