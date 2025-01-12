package agh.oop.model;

import agh.oop.model.exception.OutOfBoundsException;
import agh.oop.model.exception.PlantOverlapException;

import java.util.*;

/**
 * Object representing a 2D rectangular map
 * containing Animals and Plants.
 * Animals can occupy the same spot as other Animals and Plants.
 * Only one Plant can occupy a spot.
 * Animals can't exit the map from above or below, but
 * if they go out of bounds from left or right they loop
 * back to the opposite direction.
 */
public class WorldMap implements MoveValidator{
    private final Boundary bounds;
    private final Map<Vector2d, Set<Animal>> animals;
    private final Map<Vector2d, Plant> plants;

    private final List<MapChangeListener> observers;

    /**
     * Create a new WorldMap defined by the
     * passed rectangle.
     * @param bounds rectangle representing the boundary= of the map.
     */
    public WorldMap(Boundary bounds) {
        this.bounds = bounds;
        animals = new HashMap<>();
        plants = new HashMap<>();

        observers = List.of();
    }

    /**
     * Add observer to map.
     * @param observer the observer.
     */
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    /**
     * @return the Boundary of the map
     */
    public Boundary getBounds() {
        return bounds;
    }

    /**
     * Returns a set of WorldElements (Plants or Animals)
     * occupying the passed position.
     * @param position position to check.
     * @return a set.
     */
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

    /**
     * @return a set of every position occupied by at least one Animal.
     */
    public Set<Vector2d> getAnimalsPositions() {
        return animals.keySet();
    }

    /**
     * @param position position to check.
     * @return the set of Animals occupying the position or null if
     * there's no Animals there.
     */
    public Set<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    /**
     * Remove the Animal from the map.
     * @param animal animal to remove.
     */
    public void removeAnimal(Animal animal) {
        if(animals.get(animal.getPosition()).isEmpty()) {
            animals.remove(animal.getPosition());
          
            observers.forEach(ob -> ob.onAnimalRemove(animal));
        }
    }

    /**
     * Add a new Animal to the map starting at its position.
     * @param animal animal to add
     * @throws OutOfBoundsException thrown if the animal's position is out of bounds.
     */
    public void addAnimal(Animal animal) throws OutOfBoundsException{
        if(!bounds.contains(animal.getPosition())) {
            throw new OutOfBoundsException(animal.getPosition());
        }

        animals.computeIfAbsent(
                animal.getPosition(),
                _ -> new HashSet<>()
        ).add(animal);

        observers.forEach(ob -> ob.onAnimalAdd(animal));
    }

    /**
     * Move the passed Animal.
     * @param animal Animal to move
     */
    public void moveAnimal(Animal animal) {
        removeAnimal(animal);
        animal.move(this);
        addAnimal(animal);
    }

    /**
     * @param position position to check.
     * @return the Plant at position or null there's no Plant there.
     */
    public Plant plantAt(Vector2d position) {
        return plants.get(position);
    }

    /**
     * Add a Plant to the map.
     * A plant occupies a Boundary not a single Vector2d.
     * @param plant plant to add
     * @throws OutOfBoundsException thrown if the plant's boundary is out of bounds,
     * @throws PlantOverlapException thrown if the plant's boundary overlaps another plant's boundary.
     */
    public void addPlant(Plant plant) throws OutOfBoundsException, PlantOverlapException{
        for(Vector2d position : plant.getBounds().containedVectors()) {
            if(!bounds.contains(position)) {
                throw new OutOfBoundsException(position);
            }
            if(plantAt(position) != null) {
                throw new PlantOverlapException(position);
            }
        }
        plant.getBounds().containedVectors().forEach(v -> plants.put(v, plant));

        observers.forEach(ob -> ob.onPlantAdd(plant));
    }

    /**
     * Remove the plant from the map.
     * @param plant plant to remove.
     */
    public void removePlant(Plant plant) {
        plant.getBounds().containedVectors().forEach(v -> plants.remove(v));

        observers.forEach(ob -> ob.onPlantRemove(plant));
    }

    /**
     * Corrects the passed position so it lies inbounds.
     * If the position is above or below the rectangle,
     * it's snapped back inside.
     * If the position is to the left or to the right,
     * it loops back to the other direction.
     * For example, for map with lowerLeft at (0, 0),
     * upperRight at (5, 5)
     * and position at (-1, -1), correctPosition
     * will return (5, 0).
     *
     * @param position position to check.
     * @return a new position (or the same).
     */
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

    public void print() {
        for(int y = bounds.getLowerLeft().getY(); y <= bounds.getUpperRight().getY(); y++) {
            for(int x = bounds.getLowerLeft().getX(); x <= bounds.getUpperRight().getX(); x++) {
                var animals = animalsAt(new Vector2d(x, y));
                if(animals != null && !animals.isEmpty()) {
                    System.out.print(String.valueOf(animals.size()) + " ");
                }
                else {
                    var plant = plantAt(new Vector2d(x, y));
                    if(plant != null) {
                        System.out.print('#' + " ");
                    }
                    else {
                        System.out.print('.' + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();

    }
}
