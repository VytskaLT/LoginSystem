package net.VytskaLT.LoginSystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LoginPlayer {

    private final LoginSystemPlugin plugin;
    @Getter
    private final Player player;
    @Getter
    private final GameMode gameMode;
    @Getter
    private final Location location;
    @Getter
    private final int fireTicks;

    public void reset() {
        player.teleport(location);
        player.setGameMode(gameMode);
        player.setNoDamageTicks(20*3);
        player.setFireTicks(fireTicks);
    }
}
