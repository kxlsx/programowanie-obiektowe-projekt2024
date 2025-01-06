package agh.oop.model;

public class GenotypeCreatorFullRandom extends GenotypeCreator {
    public GenotypeCreatorFullRandom(int geneCount) {
        super(geneCount);
    }

    @Override
    public int mutate(int gene) {
        return randomGene();
    }
}
