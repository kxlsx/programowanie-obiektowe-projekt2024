package agh.oop.simulation;

import agh.oop.model.*;
import agh.oop.model.animal.Animal;
import agh.oop.model.plant.Plant;

import java.util.*;

public class StatisticsObserver implements MapChangeListener {
    private final Set<Animal> aliveAnimals;
    private final Set<Animal> deadAnimals;

    private final Set<Plant> plants;
    private final Map<Vector2d, Integer> objectsOnCell;

    public StatisticsObserver(Boundary mapBounds) {
        aliveAnimals = new HashSet<Animal>();
        deadAnimals = new HashSet<Animal>();
        plants = new HashSet<>();

        objectsOnCell = new HashMap<>();
        mapBounds.containedVectors().forEach(
                v -> objectsOnCell.put(v, 0)
        );

    }

    @Override
    public void onAnimalAdd(Animal animal) {
        if(deadAnimals.contains(animal)) {
            deadAnimals.remove(animal);
        }

        aliveAnimals.add(animal);

        objectAdded(animal.getPosition());
    }

    @Override
    public void onAnimalRemove(Animal animal) {
        aliveAnimals.remove(animal);
        deadAnimals.add(animal);

        objectRemoved(animal.getPosition());
    }

    @Override
    public void onPlantAdd(Plant plant) {
        plants.add(plant);

        for(Vector2d pos : plant.getBounds().containedVectors()) {
            objectAdded(pos);
        }
    }

    @Override
    public void onPlantRemove(Plant plant) {
        plants.remove(plant);

        for(Vector2d pos : plant.getBounds().containedVectors()) {
            objectRemoved(pos);
        }
    }

    //FIXME:
    private void objectAdded(Vector2d position) {
        objectsOnCell.merge(position, 1, (a, b) -> a + b);
    }

    private void objectRemoved(Vector2d position) {
        objectsOnCell.merge(position, -1, (a, b) -> a + b);
    }

    public int aliveAnimalCount() {
        return aliveAnimals.size();
    }

    public int deadAnimalCount() {
        return deadAnimals.size();
    }

    public int plantCount() {
        return plants.size();
    }

    public double averageEnergy() {
        double avg = 0;
        for(Animal a : aliveAnimals) {
            avg += a.getEnergy();
        }
        return avg / aliveAnimalCount();
    }

    public double averageDeadLifespan() {
        double avg = 0;
        for(Animal a : deadAnimals) {
            avg += a.getBirthDate();
        }
        return avg / deadAnimalCount();
    }

    public double averageAliveChildCount() {
        double avg = 0;
        for(Animal a : aliveAnimals) {
            avg += a.getChildren().size();
        }
        return avg / aliveAnimalCount();
    }

    public int freeCellsCount() {
        int counter = 0;
        for(int c : objectsOnCell.values()) {
            if(c == 0) counter += 1;
        }
        return counter;
    }

    public Animal animalWithMostDescendants() {
        // TODO: this
        return null;
    }
}
