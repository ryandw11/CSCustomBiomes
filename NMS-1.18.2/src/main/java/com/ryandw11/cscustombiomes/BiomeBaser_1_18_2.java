package com.ryandw11.cscustombiomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BiomeBaser_1_18_2 extends AbstractBiomeBaser {

    private final CraftBlock craftBlock;
    private final Biome baseBiome;
    private final ResourceLocation biomeKey;
    private Biome.BiomeCategory biomeCategory;

    public BiomeBaser_1_18_2(Block block) {
        super(block);

        this.craftBlock = (CraftBlock) block;

        try {
            Field world = CraftBlock.class.getDeclaredField("world");
            world.setAccessible(true);
            ServerLevel worldServer = ((LevelAccessor) world.get(craftBlock)).getMinecraftWorld();
            this.baseBiome = worldServer.getBiome(craftBlock.getPosition()).value();

            DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();

            Registry<Biome> registry = dedicatedServer.registryAccess().ownedRegistry(Registry.BIOME_REGISTRY).get();

            this.biomeKey = registry.getKey(this.baseBiome);

            try {
                biomeCategory = (Biome.BiomeCategory) Biome.class.getMethod("getBiomeCategory").invoke(null);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getNamespace() {
        return this.biomeKey.getNamespace();
    }

    @Override
    public String getPath() {
        return this.biomeKey.getPath();
    }

    @Override
    public boolean checkNamespace(String namespace) {
        return this.biomeKey.getNamespace().equalsIgnoreCase(namespace);
    }

    @Override
    public double getDownfall() {
        return this.baseBiome.getDownfall();
    }

    @Override
    public double getBaseTemp() {
        return this.baseBiome.getBaseTemperature();
    }

    @Override
    public String getCategory() {
        if (biomeCategory == null) return "";
        return biomeCategory.getName();
    }
}
