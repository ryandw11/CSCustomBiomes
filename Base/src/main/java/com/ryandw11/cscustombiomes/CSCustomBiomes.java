package com.ryandw11.cscustombiomes;

import com.ryandw11.cscustombiomes.addon.CustomBiomeRangeSection;
import com.ryandw11.cscustombiomes.addon.CustomBiomesSection;
import com.ryandw11.cscustombiomes.commands.InspectBiomeCommand;
import com.ryandw11.structure.api.CustomStructuresAPI;
import com.ryandw11.structure.api.structaddon.CustomStructureAddon;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * An addon for Custom Biome support with Custom Structures.
 *
 * @version 1.1.0
 */
public class CSCustomBiomes extends JavaPlugin {

    private static CSCustomBiomes instance;

    private Class<? extends BiomeBaser> biomeBaser;
    private MinecraftVersion minecraftVersion = MinecraftVersion.vUnknown;

    @Override
    public void onEnable() {
        instance = this;

        CustomStructureAddon customBiomesAddon = new CustomStructureAddon(this);

        CustomStructuresAPI customStructuresAPI = new CustomStructuresAPI();

        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            getLogger().severe("A fatal error has occurred!");
            getLogger().severe("Unable to determine server version!");
            return;
        }

        customBiomesAddon.addStructureSection(CustomBiomesSection.class);
        customBiomesAddon.addStructureSection(CustomBiomeRangeSection.class);

        Objects.requireNonNull(getCommand("biomeinspect")).setExecutor(new InspectBiomeCommand());

        switch (version) {
            case "v1_20_R3":
                minecraftVersion = MinecraftVersion.v1_20_4;
                biomeBaser = BiomeBaser_1_20_4.class;
                customBiomesAddon.addStructureSection(LegacyCustomBiomeAddon_1_20_4.class);
                break;
            case "v1_20_R2":
                minecraftVersion = MinecraftVersion.v1_20_2;
                biomeBaser = BiomeBaser_1_20_2.class;
                customBiomesAddon.addStructureSection(LegacyCustomBiomeAddon_1_20_2.class);
                break;
            case "v1_20_R1":
                minecraftVersion = MinecraftVersion.v1_20_0;
                biomeBaser = BiomeBaser_1_20.class;
                customBiomesAddon.addStructureSection(LegacyCustomBiomeAddon_1_20.class);
                break;
            case "v1_19_R3":
                minecraftVersion = MinecraftVersion.v1_19_4;
                biomeBaser = BiomeBaser_1_19_4.class;
                customBiomesAddon.addStructureSection(CustomBiomeAddon_1_19_4.class);
                break;
            case "v1_19_R1":
                minecraftVersion = MinecraftVersion.v_1_19_0;
                biomeBaser = BiomeBaser_1_19.class;
                customBiomesAddon.addStructureSection(CustomBiomeAddon_1_19.class);
                break;
            case "v1_18_R2":
                minecraftVersion = MinecraftVersion.v1_18_2;
                biomeBaser = BiomeBaser_1_18_2.class;
                customBiomesAddon.addStructureSection(CustomBiomeAddon_1_18_2.class);
                break;
            default:
                getLogger().severe("The Custom Biomes addon does not support the version " + version + " of minecraft!");
                return;
        }

        customStructuresAPI.registerCustomAddon(customBiomesAddon);
    }

    public static CSCustomBiomes getInstance() {
        return instance;
    }

    /**
     * Get the biome baser for a block depending on the current Minecraft version.
     *
     * @param block The block.
     * @return The correct biome baser. (Null if invalid version).
     */
    public BiomeBaser getBiomeBaserForBlock(Block block) {
        if (biomeBaser == null) {
            return null;
        }

        try {
            return biomeBaser.getDeclaredConstructor(Block.class).newInstance(block);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MinecraftVersion getMinecraftVersion() {
        return minecraftVersion;
    }
}
