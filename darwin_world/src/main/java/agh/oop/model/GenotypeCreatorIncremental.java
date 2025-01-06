package agh.oop.model;

/**
 * GenotypeCreator mutating genes incrementally.
 *
 * @see GenotypeCreator
 */
public class GenotypeCreatorIncremental extends GenotypeCreator {
    public GenotypeCreatorIncremental(int geneCount) {
        super(geneCount);
    }

    /**
     * Increment or decrement the gene randomly.
     * @param gene gene to be changed.
     * @return gene + 1 or gene - 1.
     */
    public int mutate(int gene) {
        return (randomBoolean()) ? gene + 1 : gene - 1;
    }
}
