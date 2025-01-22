package agh.oop.model.animal;

import agh.oop.model.Boundary;

/**
 * Interface for an object that creates Animals
 * and reproduces them.
 */
public interface AnimalCreator {
    Animal create(long time, Boundary mapBoundary);
    Animal reproduce(Animal parent1, Animal parent2, long time);
}
