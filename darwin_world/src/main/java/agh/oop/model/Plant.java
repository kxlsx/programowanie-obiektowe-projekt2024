package agh.oop.model;

public class Plant implements WorldElement{
    private final int energy;
    private final Boundary field;

    Plant(int energy, Boundary field) {
        this.energy = energy;
        this.field = field;
    }

    Plant(int energy, Vector2d position) {
        this(energy, new Boundary(position, position));
    }

    public int getEnergyMultiplier() {
        return energy;
    }
}
