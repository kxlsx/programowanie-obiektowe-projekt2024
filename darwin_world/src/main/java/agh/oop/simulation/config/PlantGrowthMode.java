package agh.oop.simulation.config;

public enum PlantGrowthMode {
    EQUATOR,
    BOUNTIFUL_HARVEST;

    public static PlantGrowthMode fromString(String status) {
        try {
            return PlantGrowthMode.valueOf(status);  // Converts String to enum
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
