package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.WorldMap;

public interface PlantCreator {
    /**
     * Creates plants at map.
     *
     * @param worldMap map where plants should be created.
     */
    void createPlants(WorldMap worldMap);

    /**
     * Creates plants at map, with specified number of them.
     *
     * @param worldMap map where plants should be created.
     */
    void createPlants(WorldMap worldMap, int maxPlants);

    /**
     *
     * @return boundary of a region that grows more/better plants.
     */
    Boundary getPreferredRegion();
}
