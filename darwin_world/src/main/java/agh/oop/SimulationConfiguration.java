package agh.oop;

import agh.oop.model.*;

import java.io.Serializable;

public class SimulationConfiguration implements Serializable {
    private Vector2d mapSize;
    private int initialNumberOfPlants;
    private int energyFromPlant;
    private int plantGrowthPerDay;
    private int initialNumberOfAnimals;
    private int initialAnimalEnergy;
    private int reproductionEnergyThreshold;
    private int reproductionCost;
    private int genomeLength;
    private MapType mapType;
    private PlantGrowthMode plantGrowthMode;
    private MutationMode mutationMode;

    public SimulationConfiguration(
            Vector2d mapSize,
            int initialNumberOfPlants,
            int energyFromPlant,
            int plantGrowthPerDay,
            int initialNumberOfAnimals,
            int initialAnimalEnergy,
            int reproductionEnergyThreshold,
            int reproductionCost,
            int genomeLength,
            MapType mapType,
            PlantGrowthMode plantGrowthMode,
            MutationMode mutationMode)
    {
        this.mapSize = mapSize;
        this.initialNumberOfPlants = initialNumberOfPlants;
        this.energyFromPlant = energyFromPlant;
        this.plantGrowthPerDay = plantGrowthPerDay;
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
        this.reproductionCost = reproductionCost;
        this.genomeLength = genomeLength;
        this.mapType = mapType;
        this.plantGrowthMode = plantGrowthMode;
        this.mutationMode = mutationMode;
    }


    public Vector2d getMapSize() {
        return mapSize;
    }

    public void setMapSize(Vector2d mapSize) {
        this.mapSize = mapSize;
    }

    public int getInitialNumberOfPlants() {
        return initialNumberOfPlants;
    }

    public void setInitialNumberOfPlants(int initialNumberOfPlants) {
        this.initialNumberOfPlants = initialNumberOfPlants;
    }

    public int getEnergyFromPlant() {
        return energyFromPlant;
    }

    public void setEnergyFromPlant(int energyFromPlant) {
        this.energyFromPlant = energyFromPlant;
    }

    public int getPlantGrowthPerDay() {
        return plantGrowthPerDay;
    }

    public void setPlantGrowthPerDay(int plantGrowthPerDay) {
        this.plantGrowthPerDay = plantGrowthPerDay;
    }

    public int getInitialNumberOfAnimals() {
        return initialNumberOfAnimals;
    }

    public void setInitialNumberOfAnimals(int initialNumberOfAnimals) {
        this.initialNumberOfAnimals = initialNumberOfAnimals;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public void setInitialAnimalEnergy(int initialAnimalEnergy) {
        this.initialAnimalEnergy = initialAnimalEnergy;
    }

    public int getReproductionEnergyThreshold() {
        return reproductionEnergyThreshold;
    }

    public void setReproductionEnergyThreshold(int reproductionEnergyThreshold) {
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
    }

    public int getReproductionCost() {
        return reproductionCost;
    }

    public void setReproductionCost(int reproductionCost) {
        this.reproductionCost = reproductionCost;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public PlantGrowthMode getPlantGrowthMode() {
        return plantGrowthMode;
    }

    public void setPlantGrowthMode(PlantGrowthMode plantGrowthMode) {
        this.plantGrowthMode = plantGrowthMode;
    }

    public MutationMode getMutationMode() {
        return mutationMode;
    }

    public void setMutationMode(MutationMode mutationMode) {
        this.mutationMode = mutationMode;
    }
}
