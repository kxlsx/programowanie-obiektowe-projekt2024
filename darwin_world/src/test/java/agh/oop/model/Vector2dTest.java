package agh.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Vector2dTest {

    private Vector2d pos = new Vector2d(73, 12);
    private Vector2d neg = new Vector2d(-3, -2);
    private Vector2d zero = new Vector2d(0, 0);
    private Vector2d posneg = new Vector2d(3, -2);
    private Vector2d negpos = new Vector2d(-3, 2);

    @Test
    public void add() {
        assertEquals(pos.add(neg), new Vector2d(70, 10));
        assertEquals(pos.add(zero), pos);
        assertEquals(pos.add(posneg), new Vector2d(76, 10));
        assertEquals(pos.add(negpos), new Vector2d(70, 14));

    }

    @Test
    public void subtract() {
        assertEquals(pos.subtract(pos), pos.add(pos.opposite()));
        assertEquals(pos.subtract(zero), pos);
        assertEquals(pos.subtract(posneg), new Vector2d(70, 14));
    }

    @Test
    public void upperRight() {
        assertEquals(posneg.upperRight(negpos), new Vector2d(3, 2));
    }

    @Test
    public void lowerLeft() {
        assertEquals(posneg.lowerLeft(negpos), new Vector2d(-3, -2));
    }

    @Test
    public void follows() {
        assertTrue(pos.follows(neg));
        assertTrue(pos.follows(posneg));
        assertTrue(pos.follows(negpos));
        assertFalse(neg.follows(pos));
    }

    @Test
    public void opposite() {
        assertEquals(pos.add(pos.opposite()), zero);
        assertEquals(neg.add(neg.opposite()), zero);
        assertEquals(posneg.add(posneg.opposite()), zero);
        assertEquals(negpos.add(negpos.opposite()), zero);
    }

    @Test
    public void equals() {
        assertEquals(pos.equals(pos), pos.equals(new Vector2d(73, 12)));
        assertEquals(pos.equals(neg), pos.equals(new Vector2d(-3, -2)));
    }
}
