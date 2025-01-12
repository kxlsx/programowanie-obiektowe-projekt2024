package agh.oop.model;

import org.junit.jupiter.api.Test;

class GenotypeCreatorIncrementalTest {
    @Test
    void mutate() {
        // given
        var creator = new GenotypeCreatorIncremental(5);
        int[] genes = {1, 2, 3, 4, 5};

        for(int i = 0; i < 5; i++) {
            assert( Math.abs(genes[i] - creator.mutate(genes[i])) == 1);
        }
    }
}