package com.ryandw11.cscustombiomes;

import com.ryandw11.structure.api.structaddon.StructureSection;
import com.ryandw11.structure.structure.Structure;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CustomBiomeAddon_1_18_2 implements StructureSection {

    private String biomeNamespace;
    private String biomeKey;
    private String downFall;
    private String baseTempData;
    private String category;

    @Override
    public String getName() {
        return "CustomBiomes";
    }

    @Override
    public void setupSection(@Nullable ConfigurationSection configurationSection) {
        if (configurationSection == null) return;

        if (configurationSection.contains("BiomeKey")) {
            this.biomeNamespace = configurationSection.getString("BiomeKey.Namespace");
            this.biomeKey = configurationSection.getString("BiomeKey.Key");
        }
        if (configurationSection.contains("DownFall"))
            this.downFall = configurationSection.getString("DownFall");
        if (configurationSection.contains("BaseTemp"))
            this.baseTempData = configurationSection.getString("BaseTemp");
        if (configurationSection.contains("Category"))
            this.category = configurationSection.getString("Category");
    }

    @Override
    public boolean checkStructureConditions(Structure structure, Block block, Chunk chunk) {
        CraftBlock craftBlock = (CraftBlock) block;
        try {
            Field world = CraftBlock.class.getDeclaredField("world");
            world.setAccessible(true);
            ServerLevel worldServer = (ServerLevel) world.get(craftBlock);
            Biome biomeBase = worldServer.getBiome(craftBlock.getPosition()).value();

            DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();

            WritableRegistry<Biome> registry = (WritableRegistry<Biome>) dedicatedServer.registryAccess().ownedRegistry(Registry.BIOME_REGISTRY).get();

            ResourceLocation biomeKey = registry.getKey(biomeBase);

            // Check if the MinecraftKey matches if applicable.
            if (biomeNamespace != null && this.biomeKey != null && biomeKey != null) {
                if (!biomeNamespace.equalsIgnoreCase(biomeKey.getNamespace())) return false;
                if (!this.biomeKey.equalsIgnoreCase(biomeKey.getPath())) return false;
            }

            // Check if the biome category is valid.
            // The getBiomeCategory method is not public in 1.18.2, so reflection is used to obtain it.
            if (category != null) {
                try{
                    Biome.BiomeCategory biomeCategory = (Biome.BiomeCategory) Biome.class.getMethod("getBiomeCategory").invoke(null);
                    if (!category.equalsIgnoreCase(biomeCategory.getName())) return false;
                }catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }

            // Check the depth, scale, and base temp conditions.
            if (!checkData(downFall, biomeBase.getDownfall())) return false;
            if (!checkData(baseTempData, biomeBase.getBaseTemperature())) return false;


        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check if the data for range values are correct.
     *
     * @param data      The inputted data from the config file.
     * @param inputData The data from the field to check.
     * @return If the data matches up in the range or exact value.
     */
    private boolean checkData(@Nullable String data, float inputData) {
        if (data == null) return true;

        try {
            return Float.parseFloat(data) == inputData;
        } catch (NumberFormatException ex) {
            // Do nothing.
        }

        if (!data.startsWith("[")) throw new IllegalArgumentException("Invalid Data for custom biome!");
        if (!data.contains(";")) throw new IllegalArgumentException("Invalid Data for custom biome!");
        data = data.replace("[", "").replace("]", "");
        String[] splitData = data.split(";");
        float rangeOne, rangeTwo;
        try {
            rangeOne = Float.parseFloat(splitData[0]);
            rangeTwo = Float.parseFloat(splitData[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid data for custom biome!");
        }

        return inputData >= rangeOne && inputData <= rangeTwo;
    }
}
