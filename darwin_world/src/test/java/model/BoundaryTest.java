package model;

import agh.oop.model.Boundary;
import agh.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoundaryTest {
    @Test
    public void contains_test(){
        Boundary b = new Boundary(new Vector2d(0, 0), new Vector2d(4, 4));

        for(Vector2d v : b.containedVectors()) {
            assertTrue(b.contains(v));
        }

        assertTrue(b.contains(new Vector2d(0, 0)));
        assertTrue(b.contains(new Vector2d(4, 4)));

        assertFalse(b.contains(new Vector2d(-1, 0)));
        assertFalse(b.contains(new Vector2d(0, -1)));
        assertFalse(b.contains(new Vector2d(5, 4)));
        assertFalse(b.contains(new Vector2d(4, 5)));
    }

    @Test
    public void contained_vectors() {
        Boundary b1 = new Boundary(new Vector2d(0, 0), new Vector2d(4, 4));
        Boundary b2 = new Boundary(new Vector2d(-4, -4), new Vector2d(0, 0));

        assertEquals(b1.containedVectors().size(), 25);
        assertEquals(b2.containedVectors().size(), 25);

        Iterator<Vector2d> v1it = b1.containedVectors().iterator();
        Iterator<Vector2d> v2it = b2.containedVectors().iterator();
        for(int i = 0; i < 5; i ++) {
            for(int j = 0; j < 5; j++) {
                Vector2d v1 = v1it.next();
                Vector2d v2 = v2it.next();
                assertEquals(v1, b1.getLowerLeft().add(new Vector2d(i, j)));
                assertEquals(v2, b2.getLowerLeft().add(new Vector2d(i, j)));
            }
        }
    }
}
