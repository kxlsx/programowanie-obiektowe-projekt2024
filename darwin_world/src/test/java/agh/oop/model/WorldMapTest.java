package agh.oop.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldMapTest {
    @Test
    void animalsPositions() {
        var map = new WorldMap(new Boundary(new Vector2d(-5, -5), new Vector2d(5, 5)));

        map.addAnimal(new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0));
        map.addAnimal(new Animal(new Vector2d(3, 3), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0));

        Set<Vector2d> tmp = new HashSet<>();
        tmp.add(new Vector2d(1, 1));
        tmp.add(new Vector2d(3, 3));
        assert(tmp.equals(map.getAnimalsPositions()));
    }

    @Test
    void animalsAt() {
        var map = new WorldMap(new Boundary(new Vector2d(-5, -5), new Vector2d(5, 5)));


        var a1 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var a2 = new Animal(new Vector2d(5, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var a3 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        map.addAnimal(a1);
        map.addAnimal(a2);
        map.addAnimal(a3);

        assert(map.animalsAt(new Vector2d(1, 1)).equals(Set.of(a1, a3)));
        assert(map.animalsAt(new Vector2d(5, 1)).equals(Set.of(a2)));
    }

    @Test
    void removeAnimal() {
        var map = new WorldMap(new Boundary(new Vector2d(1, 1), new Vector2d(5, 5)));
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        map.addAnimal(animal);
        map.removeAnimal(animal);

        assert(map.animalsAt(new Vector2d(1, 1)) == null);
    }

    @Test
    void moveAnimal() {
        var map = new WorldMap(new Boundary(new Vector2d(1, 1), new Vector2d(5, 5)));
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        map.addAnimal(animal);
        map.moveAnimal(animal);
        assert(map.animalsAt(new Vector2d(1, 1)) == null);
        assert(map.animalsAt(animal.getPosition()).equals(Set.of(animal)));
    }

    @Test
    void plantAt() {
        var map = new WorldMap(new Boundary(new Vector2d(-5, -5), new Vector2d(5, 5)));
        var plant = new Plant(1, new Vector2d(1, 1));

        map.addPlant(plant);

        assert(map.plantAt(new Vector2d(1, 1)).equals(plant));
        assert(map.plantAt(new Vector2d(0, 1)) == null);
    }

    @Test
    void removePlant() {
        var map = new WorldMap(new Boundary(new Vector2d(1, 1), new Vector2d(5, 5)));
        var plant = new Plant(1, new Vector2d(1, 1));

        map.addPlant(plant);
        map.removePlant(plant);

        assert(map.plantAt(new Vector2d(1, 1)) == null);
    }

    @Test
    void wrap() {
        var map = new WorldMap(new Boundary(new Vector2d(1, 1), new Vector2d(5, 5)));
        var animal = new Animal(new Vector2d(1, 1), MapDirection.WEST, new Genotype(new int[]{0}, 0), 5, 0);

        map.addAnimal(animal);
        map.moveAnimal(animal);
        assertEquals(animal.getPosition(), new Vector2d(5, 1));
    }

    @Test
    void clamp() {
        var map = new WorldMap(new Boundary(new Vector2d(1, 1), new Vector2d(5, 5)));
        var animal = new Animal(new Vector2d(1, 1), MapDirection.SOUTH, new Genotype(new int[]{0}, 0), 5, 0);

        map.addAnimal(animal);
        map.moveAnimal(animal);
        assertEquals(animal.getPosition(), new Vector2d(1, 1));
    }
}