package agh.oop.model.animal;

/**
 * GenotypeCreator mutating genes to completely random ones.
 * @see GenotypeCreator
 */
public class GenotypeCreatorFullRandom extends GenotypeCreator {
    public GenotypeCreatorFullRandom(int geneCount) {
        super(geneCount);
    }

    /**
     * Returns a new completely random gene.
     * @param gene this param is irrelevant.
     * @return int representing a new gene.
     */
    @Override
    public int mutate(int gene) {
        return randomGene();
    }
}
