package net.VytskaLT.LoginSystem;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

@RequiredArgsConstructor
public class InteractListener implements Listener {

    private final LoginSystemPlugin plugin;

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (!plugin.getLoggedIn().contains(e.getPlayer()))
            e.setTo(e.getFrom());
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        check(e.getPlayer(), e);
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        check(e.getWhoClicked(), e);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        check(e.getPlayer(), e);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        check(e.getPlayer(), e);
    }

    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
        check(e.getPlayer(), e);
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {
        check(e.getDamager(), e);
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        check(e.getEntity(), e);
    }

    private void check(Entity entity, Cancellable event) {
        if (entity instanceof Player && !plugin.getLoggedIn().contains(entity))
            event.setCancelled(true);
    }
}
