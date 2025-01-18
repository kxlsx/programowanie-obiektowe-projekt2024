package agh.oop.model.animal;

import agh.oop.model.MapDirection;
import agh.oop.model.Vector2d;

public class AnimalCreatorNormal implements AnimalCreator {
    private final GenotypeCreator genotypeCreator;
    private final int reproductionCost;
    private final int energyOnCreate;

    public AnimalCreatorNormal(GenotypeCreator genotypeCreator, int reproductionCost, int energyOnCreate) {
        this.genotypeCreator = genotypeCreator;
        this.reproductionCost = reproductionCost;
        this.energyOnCreate = energyOnCreate;
    }

    @Override
    public Animal create(long time) {
        return new Animal(
                new Vector2d(0, 0), // TODO: randomize pos
                MapDirection.createRandomMapDirection(),
                genotypeCreator.create(),
                energyOnCreate,
                time
        );
    }

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
