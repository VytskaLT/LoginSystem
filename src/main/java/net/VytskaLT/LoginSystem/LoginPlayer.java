package net.VytskaLT.LoginSystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LoginPlayer {

    @Getter
    private final Player player;
    @Getter
    private final GameMode gameMode;
    @Getter
    private final Location location;
    @Getter
    private final int fireTicks;
    @Getter
    private final boolean dead;

    public void reset() {
        player.teleport(dead ? player.getBedSpawnLocation() == null ? location.getWorld().getSpawnLocation() : player.getBedSpawnLocation() : location);
        player.setGameMode(gameMode);
        player.setNoDamageTicks(20*3);
        player.setFireTicks(fireTicks);
    }
}
