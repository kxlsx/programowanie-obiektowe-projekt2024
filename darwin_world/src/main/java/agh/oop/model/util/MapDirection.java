package agh.oop.model.util;

import java.util.Map;

public interface MapDirection {
    Vector2d toUnitVector();
    MapDirection rotateRight(int times);
    String toString();
}
