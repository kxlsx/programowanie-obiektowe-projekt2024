package agh.oop.model;

/**
 * Enum representing a cardinal direction
 * on the map. There are 8 possible direction
 * (NORTH, NORTH_EAST, ..., WEST, NORTH_WEST).
 */
public enum MapDirection{
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    final static MapDirection[] VALUES = MapDirection.values();
    final static int VALUE_COUNT = VALUES.length;

    /**
     * Returns the direction as a Vector2D
     * For example, NORTH is (0, 1), SOUT_EAST is (1, -1) etc.
     * @return vector representing the direction.
     */
    public Vector2d toUnitVector() {
        return switch(this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }

    /**
     * Rotate the direction to the right.
     * @param times number of times to rotate.
     * @return a new direction
     */
    public MapDirection rotateRight(int times) {
        int nextOrdinal = (this.ordinal() + times) % VALUE_COUNT;
        return VALUES[nextOrdinal];
    }

    /**
     * @return a random MapDirection.
     */
    public static MapDirection createRandomMapDirection() {
        return VALUES[(int)(Math.random() * VALUE_COUNT)];
    }
}
