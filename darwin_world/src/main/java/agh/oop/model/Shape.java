package agh.oop.model;


import javafx.scene.paint.Color;

public class Shape {
    private Color color;
    private ShapeType type;
    private double alpha;

    /**
     * Create Shape with passed parameters.
     *
     * @param color color of shape.
     * @param type type of shape.
     * @param alpha opacity of shape.
     */
    public Shape(Color color, ShapeType type, double alpha) {
        this.color = color;
        this.type = type;
        this.alpha = alpha;
    }

    /**
     *
     * @return color of shape.
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color color of shape.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * @return type of shape.
     */
    public ShapeType getType() {
        return type;
    }

    /**
     *
     * @param type type of shape
     */
    public void setType(ShapeType type) {
        this.type = type;
    }

    /**
     *
     * @return opacity of shape.
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     *
     * @param alpha opacity of shape.
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
