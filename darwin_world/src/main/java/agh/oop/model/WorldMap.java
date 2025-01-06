package agh.oop.model;

import agh.oop.model.exception.InvalidAnimalPositionException;
import agh.oop.model.exception.InvalidPlantPositionException;

import java.util.*;

public class WorldMap implements MoveValidator{
    private final Boundary bounds;
    private final Map<Vector2d, Set<Animal>> animals;
    private final Map<Vector2d, Plant> plants;

    public WorldMap(Boundary bounds) {
        this.bounds = bounds;
        animals = new HashMap<>();
        plants = new HashMap<>();
    }

    public Boundary getBounds() {
        return bounds;
    }

    public Set<WorldElement> objectsAt(Vector2d position) {
        Set<WorldElement> objs = new HashSet<>();

        Set<Animal> animalsSet = animalsAt(position);
        if(animalsSet != null) {
            objs.addAll(animalsSet);
        }
        Plant plant = plantAt(position);
        if(plant != null) {
            objs.add(plant);
        }
        return objs;
    }

    public Set<Vector2d> getAnimalsPositions() {
        return animals.keySet();
    }

    public Set<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    public void removeAnimal(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
    }

    public void addAnimal(Animal animal) throws InvalidAnimalPositionException{
        if(!bounds.contains(animal.getPosition())) {
            throw new InvalidAnimalPositionException("Animal position: " + animal.getPosition() + " is out of bounds");
        }

        animals.computeIfAbsent(
                animal.getPosition(),
                _ -> new HashSet<>()
        ).add(animal);
    }

    public void moveAnimal(Animal animal) {
        removeAnimal(animal);
        animal.move(this);
        addAnimal(animal);
    }

    public Plant plantAt(Vector2d position) {
        return plants.get(position);
    }

    public void addPlant(Plant plant) throws InvalidPlantPositionException{
        for(Vector2d position : plant.getBounds().containedVectors()) {
            if(!bounds.contains(position)) {
                throw new InvalidPlantPositionException("Plant position: " + position + "is out of bounds");
            }
            if(plantAt(position) != null) {
                throw new InvalidPlantPositionException("Another plant already occupies " + position);
            }
        }
        plant.getBounds().containedVectors().forEach(v -> plants.put(v, plant));
    }

    public void removePlant(Plant plant) {
        plant.getBounds().containedVectors().forEach(v -> plants.remove(v));
    }

    @Override
    public Vector2d correctPosition(Vector2d position) {
        int x = position.getX();
        int y = position.getY();

        // prevent from exiting bounds vertically
        if(bounds.isOobDown(y)) {
            y = bounds.getLowerLeft().getY();
        } else if (bounds.isOobUp(y)) {
            y = bounds.getUpperRight().getY();
        }

        // loop back around if exiting horizontally
        if(bounds.isOobLeft(x)) {
            x = bounds.getUpperRight().getX();
        } else if (bounds.isOobRight(x)) {
            x = bounds.getLowerLeft().getX();
        }

        return new Vector2d(x, y);
    }
}
