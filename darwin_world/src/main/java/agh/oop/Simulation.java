package agh.oop;

import agh.oop.model.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation implements Runnable {
    private WorldMap map;
    private ArrayList<Animal> animals;
    private SimulationConfiguration config;
    private GenotypeCreator genotypeCreator;
    private PlantCreator plantCreator;
    private long time;

    private final AtomicBoolean running = new AtomicBoolean(true);

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        while (running.get()) {
            advance();
            time++;
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
            map.moveAnimal(animal, animal.move());
        }
    }

    private void feedAnimals() {
        HashMap<Plant, ArrayList<Animal>> animalsPerPlant = new HashMap<>();

        var positions = map.getAnimalsPositions();
        for (var position : positions) {
            var plant = map.plantAt(position);
            if(plant != null && !map.animalsAt(position).isEmpty()) {
                animalsPerPlant.get(plant).addAll(map.animalsAt(position));
            }
        }

        for(var plant : animalsPerPlant.keySet()) {
            var strongest = getStrongest(animalsPerPlant.get(plant));
            strongest.getLast().addEnergy(plant.getEnergyMultiplier() * config.energyFromPlant());
            map.removePlant(plant);
        }
    }

    private void reproduceAnimals() {
        Random rand = new Random();
        var positions = map.getAnimalsPositions();
        for (var position : positions) {
            if(map.animalsAt(position).size() < 2) {
                continue;
            }
            var strongest = getStrongest(map.animalsAt(position));
            var parent1 = strongest.getLast();
            var parent2 = strongest.get(strongest.size() - 2);
            var genotype = genotypeCreator.mixAnimals(parent1, parent2);
            var child = new Animal(position, MapDirection.createRandomMapDirection(), genotype, config.initialAnimalEnergy(), time);
            parent1.addChild(child);
            parent2.addChild(child);
            animals.add(child);
            map.addAnimal(child);
        }
    }

    private void growPlants() {
        plantCreator.createPlants(map);
    }

    private static Comparator<Animal> getAnimalComparator() {
        return Comparator.comparing(Animal::getEnergy).thenComparing((Animal a) -> -a.getBirthDate()).thenComparing(Animal::countDescendants);
    }

    private ArrayList<Animal> getStrongest(Collection<Animal> candidates) {
        ArrayList<Animal> strongest = new ArrayList<Animal>(candidates);
        strongest.sort(getAnimalComparator());
        int first = 0;
        var cmp = getAnimalComparator();
        for(int i = 1; i < strongest.size(); i++) {
            if(cmp.compare(strongest.get(i), strongest.get(i - 1)) != 0) {
                Collections.shuffle(strongest.subList(first, i));
                first = i;
            }
        }
        Collections.shuffle(strongest.subList(first, strongest.size()));
        return strongest;
    }

}
