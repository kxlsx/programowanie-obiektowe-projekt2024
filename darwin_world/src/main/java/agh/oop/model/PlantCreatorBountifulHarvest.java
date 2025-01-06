package agh.oop.model;

public class PlantCreatorBountifulHarvest implements PlantCreator {
    private final float plantGrowthProbability;
    //private final float bigPlantGrowthProbability;
    private final Boundary bigPlantsRegion;

    PlantCreatorBountifulHarvest(int plantsPerDay, Boundary bigPlantsRegion, Boundary mapRegion) {
        this.plantGrowthProbability = (float) plantsPerDay / mapRegion.area();
        this.bigPlantsRegion = bigPlantsRegion;
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        for(int x = 0; x < bigPlantsRegion.width(); x++) {
            for(int y = 0; y < bigPlantsRegion.height(); y++) {
                if(Math.random() <= plantGrowthProbability) {
                    worldMap.addPlant(new Plant(1, new Vector2d(x, y)));
                }
            }
        }
    }
}
