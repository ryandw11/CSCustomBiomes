package com.ryandw11.cscustombiomes.addon;

import com.ryandw11.cscustombiomes.BiomeBaser;
import com.ryandw11.cscustombiomes.CSCustomBiomes;
import com.ryandw11.structure.api.structaddon.StructureSection;
import com.ryandw11.structure.structure.Structure;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class CustomBiomeRangeSection implements StructureSection {

    private String baseTemp;
    private String downFall;

    @Override
    public String getName() {
        return "CustomBiomeRange";
    }

    @Override
    public void setupSection(@Nullable ConfigurationSection configurationSection) {
        if (configurationSection == null) return;

        baseTemp = configurationSection.getString("BaseTemp");
        downFall = configurationSection.getString("DownFall");
    }

    @Override
    public boolean checkStructureConditions(Structure structure, Block block, Chunk chunk) {
        BiomeBaser biomeBaser = CSCustomBiomes.getInstance().getBiomeBaserForBlock(block);

        return checkData(baseTemp, biomeBaser.getBaseTemp()) && checkData(downFall, biomeBaser.getDownfall());
    }

    /**
     * Check if the data for range values are correct.
     *
     * @param data      The inputted data from the config file.
     * @param inputData The data from the field to check.
     * @return If the data matches up in the range or exact value.
     */
    private boolean checkData(@Nullable String data, double inputData) {
        if (data == null) return true;

        try {
            return Double.parseDouble(data) == inputData;
        } catch (NumberFormatException ex) {
            // Do nothing.
        }

        if (!data.startsWith("[")) throw new IllegalArgumentException("Invalid Data for custom biome!");
        if (!data.contains(";")) throw new IllegalArgumentException("Invalid Data for custom biome!");
        data = data.replace("[", "").replace("]", "");
        String[] splitData = data.split(";");
        double rangeOne, rangeTwo;
        try {
            rangeOne = Double.parseDouble(splitData[0]);
            rangeTwo = Double.parseDouble(splitData[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid data for custom biome!");
        }

        return inputData >= rangeOne && inputData <= rangeTwo;
    }
}
