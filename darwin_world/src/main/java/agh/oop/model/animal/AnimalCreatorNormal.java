package agh.oop.model.animal;

import agh.oop.model.Boundary;
import agh.oop.model.MapDirection;

/**
 * The default implementation of AnimalCreator
 */
public class AnimalCreatorNormal implements AnimalCreator {
    private final GenotypeCreator genotypeCreator;
    private final int reproductionCost;
    private final int energyOnCreate;

    /**
     * Initialize a new AnimalCreatorNormal
     * @param genotypeCreator the picked GenotypeCreator.
     * @param reproductionCost energy cost subtracted from each parent and added to child.
     * @param energyOnCreate initial energy for a created Animal (not from reproduction).
     */
    public AnimalCreatorNormal(GenotypeCreator genotypeCreator, int reproductionCost, int energyOnCreate) {
        this.genotypeCreator = genotypeCreator;
        this.reproductionCost = reproductionCost;
        this.energyOnCreate = energyOnCreate;
    }

    /**
     * Create a completely new Animal.
     * @param time birthDate of the Animal.
     * @return a new Animal
     */
    @Override
    public Animal create(long time, Boundary mapBoundary) {
        return new Animal(
                mapBoundary.randomVector(),
                MapDirection.createRandomMapDirection(),
                genotypeCreator.create(),
                energyOnCreate,
                time
        );
    }

    /**
     * Reproduce parent1 and parent2 creating a new Animal.
     * Parents lose energy and add it to the child.
     * @param parent1 first parent.
     * @param parent2 second parent.
     * @param time birthDate of the Animal.
     * @return an Animal with the mixed Genotype of parent1 and parent2.
     */
    @Override
    public Animal reproduce(Animal parent1, Animal parent2, long time) {
        var genotype = genotypeCreator.mixAnimals(parent1, parent2);
        parent1.loseEnergy(reproductionCost);
        parent2.loseEnergy(reproductionCost);
        var child = new Animal(parent1.getPosition(), MapDirection.createRandomMapDirection(), genotype, reproductionCost * 2, time);
        parent1.addChild(child);
        parent2.addChild(child);
        return child;
    }
}
