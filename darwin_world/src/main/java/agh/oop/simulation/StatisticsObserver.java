package agh.oop.simulation;

import agh.oop.model.Boundary;
import agh.oop.model.MapChangeListener;
import agh.oop.model.Vector2d;
import agh.oop.model.animal.Animal;
import agh.oop.model.plant.Plant;

import java.util.*;


public class StatisticsObserver implements MapChangeListener {
    private final Set<Animal> aliveAnimals;
    private final Set<Animal> deadAnimals;

    private final Set<Plant> plants;
    private final Map<Vector2d, Integer> objectsOnCell;
    private Animal trackedAnimal;
    private final Boundary plantsPreferredRegion;

    public StatisticsObserver(Boundary mapBounds, Boundary plantsPreferredRegion) {
        aliveAnimals = new HashSet<Animal>();
        deadAnimals = new HashSet<Animal>();
        plants = new HashSet<>();

        objectsOnCell = new HashMap<>();
        mapBounds.containedVectors().forEach(
                v -> objectsOnCell.put(v, 0)
        );

        this.plantsPreferredRegion = plantsPreferredRegion;
    }

    public synchronized void setTrackedAnimal(Animal animal) {
        trackedAnimal = animal;
    }

    public synchronized Collection<Vector2d> getMostPopularGenotypePositions() {
        // TODO
        return List.of();
    }

    public synchronized Boundary getPlantsPreferredRegion() {
        return plantsPreferredRegion;
    }

    public synchronized AnimalFateStatistics getTrackedAnimalStatistics() {
        if(trackedAnimal == null) {
            return null;
        }
        return new AnimalFateStatistics(
                trackedAnimal.getPosition().deepCopy(),
                trackedAnimal.getGenes().deepCopy(),
                trackedAnimal.getGenes().getNextValue(),
                trackedAnimal.getEnergy(),
                0, // TODO
                trackedAnimal.getChildren().size(),
                trackedAnimal.countDescendants(),
                trackedAnimal.getBirthDate(),
                0); // TODO
    }

    @Override
    public synchronized void onAnimalAdd(Animal animal) {
        if(deadAnimals.contains(animal)) {
            deadAnimals.remove(animal);
        }

        aliveAnimals.add(animal);

        objectAdded(animal.getPosition());
    }

    @Override
    public synchronized void onAnimalRemove(Animal animal) {
        aliveAnimals.remove(animal);
        deadAnimals.add(animal);

        objectRemoved(animal.getPosition());
    }

    @Override
    public synchronized void onPlantAdd(Plant plant) {
        plants.add(plant);

        for(Vector2d pos : plant.getBounds().containedVectors()) {
            objectAdded(pos);
        }
    }

    @Override
    public synchronized void onPlantRemove(Plant plant) {
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

    public synchronized int aliveAnimalCount() {
        return aliveAnimals.size();
    }

    public synchronized int deadAnimalCount() {
        return deadAnimals.size();
    }

    public synchronized int plantCount() {
        return plants.size();
    }

    public synchronized double averageEnergy() {
        double avg = 0;
        for(Animal a : aliveAnimals) {
            avg += a.getEnergy();
        }
        return avg / aliveAnimalCount();
    }

    public synchronized double averageDeadLifespan() {
        double avg = 0;
        for(Animal a : deadAnimals) {
            avg += a.getBirthDate();
        }
        return avg / deadAnimalCount();
    }

    public synchronized double averageAliveChildCount() {
        double avg = 0;
        for(Animal a : aliveAnimals) {
            avg += a.getChildren().size();
        }
        return avg / aliveAnimalCount();
    }

    public synchronized int freeCellsCount() {
        int counter = 0;
        for(int c : objectsOnCell.values()) {
            if(c == 0) counter += 1;
        }
        return counter;
    }

    // FIXME: temporary hack, may be slow
    public synchronized Animal animalWithMostDescendants() {
        Optional<Animal> mx1 = aliveAnimals.stream().reduce(
                (a, b) ->
                        (a.countDescendants() > b.countDescendants()) ? a : b
        );
        Optional<Animal> mx2 = deadAnimals.stream().reduce(
                (a, b) ->
                        (a.countDescendants() > b.countDescendants()) ? a : b
        );

        if(mx1.isEmpty()){
            return mx2.get();
        }
        if(mx2.isEmpty()) {
            return mx1.get();
        }

        Animal a = mx1.get();
        Animal b = mx2.get();
        return (a.countDescendants() > b.countDescendants()) ? a : b;
    }
}

/*
TODO
Add statistics to be returned in record
Add better pause thing
Add frame time to configuration
Fix creating plants at start
Bountiful harvest region
 */