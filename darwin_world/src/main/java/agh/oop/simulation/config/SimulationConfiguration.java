package agh.oop.simulation.config;

import agh.oop.model.Vector2d;

import java.io.Serializable;

public record SimulationConfiguration(
        Vector2d mapSize,
        int initialNumberOfPlants,
        int energyFromPlant,
        int plantGrowthPerDay,
        int initialNumberOfAnimals,
        int initialAnimalEnergy,
        int reproductionEnergyThreshold,
        int reproductionCost,
        int minNumberOfMutations,
        int maxNumberOfMutations,
        int genomeLength,
        MapType mapType,
        PlantGrowthMode plantGrowthMode,
        MutationMode mutationMode) implements Serializable {}
