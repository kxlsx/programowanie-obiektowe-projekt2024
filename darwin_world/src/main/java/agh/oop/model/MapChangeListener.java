package agh.oop.model;

public interface MapChangeListener {
    void onAnimalAdd(Animal animal);
    void onAnimalRemove(Animal animal);
    void onPlantAdd(Plant plant);
    void onPlantRemove(Plant plant);
}
