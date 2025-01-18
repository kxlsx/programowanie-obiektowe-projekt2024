package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldMap;

public class PlantCreatorBountifulHarvest implements PlantCreator {
    private final float plantGrowthProbability;
    private final Boundary bigPlantsRegion;

    public PlantCreatorBountifulHarvest(int plantsPerDay, Boundary bigPlantsRegion, Boundary mapRegion) {
        this.plantGrowthProbability = (float) plantsPerDay / mapRegion.area();
        this.bigPlantsRegion = bigPlantsRegion;
    }

    private boolean diceGrow(WorldMap worldMap, Vector2d position) {
        return Math.random() <= plantGrowthProbability && worldMap.plantAt(position) != null;
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        for(var position : worldMap.getBounds().containedVectors()) {
            if(diceGrow(worldMap, position)) {
                if(bigPlantsRegion.contains(position)) {
                    worldMap.addPlant(new Plant(4, new Boundary(position, position.add(new Vector2d(1, 1)))));
                }
                else {
                    worldMap.addPlant(new Plant(1, position));
                }
            }
        }
    }
}
