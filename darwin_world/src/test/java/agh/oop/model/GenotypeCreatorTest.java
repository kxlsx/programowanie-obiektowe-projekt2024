package agh.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class GenotypeCreatorTest {
    static private class GenotypeCreatorTestNoMutation extends GenotypeCreator {

        public GenotypeCreatorTestNoMutation(int geneCount) {
            super(geneCount);
        }

        @Override
        protected int mutate(int gene) {
            return gene;
        }

    }


    @Test
    void mix() {
        // given
        GenotypeCreatorTestNoMutation genotypeCreator = new GenotypeCreatorTestNoMutation(5);
        var g1 = genotypeCreator.create();
        var g2 = genotypeCreator.create();
        var a1 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g1, 2, 0);
        var a2 = new Animal(new Vector2d(0, 0), MapDirection.NORTH, g2, 3, 0);

        // when
        var ans = genotypeCreator.mixAnimals(a1, a2);

        // then
        boolean firstLeft = true;
        boolean firstRight = true;

        for(int i = 0; i < 2; i++) {
            if(g1.getGenes()[i] != ans.getGenes()[i]) {
                firstLeft = false;
            }
        }
        for(int i = 2; i < 5; i++) {
            if(g2.getGenes()[i - 2] != ans.getGenes()[i]) {
                firstLeft = false;
            }
        }

        for(int i = 0; i < 3; i++) {
            if(g2.getGenes()[i] != ans.getGenes()[i]) {
                firstRight = false;
            }
        }
        for(int i = 3; i < 5; i++) {
            if(g1.getGenes()[i - 3] != ans.getGenes()[i]) {
                firstRight = false;
            }
        }

        assert(firstLeft || firstRight);
    }


}