package agh.oop.model;


import javafx.scene.paint.Color;

public class Shape {
    private Color color;
    private ShapeType type;
    private double alpha;

    public Shape(Color color, ShapeType type, double alpha) {
        this.color = color;
        this.type = type;
        this.alpha = alpha;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
