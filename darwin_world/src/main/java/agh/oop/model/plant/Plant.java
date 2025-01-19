package agh.oop.model.plant;

import agh.oop.model.*;
import javafx.scene.paint.Color;

public class Plant implements WorldElement {
    private final int energy;
    private final Boundary bounds;

    /**
     * Create Plant with passed parameters.
     *
     * @param energy plant energy value when consumed.
     * @param bounds space occupied by plant.
     */
    public Plant(int energy, Boundary bounds) {
        this.energy = energy;
        this.bounds = bounds;
    }

    /**
     *
     * @param energy plant energy value when consumed.
     * @param position position of plant taking single cell.
     */
    public Plant(int energy, Vector2d position) {
        this(energy, new Boundary(position, position));
    }

    /**
     *
     * @return amount of energy that animal gains when eating this plant.
     */
    public int getEnergyMultiplier() {
        return energy;
    }

    /**
     *
     * @return space occupied by plant.
     */
    public Boundary getBounds() { return bounds; }

    @Override
    public Shape getShape() {
        return new Shape((bounds.area() > 1 ? Color.DARKGREEN : Color.LIGHTGREEN), ShapeType.SQUARE, 1.);
    }
}