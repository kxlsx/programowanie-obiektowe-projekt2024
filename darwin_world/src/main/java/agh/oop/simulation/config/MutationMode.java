package agh.oop.simulation.config;

public enum MutationMode {
    FULL_RANDOM,
    INCREMENTAL;

    public static MutationMode fromString(String status) {
        try {
            return MutationMode.valueOf(status);  // Converts String to enum
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
