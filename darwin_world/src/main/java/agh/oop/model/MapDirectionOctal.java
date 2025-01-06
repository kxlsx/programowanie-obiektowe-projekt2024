package agh.oop.model;

public enum MapDirectionOctal implements MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    private final static MapDirectionOctal[] VALUES = MapDirectionOctal.values();
    private final static int VALUE_COUNT = VALUES.length;

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

    public MapDirection rotateRight(int times) {
        int nextOrdinal = (this.ordinal() + times) % VALUE_COUNT;
        return VALUES[nextOrdinal];
    }
}
