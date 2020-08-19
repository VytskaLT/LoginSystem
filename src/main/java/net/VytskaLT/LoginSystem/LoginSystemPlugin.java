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
    private final List<LoginPlayer> loggedIn = new ArrayList<>();
    @Getter
    private World loginWorld;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);

        loginWorld = new WorldCreator("login").type(WorldType.FLAT).generator(new EmptyWorldGenerator()).createWorld();
        loginWorld.setSpawnLocation(new Location(loginWorld, 0, 100, 0));

        Bukkit.getOnlinePlayers().forEach(this::login);
    }

    @Override
    public void onDisable() {
        loggedIn.forEach(LoginPlayer::reset);
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
        LoginPlayer loginPlayer = new LoginPlayer(this, player, player.getGameMode(), player.getLocation(), player.getFireTicks());
        player.teleport(loginWorld.getSpawnLocation());
        player.sendMessage(getPassword(player) == null ? getMessage("register") : getMessage("login"));
        loggedIn.add(loginPlayer);
    }

    public LoginPlayer getPlayer(Player player) {
        for (LoginPlayer loginPlayer : loggedIn)
            if (loginPlayer.getPlayer() == player)
                return loginPlayer;
        return null;
    }
}
