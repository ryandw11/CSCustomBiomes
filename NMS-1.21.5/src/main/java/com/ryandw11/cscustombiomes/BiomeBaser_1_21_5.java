package com.ryandw11.cscustombiomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_21_R4.CraftRegistry;
import org.bukkit.craftbukkit.v1_21_R4.block.CraftBlock;

import java.lang.reflect.Field;

public class BiomeBaser_1_21_5 extends AbstractBiomeBaser {

    private final CraftBlock craftBlock;
    private final Biome baseBiome;
    private final ResourceLocation biomeKey;

    public BiomeBaser_1_21_5(Block block) {
        super(block);

        this.craftBlock = (CraftBlock) block;

        try {
            Field world = CraftBlock.class.getDeclaredField("world");
            world.setAccessible(true);
            ServerLevel worldServer = ((LevelAccessor) world.get(craftBlock)).getMinecraftWorld();
            this.baseBiome = worldServer.getBiome(craftBlock.getPosition()).value();
            this.biomeKey = CraftRegistry.getMinecraftRegistry(Registries.BIOME).getKey(this.baseBiome);
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
        return this.baseBiome.climateSettings.downfall();
    }

    @Override
    public double getBaseTemp() {
        return this.baseBiome.getBaseTemperature();
    }

    @Override
    public String getCategory() {
        return null;
    }
}
