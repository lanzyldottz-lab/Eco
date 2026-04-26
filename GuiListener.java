package me.ecocontrol;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getView().getTitle().equals("EcoControl Menu")) {

            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;

            String name = e.getCurrentItem().getItemMeta().getDisplayName();

            if (name.equals("Toggle Overworld")) toggle("overworld", player);
            if (name.equals("Toggle Nether")) toggle("nether", player);
            if (name.equals("Toggle End")) toggle("end", player);

            if (name.equals("Player Teleport Menu")) {

                Inventory playerMenu = Bukkit.createInventory(null, 54, "Teleport Players");

                for (Player online : Bukkit.getOnlinePlayers()) {
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    ItemMeta meta = head.getItemMeta();
                    meta.setDisplayName(online.getName());
                    head.setItemMeta(meta);

                    playerMenu.addItem(head);
                }

                player.openInventory(playerMenu);
            }
        }

        // PLAYER TELEPORT GUI
        if (e.getView().getTitle().equals("Teleport Players")) {

            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;

            String targetName = e.getCurrentItem().getItemMeta().getDisplayName();
            Player target = Bukkit.getPlayer(targetName);

            if (target != null) {
                player.teleport(target);
                player.sendMessage("§a[EcoControl] Teleported to §e" + target.getName());
            }
        }
    }

    private void toggle(String world, Player player) {
        boolean state = Main.getInstance().getConfig().getBoolean("worlds." + world);

        Main.getInstance().getConfig().set("worlds." + world, !state);
        Main.getInstance().saveConfig();

        player.sendMessage("§a[EcoControl] §e" + world + " §7is now " +
                (!state ? "§aENABLED" : "§cDISABLED"));
    }

    // BLOCK ALL TELEPORTS (RTP INCLUDED)
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {

        World world = e.getTo().getWorld();
        String name = world.getName();

        if (name.equals("world") && !Main.getInstance().getConfig().getBoolean("worlds.overworld")) {
            block(e.getPlayer(), e, "Overworld");
        }

        if (name.equals("world_nether") && !Main.getInstance().getConfig().getBoolean("worlds.nether")) {
            block(e.getPlayer(), e, "Nether");
        }

        if (name.equals("world_the_end") && !Main.getInstance().getConfig().getBoolean("worlds.end")) {
            block(e.getPlayer(), e, "End");
        }
    }

    // BLOCK PORTALS
    @EventHandler
    public void onPortal(PlayerPortalEvent e) {

        World world = e.getTo().getWorld();
        String name = world.getName();

        if (name.equals("world_nether") && !Main.getInstance().getConfig().getBoolean("worlds.nether")) {
            block(e.getPlayer(), e, "Nether");
        }

        if (name.equals("world_the_end") && !Main.getInstance().getConfig().getBoolean("worlds.end")) {
            block(e.getPlayer(), e, "End");
        }
    }

    private void block(Player player, Cancellable e, String world) {
        e.setCancelled(true);
        player.sendMessage("§c[EcoControl] Access to §e" + world + " §cis currently disabled.");
    }
}