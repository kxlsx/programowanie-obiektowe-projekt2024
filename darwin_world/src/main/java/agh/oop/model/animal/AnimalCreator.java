package agh.oop.model.animal;

/**
 * Interface for an object that creates Animals
 * and reproduces them.
 */
public interface AnimalCreator {
    Animal create(long time);
    Animal reproduce(Animal parent1, Animal parent2, long time);
}
