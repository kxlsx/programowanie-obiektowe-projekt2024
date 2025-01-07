package agh.oop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representing a rectangular
 * field of Vector2ds
 * @see Vector2d
 */
public class Boundary {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    /**
     * @param lowerLeft the lower-left vertex of the rectangle.
     * @param upperRight the upper-right vertex of the rectangle.
     */
    public Boundary(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    /**
     * @param point vector to check.
     * @return true if point is inside the boundary.
     */
    public boolean contains(Vector2d point) {
        return contains(point.getX(), point.getY());
    }

    /**
     * @param x x coordinate to check.
     * @param y y coordinate to check.
     * @return true if the point (x,y) is inside the boundary.
     */
    public boolean contains(int x, int y) {
        return !isOobLeft(x) && !isOobRight(x)
                && !isOobDown(y) && !isOobUp(y);
    }

    /**
     * @param x x coordinate to check.
     * @return true if x is to the left of lowerLeft's x coordinate.
     */
    public boolean isOobLeft(int x) {
        return x < lowerLeft.getX();
    }

    /**
     * @param x x coordinate to check.
     * @return true if x is to the right of upperRight's x coordinate.
     */
    public boolean isOobRight(int x) {
        return x > upperRight.getX();
    }

    /**
     * @param y y coordinate to check.
     * @return true if y is below lowerLeft's y coordinate.
     */
    public boolean isOobDown(int y) {
        return y < lowerLeft.getY();
    }

    /**
     * @param y y coordinate to check.
     * @return true if y is above upperRight's y coordinate.
     */
    public boolean isOobUp(int y) {
        return y > upperRight.getY();
    }

    /**
     * @return the width of the boundary
     */
    public int width() {
        return upperRight.getX() - lowerLeft.getX() + 1;
    }

    /**
     * @return the height of the boundary
     */
    public int height() {
        return upperRight.getY() - lowerLeft.getY() + 1;
    }

    /**
     * @return lower-left vertex of the rectangle
     */
    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    /**
     * @return upper-right vertex of the rectangle
     */
    public Vector2d getUpperRight() {
        return upperRight;
    }

    /**
     * @return the area of the rectangle
     */
    public int area() {
        return width() * height();
    }

    /**
     * @return a list of Vector2ds contained
     * inside the rectangle represented by the boundary.
     */
    public List<Vector2d> containedVectors() {
        List<Vector2d> vectors = new ArrayList<>();

        for(int i = 0; i < width(); i++) {
            for(int j = 0; j < height(); j++) {
                vectors.add(lowerLeft.add(new Vector2d(i, j)));
            }
        }
        return vectors;
    }
}
