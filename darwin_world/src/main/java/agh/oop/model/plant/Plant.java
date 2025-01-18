package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldElement;

public class Plant implements WorldElement {
    private final int energy;
    private final Boundary bounds;

    public Plant(int energy, Boundary bounds) {
        this.energy = energy;
        this.bounds = bounds;
    }

    public Plant(int energy, Vector2d position) {
        this(energy, new Boundary(position, position));
    }

    public int getEnergyMultiplier() {
        return energy;
    }
    public Boundary getBounds() { return bounds; }
}
