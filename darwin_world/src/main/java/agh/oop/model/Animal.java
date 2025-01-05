package agh.oop.model;

import java.util.List;

public class Animal {

    public Animal(Vector2d position, MapDirection facing, Genotype genes, int energy, long birthDate) {}

    public int getEnergy() {
        return 0;
    }

    // potrzebne, zeby wiedziec jaki jest stary
    public long getBirthDate() {

    }

    public int countDescendants() {

    }

    public void addEnergy(int amount) {

    }

    public boolean isDead() {
        return false;
    }

    // zakladam, ze on zwraca tam gdzie pojdzie i se przesuwa costam na kolejny ruch
    public MapDirection move() {}

    public void addChild(Animal child) {}
}
