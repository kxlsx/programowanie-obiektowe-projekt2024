package agh.oop.model.animal;

import agh.oop.model.MapDirection;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A Genotype is the de-facto
 * decision maker for an Animal;
 * Animals change direction according to
 * the Genotype's internal gene array.
 * Should be created with a GenotypeCreator.
 *
 * @see Animal
 * @see GenotypeCreator
 */
public class Genotype {
    private final int[] genes;
    private int currentGeneIndex;

    /**
     * Create a Genotype with the passed parameters.
     *
     * @param genes array representing the moves an Animal should make.
     *              Individual genes represent how many times the animal
     *              should rotate right before moving.
     * @param startIndex starting index in the genes array.
     */
    public Genotype(int[] genes, int startIndex) {
        this.genes = genes;
        currentGeneIndex = startIndex;
    }

    /**
     * Returns a new MapDirection for the animal
     * and advances to the next gene.
     *
     * @param facing current MapDirection of the animal.
     * @return new MapDirection for the animal.
     *
     * @see MapDirection
     */
    public MapDirection getNextMapDirection(MapDirection facing) {
        int times = genes[currentGeneIndex];
        currentGeneIndex = (currentGeneIndex + 1) % genes.length;
        return facing.rotateRight(times);
    }

    public int getNextValue() {
        return genes[currentGeneIndex];
    }

    /**
     * @return the internal gene array as a stream of ints.
     */
    public IntStream stream() {
        return Arrays.stream(genes);
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }

    public Genotype deepCopy() {
        return new Genotype(genes.clone(), currentGeneIndex);
    }
}
