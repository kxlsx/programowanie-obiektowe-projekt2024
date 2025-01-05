package agh.oop.model;

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
        return x >= lowerLeft.getX() && x <= upperRight.getX()
                && y >= lowerLeft.getY() && y <= upperRight.getY();
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
}
