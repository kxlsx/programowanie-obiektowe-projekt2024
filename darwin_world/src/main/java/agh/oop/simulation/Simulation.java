package agh.oop.simulation;

import agh.oop.model.SimulationProgressListener;
import agh.oop.model.WorldMap;
import agh.oop.model.animal.Animal;
import agh.oop.model.animal.AnimalComparator;
import agh.oop.model.animal.AnimalCreator;
import agh.oop.model.plant.Plant;
import agh.oop.model.plant.PlantCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final ArrayList<SimulationProgressListener> progressListeners = new ArrayList<>();

    private final AtomicBoolean running = new AtomicBoolean(true);
    //private final AtomicBoolean paused = new AtomicBoolean(false);
    private final PauseMechanism pause;

    /**
     * Create Simulation wit hpassed parameters.
     *
     * @param map simulation map object.
     * @param animals list of animals(TODO).
     * @param animalComparator used to compare animals that fight for food or to reproduce.
     * @param animalCreator object used for reproduction and creation of animals.
     * @param plantCreator used for creating new animals.
     * @param energyFromPlant how much energy animal gains from eating plant.
     * @param reproductionEnergyThreshold required energy for reproduction.
     * @param initialNumberOfAnimals how many animals to create initially.
     * @param initialNumberOfPlants how many plants to create initially.
     */
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
        pause = new PauseMechanism();

        // create initial animals
        for(int i = 0; i < initialNumberOfAnimals; i++) {
            var animal = animalCreator.create(time);
            animals.add(animal);
            map.addAnimal(animal);
        }

        // create initial plants
        plantCreator.createPlants(map); // TODO use initial number of plants
    }

    /**
     * stops simulation
     */
    public void stop() {
        //paused.set(false);
        pause.unpause();
        running.set(false);
    }

    /**
     * starts simulation and advances it till stop is called.
     */
    @Override
    public void run() {
        while (running.get()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }

            try {
                pause.waitOnPause();
            } catch (InterruptedException e) {
                return;
            }

            advance();
        }
        System.out.println("Simulation stopped");
    }

    /**
     * Pauses simulation.
     */
    public void pause() {
        pause.pause();
    }


    /**
     * Unpauses simulation.
     */
    public void unpause() {
        pause.unpause();
    }


    /**
     * Performs one cycle/frame of simulation.
     */
    public void advance() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        growPlants();
        time++;
        progressListeners.forEach(SimulationProgressListener::afterAdvance);
    }

    /**
     *
     * @return simulation's WorldMap object.
     */
    public WorldMap getMap() {
        return map;
    }

    /**
     *
     * @return simulation's PlantCreator object.
     */
    public PlantCreator getPlantCreator() {
        return plantCreator;
    }

    /**
     * Adds listener that gets notified every time advance finishes.
     *
     * @param listener new listener to add
     */
    public void addSimulationProgressListener(SimulationProgressListener listener) {
        progressListeners.add(listener);
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
            var strongest = animalComparator.sort(animalsPerPlant.get(plant));
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
            var strongest = animalComparator.sort(map.animalsAt(position));
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