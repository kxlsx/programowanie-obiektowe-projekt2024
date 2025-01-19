package agh.oop.model;

import agh.oop.model.animal.Animal;
import agh.oop.model.plant.Plant;

public interface MapChangeListener {
    void onAnimalAdd(Animal animal);
    void onAnimalRemove(Animal animal);
    void onPlantAdd(Plant plant);
    void onPlantRemove(Plant plant);
}
