package agh.oop.model;

public class Plant implements WorldElement{
    private final int energy;
    private final Boundary bounds;

    Plant(int energy, Boundary bounds) {
        this.energy = energy;
        this.bounds = bounds;
    }

    Plant(int energy, Vector2d position) {
        this(energy, new Boundary(position, position));
    }

    public int getEnergyMultiplier() {
        return energy;
    }
    public Boundary getBounds() { return bounds; }
}
