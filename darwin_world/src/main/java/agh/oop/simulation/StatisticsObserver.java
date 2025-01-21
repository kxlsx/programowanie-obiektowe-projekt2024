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
    private final Boundary mapBounds;
    private final Boundary plantsPreferredRegion;

    public StatisticsObserver(Boundary mapBounds, Boundary plantsPreferredRegion) {
        aliveAnimals = new HashSet<Animal>();
        deadAnimals = new HashSet<Animal>();
        plants = new HashSet<>();

        this.mapBounds = mapBounds;

        this.plantsPreferredRegion = plantsPreferredRegion;
    }

    public synchronized Collection<Vector2d> getMostPopularGenotypePositions() {
        ArrayList<Vector2d> positions = new ArrayList<>();
        HashSet<Animal> visited = new HashSet<>();
        Stack<Animal> stack = new Stack<>();

        var start = this.animalWithMostDescendants();
        visited.add(start);
        stack.push(start);
        while(!stack.empty()) {
            Animal v = stack.pop();
            if(!v.isDead()) {
                positions.add(v.getPosition());
            }

            for(Animal child : v.getChildren()) {
                if(!visited.contains(child)) {
                    visited.add(child);
                    stack.push(child);
                }
            }
        }
        return positions;
    }

    public synchronized Boundary getPlantsPreferredRegion() {
        return plantsPreferredRegion;
    }

    @Override
    public synchronized void onAnimalAdd(Animal animal) {
        deadAnimals.remove(animal);
        aliveAnimals.add(animal);
    }

    @Override
    public synchronized void onAnimalRemove(Animal animal) {
        aliveAnimals.remove(animal);
        deadAnimals.add(animal);
    }

    @Override
    public synchronized void onPlantAdd(Plant plant) {
        plants.add(plant);
    }

    @Override
    public synchronized void onPlantRemove(Plant plant) {
        plants.remove(plant);
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
        Set<Vector2d> usedPositions = new HashSet<Vector2d>();

        aliveAnimals.forEach(animal -> usedPositions.add(animal.getPosition()));
        plants.forEach(plant -> usedPositions.addAll(plant.getBounds().containedVectors()));

        for(Vector2d pos : mapBounds.containedVectors()) {
            if(!usedPositions.contains(pos)) counter++;
        }
        return counter;
    }

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
