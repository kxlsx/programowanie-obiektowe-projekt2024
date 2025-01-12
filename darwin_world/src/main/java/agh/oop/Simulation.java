package agh.oop;

import agh.oop.model.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final ArrayList<Animal> animals;
    private final SimulationConfiguration config;
    private final GenotypeCreator genotypeCreator;
    private final PlantCreator plantCreator;
    private long time;

    private final AtomicBoolean running = new AtomicBoolean(true);


    public Simulation(SimulationConfiguration config) {
        this.config = config;
        var mapBoundary = new Boundary(new Vector2d(0, 0), config.mapSize());
        map = new WorldMap(mapBoundary);
        animals = new ArrayList<>();

        genotypeCreator = switch (config.mutationMode()) {
            case MutationMode.FULL_RANDOM -> new GenotypeCreatorFullRandom(config.genomeLength());
            case MutationMode.INCREMENTAL -> new GenotypeCreatorIncremental(config.genomeLength());
        };

        plantCreator = switch (config.plantGrowthMode()) {
            case PlantGrowthMode.EQUATOR -> new PlantCreatorEquator(config.plantGrowthPerDay(), mapBoundary);
            case PlantGrowthMode.BOUNTIFUL_HARVEST ->
                    new PlantCreatorBountifulHarvest(
                            config.plantGrowthPerDay(),
                            new Boundary(new Vector2d(0, 0), new Vector2d(3, 3)), // TODO
                            mapBoundary
                    );
        };

        time = 0;


        // create initial animals
        for(int i = 0; i < config.initialNumberOfAnimals(); i++) {
            var animal = new Animal(new Vector2d(0, 0), MapDirection.createRandomMapDirection(), genotypeCreator.create(), config.initialAnimalEnergy(), time); // TODO random position
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
            if(plant != null && !map.animalsAt(position).isEmpty()) {
                animalsPerPlant.putIfAbsent(plant, new ArrayList<>());
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

            parent1.loseEnergy(config.reproductionCost());
            parent2.loseEnergy(config.reproductionCost());
            var child = new Animal(position, MapDirection.createRandomMapDirection(), genotype, config.reproductionCost() * 2, time);
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
