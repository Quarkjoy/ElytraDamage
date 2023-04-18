package me.quarkjoy.elytradamage;

import me.quarkjoy.elytradamage.listeners.PlayerJoinGlidingListener;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ElytraDamage extends JavaPlugin implements Listener {

    private static ElytraDamage plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //System.out.println("ElytraDamage started - Quarkjoy");
        plugin = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ElytraStartGlideListener(), this);
        getServer().getPluginManager().registerEvents(new ElytraEndGlideListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinGlidingListener(), this);

    }

    /**
     * Listens for a player to begin flying and prints message.
     */
    public class ElytraStartGlideListener implements Listener {

        @EventHandler
        public void onBeginGlide(EntityToggleGlideEvent event) {
            Player player = (Player) event.getEntity();
            if (!player.isGliding()) {
                //System.out.println(player.getName() + ": just started flying.");
                damageElytra(player);
            }
        }
    }

    /**
     * When activated prints message every 20t.
     */
    public void damageElytra(Player player) {

        //System.out.println(player.getName() + ": flyingTask activated.");
        new BukkitRunnable() {
            int counter = 0;
            int emergencyCounter = 0;
            @Override
            public void run() {
                //System.out.println("Player is currently flying. 1 second has passed.");
                if (emergencyCounter == 1728) {
                    System.out.println(player.getName() +"'s Elytra is no longer taking damage to prevent lag. Why did they time out?");
                }
                final ItemStack elytra = player.getInventory().getChestplate();
                assert elytra != null;
                final Damageable elytraDamageable = (Damageable) elytra.getItemMeta();
                if ((
                        player.getInventory().getChestplate() != null
                        && player.isOnline()
                        && player.isGliding()
                        && (elytraDamageable.getDamage() != 432
                        && player.getGameMode() == GameMode.SURVIVAL
                        && emergencyCounter <= 1728))) {
                    int level = elytra.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
                    if (!elytra.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
                        //System.out.println(counter);
                            //System.out.println("No umbreaking");
                            int damage = elytraDamageable.getDamage() + 1;
                            elytraDamageable.setDamage((short) damage);
                            elytra.setItemMeta(elytraDamageable);
                        //System.out.println("counter increased");
                    }
                    if (level == 1) {
                        //System.out.println(counter);
                        if (counter == 2) {
                           //System.out.println("Unbreaking 1");
                            int damage = elytraDamageable.getDamage() + 1;
                            elytraDamageable.setDamage((short) damage);
                            elytra.setItemMeta(elytraDamageable);
                            if (counter == 2) {
                                counter = 0;
                            }
                        }
                        counter++;
                        //System.out.println("counter increased");
                    }
                    if (level == 2) {
                        //System.out.println(counter);
                        if (counter == 3) {
                            //System.out.println("Unbreaking 2");
                            int damage = elytraDamageable.getDamage() + 1;
                            elytraDamageable.setDamage((short) damage);
                            elytra.setItemMeta(elytraDamageable);
                            if (counter == 3) {
                                counter = 0;
                            }
                        }
                        counter++;
                        //System.out.println("counter increased");
                    }
                    if (level == 3) {
                        //System.out.println(counter);
                        if (counter == 5) {
                            //System.out.println("Unbreaking 3");
                            int damage = elytraDamageable.getDamage() + 1;
                            elytraDamageable.setDamage((short) damage);
                            elytra.setItemMeta(elytraDamageable);
                            if (counter == 5) {
                                counter = 0;
                            }
                        }
                        counter++;
                        //System.out.println("counter increased");
                    }
                    emergencyCounter++;
                    //System.out.println(emergencyCounter);
                    //System.out.println(player.getName() + ": elytra detected, not null, and damaged.");
                }
                else {
                    //System.out.println(player.getName() + ": elytra not detected.");
                    emergencyCounter = 0;
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(ElytraDamage.getPlugin(), 0, 20);
    }

    /**
     * Listens for player ending glide and cancels flyingTask
     */
    public class ElytraEndGlideListener implements Listener {

        @EventHandler
        public void onEndGlide(EntityToggleGlideEvent event) {
            Player player = (Player) event.getEntity();
            if (player.isGliding()) {
                //System.out.println(player.getName() + " just stopped flying.");
                //flyingTask.cancel();
            }
        }
    }

    @EventHandler
    public void onPlayerJoinGliding(PlayerJoinEvent event) {
        Player player = (Player) event.getPlayer();
        if (!player.isGliding()) {
            //System.out.println(player.getName() + " has joined and they are not gliding.");
        }
        else if (player.isGliding()){
            //System.out.println(player.getName() + " has joined and they are gliding.");
            damageElytra(player);
        }
    }

    public static ElytraDamage getPlugin() {
        return plugin;
    }
}
