package com.ryandw11.cscustombiomes;

import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public abstract class AbstractBiomeBaser implements BiomeBaser {
    protected Biome bukkitBiome;
    protected Block baseBlock;

    public AbstractBiomeBaser(Block block) {
        this.bukkitBiome = block.getBiome();
        this.baseBlock = block;
    }

    public Biome getBukkitBiome() {
        return bukkitBiome;
    }

    public Block getBaseBlock() {
        return baseBlock;
    }
}
