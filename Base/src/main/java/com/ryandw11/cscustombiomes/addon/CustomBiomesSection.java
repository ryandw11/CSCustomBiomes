package com.ryandw11.cscustombiomes.addon;

import com.ryandw11.cscustombiomes.BiomeBaser;
import com.ryandw11.cscustombiomes.CSCustomBiomes;
import com.ryandw11.structure.api.structaddon.StructureSection;
import com.ryandw11.structure.structure.Structure;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomBiomesSection implements StructureSection {
    private List<String> customBiomeNamespaces = new ArrayList<>();

    @Override
    public String getName() {
        return "CustomBiomes";
    }

    @Override
    public void setupSection(@Nullable ConfigurationSection configurationSection) {
        if (configurationSection == null) return;

        if (configurationSection.contains("Namespaces")) {
            customBiomeNamespaces = configurationSection.getStringList("Namespaces");
            customBiomeNamespaces = customBiomeNamespaces.stream().map(String::toLowerCase).collect(Collectors.toList());
        }
    }

    @Override
    public boolean checkStructureConditions(Structure structure, Block block, Chunk chunk) {
        if (customBiomeNamespaces.isEmpty()) return true;

        BiomeBaser biomeBaser = CSCustomBiomes.getInstance().getBiomeBaserForBlock(block);

        return customBiomeNamespaces.contains(String.format("%s:%s", biomeBaser.getNamespace().toLowerCase(), biomeBaser.getPath().toLowerCase()));
    }
}
