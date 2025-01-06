package agh.oop.model.util;

public class GenotypeCreatorFullRandom extends GenotypeCreator {
    public GenotypeCreatorFullRandom(int geneCount, int geneMax) {
        super(geneCount, geneMax);
    }

    @Override
    public int mutate(int gene) {
        return randomGene();
    }
}
