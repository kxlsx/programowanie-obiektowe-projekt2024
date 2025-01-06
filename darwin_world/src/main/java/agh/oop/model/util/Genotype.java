package agh.oop.model.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Genotype {
    private final int[] genes;
    private int currentGeneIndex;

    public Genotype(int[] genes, int startIndex) {
        this.genes = genes;
        currentGeneIndex = startIndex;
    }

    public MapDirection getNextMapDirection(MapDirection facing) {
        int times = genes[currentGeneIndex];
        currentGeneIndex = (currentGeneIndex + 1) % genes.length;
        return facing.rotateRight(times);
    }

    public IntStream stream() {
        return Arrays.stream(genes);
    }
}
