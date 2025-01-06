package agh.oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boundary {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    Boundary(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean contains(Vector2d point) {
        return contains(point.getX(), point.getY());
    }

    public boolean contains(int x, int y) {
        return !isOobLeft(x) && !isOobRight(x)
                && !isOobDown(y) && isOobUp(y);
    }

    public boolean isOobLeft(int x) {
        return x < lowerLeft.getX();
    }

    public boolean isOobRight(int x) {
        return x > upperRight.getX();
    }

    public boolean isOobDown(int y) {
        return y < lowerLeft.getY();
    }

    public boolean isOobUp(int y) {
        return y > upperRight.getY();
    }

    public int width() {
        return upperRight.getX() - lowerLeft.getX();
    }

    public int height() {
        return upperRight.getY() - lowerLeft.getY();
    }

    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public int area() {
        return width() * height();
    }

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
