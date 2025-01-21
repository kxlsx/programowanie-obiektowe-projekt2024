package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.WorldMap;

public interface PlantCreator {
    void createPlants(WorldMap worldMap);
    void createPlants(WorldMap worldMap, int maxPlants);
    Boundary getPreferredRegion();
}
