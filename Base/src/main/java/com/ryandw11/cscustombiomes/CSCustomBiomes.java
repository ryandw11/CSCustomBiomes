package com.ryandw11.cscustombiomes;

import com.ryandw11.structure.api.CustomStructuresAPI;
import com.ryandw11.structure.api.structaddon.CustomStructureAddon;
import com.ryandw11.structure.api.structaddon.StructureSection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * An addon for Custom Biome support with Custom Structures.
 *
 * @version 1.0
 */
public class CSCustomBiomes extends JavaPlugin {

    @Override
    public void onEnable() {
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


        switch (version) {
            case "v1_17_R1":
                // Use reflection to see if newer mc versions are included.
                try {
                    Class<? extends StructureSection> cs_1_17 = (Class<? extends StructureSection>) Class.forName("com.ryandw11.cscustombiomes.CustomBiomeAddon_1_17");
                    customBiomesAddon.addStructureSection(cs_1_17);
                } catch (ClassNotFoundException ex) {
                    getLogger().severe("A fatal error has occurred!");
                    getLogger().severe("It appears that the addon has been compiled with the wrong version of Java!");
                    getLogger().severe("Please ensure that you downloaded the right version or compiled the addon with Java 16+!");
                    return;
                }
                break;
            case "v1_16_R3":
                customBiomesAddon.addStructureSection(CustomBiomeAddon_1_16_R3.class);
                break;
            case "v1_15_R1":
                customBiomesAddon.addStructureSection(CustomBiomeAddon_1_15.class);
                break;
        }

        customStructuresAPI.registerCustomAddon(customBiomesAddon);
    }
}
