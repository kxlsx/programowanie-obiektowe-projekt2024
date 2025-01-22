package agh.oop.simulation;

import agh.oop.model.animal.Animal;

public interface SimulationProgressListener {
    /**
     * method that should be called after every simulation day.
     */
    void afterAdvance();

    /**
     * method that should be called each time animal dies.
     * @param animal animal that died.
     * @param date current simulation day.
     */
    void animalDied(Animal animal, long date);
    void animalAte(Animal animal);
}
