package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldMap;

import java.util.Collections;

public class PlantCreatorBountifulHarvest implements PlantCreator {
    private final float plantGrowthProbability;
    private final Boundary bigPlantsRegion;

    /**
     * Create an PlantCreatorBountifulHarvest with the passed parameters.
     * @param plantsPerDay expected number of plants to grow per day.
     * @param bigPlantsRegion area where big plants spawn (it is enough if upper-left corner fits).
     * @param mapRegion map boundary.
     */
    public PlantCreatorBountifulHarvest(int plantsPerDay, Boundary bigPlantsRegion, Boundary mapRegion) {
        this.plantGrowthProbability = (float) plantsPerDay / mapRegion.area();
        this.bigPlantsRegion = bigPlantsRegion;
    }

    private boolean diceGrow(WorldMap worldMap, Vector2d position) {
        return Math.random() <= plantGrowthProbability && worldMap.plantAt(position) == null;
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        createPlants(worldMap, worldMap.getBounds().area());
    }

    @Override
    public void createPlants(WorldMap worldMap, int maxPlants) {
        int grownCount = 0;
        var positions = worldMap.getBounds().containedVectors();
        Collections.shuffle(positions);
        for(var position : positions) {
            if(grownCount == maxPlants) break;
            if(diceGrow(worldMap, position)) {
                var bigPlantBoundary = new Boundary(position, position.add(new Vector2d(1, 1)));
                if(bigPlantsRegion.contains(position) && !worldMap.plantExists(bigPlantBoundary)) {
                    worldMap.addPlant(new Plant(4, bigPlantBoundary));
                }
                else {
                    worldMap.addPlant(new Plant(1, position));
                }
                grownCount++;
            }
        }
    }

    @Override
    public Boundary getPreferredRegion() {
        return bigPlantsRegion;
    }
}
