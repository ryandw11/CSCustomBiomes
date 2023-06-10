package com.ryandw11.cscustombiomes;

/**
 * Version independent interface to get extra data from the Minecraft Biome class.
 */
public interface BiomeBaser {
    /**
     * Get the namespace of the biome.
     *
     * @return The namespace of the biome.
     */
    String getNamespace();

    /**
     * Get the path (key) of the biome.
     *
     * @return The path of the biome.
     */
    String getPath();

    /**
     * Check the namespace of the biome.
     *
     * @param namespace The namespace to compare.
     * @return If the two namespaces are the same.
     */
    boolean checkNamespace(String namespace);

    /**
     * Get the downfall of the biome.
     *
     * @return The downfall of the biome.
     */
    double getDownfall();

    /**
     * Get the base temp of the biome.
     *
     * @return The base temp of the biome.
     */
    double getBaseTemp();

    /**
     * Get the category of the biome.
     *
     * <p>Only returns on 1.18, does not exist on later versions.</p>
     *
     * @return The category of the biome.
     */
    String getCategory();
}
