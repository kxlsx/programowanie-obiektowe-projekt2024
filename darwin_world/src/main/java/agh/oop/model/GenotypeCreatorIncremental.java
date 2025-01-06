package agh.oop.model;

public class GenotypeCreatorIncremental extends GenotypeCreator {
    public GenotypeCreatorIncremental(int geneCount) {
        super(geneCount);
    }

    public int mutate(int gene) {
        return (randomBoolean()) ? gene + 1 : gene - 1;
    }
}
