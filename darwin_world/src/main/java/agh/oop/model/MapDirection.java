package agh.oop.model;

public interface MapDirection {
    Vector2d toUnitVector();
    MapDirection rotateRight(int times);
    String toString();
}
