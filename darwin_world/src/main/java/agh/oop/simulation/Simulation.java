package agh.oop.simulation;

import agh.oop.model.animal.*;
import agh.oop.simulation.config.MutationMode;
import agh.oop.simulation.config.PlantGrowthMode;
import agh.oop.simulation.config.SimulationConfiguration;
import agh.oop.model.*;
import agh.oop.model.plant.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final List<Animal> animals;
    private final AnimalComparator animalComparator;
    private final AnimalCreator animalCreator;
    private final PlantCreator plantCreator;
    private final int energyFromPlant;
    private final int reproductionEnergyThreshold;
    private long time;

    private final AtomicBoolean running = new AtomicBoolean(true);

    public Simulation(
            WorldMap map,
            List<Animal> animals,
            AnimalComparator animalComparator,
            AnimalCreator animalCreator,
            PlantCreator plantCreator,
            int energyFromPlant,
            int reproductionEnergyThreshold,
            int initialNumberOfAnimals,
            int initialNumberOfPlants
    ){
        this.map = map;
        this.animals = animals;
        this.animalComparator = animalComparator;
        this.animalCreator = animalCreator;
        this.plantCreator = plantCreator;
        this.energyFromPlant = energyFromPlant;
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
        time = 0;


        // create initial animals
        for(int i = 0; i < initialNumberOfAnimals; i++) {
            var animal = animalCreator.create(time);
            animals.add(animal);
            map.addAnimal(animal);
        }

        // create initial plants
        plantCreator.createPlants(map); // TODO use initial number of plants
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        while (running.get()) {
            advance();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
            }
        }
    }

    public void advance() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        growPlants();
        time++;
        map.print();
    }

    private void removeDeadAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).isDead()) {
                map.removeAnimal(animals.get(i));
                animals.set(i, animals.getLast());
                animals.removeLast();
            }
        }
    }

    private void moveAnimals() {
        for(var animal : animals) {
            map.moveAnimal(animal);
        }
    }

    private void feedAnimals() {
        HashMap<Plant, ArrayList<Animal>> animalsPerPlant = new HashMap<>();

        var positions = map.getAnimalsPositions();
        for (var position : positions) {
            var plant = map.plantAt(position);
            if (plant != null && !map.animalsAt(position).isEmpty()) {
                animalsPerPlant.putIfAbsent(plant, new ArrayList<>());
                animalsPerPlant.get(plant).addAll(map.animalsAt(position));
            }
        }

        for (var plant : animalsPerPlant.keySet()) {
            var strongest = animalComparator.getStrongest(animalsPerPlant.get(plant));
            strongest.getLast().addEnergy(plant.getEnergyMultiplier() * energyFromPlant);
            map.removePlant(plant);
        }
    }

    private void reproduceAnimals() {
        var positions = map.getAnimalsPositions();
        for (var position : positions) {
            if(map.animalsAt(position).size() < 2) {
                continue;
            }
            var strongest = animalComparator.getStrongest(map.animalsAt(position));
            var parent1 = strongest.getLast();
            var parent2 = strongest.get(strongest.size() - 2);
            if(parent2.getEnergy() < reproductionEnergyThreshold) {
                continue;
            }

            var child = animalCreator.reproduce(parent1, parent2, time);
            animals.add(child);
            map.addAnimal(child);
        }
    }

    private void growPlants() {
        plantCreator.createPlants(map);
    }
}