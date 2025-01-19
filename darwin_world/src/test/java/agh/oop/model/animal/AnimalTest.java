package agh.oop.model.animal;

import agh.oop.model.MapDirection;
import agh.oop.model.MoveValidator;
import agh.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void isAt() {
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        assertTrue(animal.isAt(new Vector2d(1, 1)));
        assertFalse(animal.isAt(new Vector2d(2, 1)));
    }

    @Test
    void isFacing() {
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        assertTrue(animal.isFacing(MapDirection.NORTH));
        assertFalse(animal.isFacing(MapDirection.SOUTH));
    }

    @Test
    void addEnergy() {
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        animal.addEnergy(2);

        assertEquals(7, animal.getEnergy());
    }

    @Test
    void isDead() {
        var animal1 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var animal2 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 0, 0);

        assertFalse(animal1.isDead());
        assertTrue(animal2.isDead());
    }

    @Test
    void move() {
        class Validator implements MoveValidator {
            @Override
            public Vector2d correctPosition(Vector2d position) {
                return position;
            }
        }

        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        animal.move(new Validator());

        assertEquals(new Vector2d(2, 2), animal.getPosition());
    }

    @Test
    void countDescendants() {
        var animal = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var c1 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var c2 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var c3 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var c4 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);
        var c5 = new Animal(new Vector2d(1, 1), MapDirection.NORTH, new Genotype(new int[]{1, 2, 3}, 0), 5, 0);

        animal.addChild(c1);
        animal.addChild(c2);
        c1.addChild(c3);
        c2.addChild(c3);
        c3.addChild(c4);
        c4.addChild(c5);

        assertEquals(5, animal.countDescendants());
    }
}