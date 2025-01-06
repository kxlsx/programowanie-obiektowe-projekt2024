package agh.oop.model;

import java.util.stream.IntStream;

/**
 * Object used to create new Genotypes for the Animals.
 * @see Genotype
 * @see Animal
 */
public abstract class GenotypeCreator {
    protected static final int GENE_MAX = MapDirection.VALUE_COUNT;
    protected final int geneCount;

    /**
     * Initialize a new GenotypeCreator creating
     * Genotypes with geneCount genes.
     * @param geneCount the number of genes that the Genotypes should have.
     */
    public GenotypeCreator(int geneCount){
        this.geneCount = geneCount;
    }

    /**
     * Create a new, completely random Genotype.
     * @return a Genotype object
     */
    public Genotype create() {
        int[] genes = new int[geneCount];

        for(int i = 0; i < geneCount; i++) {
            genes[i] = randomGene();
        }

        return new Genotype(genes, randomStartIndex());
    }

    /**
     * Creates a new Genotype mixing parent1's
     * and parent2's Genotypes.
     * The creator takes genes from parents
     * proportionally to their relative
     * energy values.
     * Random mutations occur after mixing.
     *
     * @param parent1 animal parent 1
     * @param parent2 animal parent 2
     * @return new mixed & mutated Genotype
     */
    public Genotype mixAnimals(Animal parent1, Animal parent2) {
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();

        double energySum = energy1 + energy2;

        // FIXME: dunno if its sensible for one parent to pass 0
        // genes but currently it can be the case.
        int genesFrom1 =(int)((energy1 / energySum) * geneCount);
        int genesFrom2 = geneCount - genesFrom1;

        boolean is1Left = randomBoolean();
        Genotype startGene, endGene;
        int startGeneCount, endGeneCount;
        if(is1Left) {
            startGene = parent1.getGenes();
            startGeneCount = genesFrom1;
            endGene = parent2.getGenes();
            endGeneCount = genesFrom2;
        } else {
            startGene = parent2.getGenes();
            startGeneCount = genesFrom2;
            endGene = parent1.getGenes();
            endGeneCount = genesFrom1;
        }

        int[] newGenes = IntStream.concat(
                startGene.stream().limit(startGeneCount),
                endGene.stream().limit(endGeneCount)
        ).toArray();

        mutateGenes(newGenes);

        return new Genotype(newGenes, randomStartIndex());
    }

    /**
     * Randomly mutate a random amount of genes.
     * @param genes an array of ints representing individual genes.
     */
    protected void mutateGenes(int[] genes) {
        for(int i = 0; i < geneCount; i++) {
            if (randomBoolean()) {
                genes[i] = mutate(genes[i]);
            }
        }
    }

    protected abstract int mutate(int gene);

    /**
     * Returns a random index to initialize a Genotype.
     * @return index for a genes array.
     */
    protected int randomStartIndex() {
        return (int)(Math.random() * geneCount);
    }

    /**
     * Returns a completely random gene.
     * @return int representing an individual gene.
     */
    protected int randomGene() {
        return (int) (Math.random() * GENE_MAX);
    }

    /**
     * @return 50% it's true, 50% it's false.
     */
    protected static boolean randomBoolean() {
        return Math.random() < 0.5;
    }
}
