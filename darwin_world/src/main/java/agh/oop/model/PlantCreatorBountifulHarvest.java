package agh.oop.model;

public class PlantCreatorBountifulHarvest implements PlantCreator {
    private final float plantGrowthProbability;
    //private final float bigPlantGrowthProbability;
    private final Boundary bigPlantsRegion;

    public PlantCreatorBountifulHarvest(int plantsPerDay, Boundary bigPlantsRegion, Boundary mapRegion) {
        this.plantGrowthProbability = (float) plantsPerDay / mapRegion.area();
        this.bigPlantsRegion = bigPlantsRegion;
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        for(int x = worldMap.getBounds().getLowerLeft().getX(); x < worldMap.getBounds().getUpperRight().getX(); x++) {
            for(int y = worldMap.getBounds().getLowerLeft().getY(); y < worldMap.getBounds().getUpperRight().getY(); y++) {
                if(Math.random() <= plantGrowthProbability && worldMap.plantAt(new Vector2d(x, y)) != null) {
                    if(bigPlantsRegion.contains(new Vector2d(x, y))) {
                        worldMap.addPlant(new Plant(4, new Boundary(new Vector2d(x, y), new Vector2d(x + 1, y + 1))));
                    }
                    else {
                        worldMap.addPlant(new Plant(1, new Vector2d(x, y)));
                    }
                }
            }
        }
    }
}
