package me.ecocontrol;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        // RELOAD COMMAND
        if (cmd.getName().equalsIgnoreCase("reload")) {

            if (!player.hasPermission("ecocontrol.admin")) {
                player.sendMessage("§c[EcoControl] No permission!");
                return true;
            }

            Main.getInstance().reloadConfig();
            player.sendMessage("§a[EcoControl] Config reloaded!");
            return true;
        }

        // MAIN GUI
        if (cmd.getName().equalsIgnoreCase("ecogui")) {

            Inventory gui = Bukkit.createInventory(null, 27, "EcoControl Menu");

            ItemStack overworld = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta oMeta = overworld.getItemMeta();
            oMeta.setDisplayName("Toggle Overworld");
            overworld.setItemMeta(oMeta);

            ItemStack nether = new ItemStack(Material.NETHERRACK);
            ItemMeta nMeta = nether.getItemMeta();
            nMeta.setDisplayName("Toggle Nether");
            nether.setItemMeta(nMeta);

            ItemStack end = new ItemStack(Material.END_STONE);
            ItemMeta eMeta = end.getItemMeta();
            eMeta.setDisplayName("Toggle End");
            end.setItemMeta(eMeta);

            ItemStack players = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta pMeta = players.getItemMeta();
            pMeta.setDisplayName("Player Teleport Menu");
            players.setItemMeta(pMeta);

            gui.setItem(10, overworld);
            gui.setItem(13, nether);
            gui.setItem(16, end);
            gui.setItem(22, players);

            player.openInventory(gui);
        }

        return true;
    }
}