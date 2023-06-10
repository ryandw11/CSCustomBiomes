package com.ryandw11.cscustombiomes.commands;

import com.ryandw11.cscustombiomes.BiomeBaser;
import com.ryandw11.cscustombiomes.CSCustomBiomes;
import com.ryandw11.cscustombiomes.MinecraftVersion;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InspectBiomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "This command can only be executed by players!");
            return false;
        }

        if (!player.hasPermission("cscustombiomes.inspector")) return false;

        Block block = player.getLocation().getBlock();
        BiomeBaser biomeBaser = CSCustomBiomes.getInstance().getBiomeBaserForBlock(block);

        player.sendMessage(String.format("%s=================[%sBiome Inspector%s]=================",
                ChatColor.AQUA, ChatColor.GREEN, ChatColor.AQUA));
        player.sendMessage(String.format("%sBiome Namespace: %s:%s",
                ChatColor.AQUA,
                ChatColor.GREEN + biomeBaser.getNamespace(),
                biomeBaser.getPath()));
        player.sendMessage(String.format("%sBiome Temp: %s%.2f",
                ChatColor.AQUA,
                ChatColor.GREEN,
                biomeBaser.getBaseTemp()));
        player.sendMessage(String.format("%sBiome DownFall: %s%.2f",
                ChatColor.AQUA,
                ChatColor.GREEN,
                biomeBaser.getDownfall()));

        if (CSCustomBiomes.getInstance().getMinecraftVersion() == MinecraftVersion.v1_18_2) {
            player.sendMessage(String.format("%sBiome Category: %s%s",
                    ChatColor.AQUA,
                    ChatColor.GREEN,
                    biomeBaser.getCategory()));
        } else {
            player.sendMessage(String.format("%sBiome Category: %sNot support on this version of Minecraft.",
                    ChatColor.AQUA,
                    ChatColor.GREEN));
        }

        return false;
    }

}
