package agh.oop.simulation;

import agh.oop.model.animal.Animal;

public interface SimulationProgressListener {
    void afterAdvance();
    void animalDied(Animal animal, long date);
    void animalAte(Animal animal);
}
