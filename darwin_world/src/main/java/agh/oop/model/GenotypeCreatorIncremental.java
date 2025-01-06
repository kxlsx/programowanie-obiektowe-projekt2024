package agh.oop.model;

public class GenotypeCreatorIncremental extends GenotypeCreator {
    public GenotypeCreatorIncremental(int geneCount, int geneMax) {
        super(geneCount, geneMax);
    }

    public int mutate(int gene) {
        return (randomBoolean()) ? gene + 1 : gene - 1;
    }
}
