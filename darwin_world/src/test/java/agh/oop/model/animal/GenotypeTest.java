package agh.oop.model.animal;

import agh.oop.model.MapDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenotypeTest {
    @Test
    void test() {
        var gen = new Genotype(new int[] {0, 1, 2, 3, 4}, 0);

        Assertions.assertEquals(MapDirection.NORTH, gen.getNextMapDirection(MapDirection.NORTH));
        assertEquals(MapDirection.NORTH_EAST, gen.getNextMapDirection(MapDirection.NORTH));
        assertEquals(MapDirection.EAST, gen.getNextMapDirection(MapDirection.NORTH));
        assertEquals(MapDirection.WEST, gen.getNextMapDirection(MapDirection.SOUTH_EAST));
        assertEquals(MapDirection.EAST, gen.getNextMapDirection(MapDirection.WEST));
    }
}