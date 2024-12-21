package agh.oop.model;

public class PlantCreatorEquator implements PlantCreator {
    private final double baseGrowthProbability;
    private final Boundary mapRegion;
    private final Boundary equator;


    PlantCreatorEquator(int plantsPerDay, Boundary mapRegion) {
        this.mapRegion = mapRegion;
        equator = new Boundary(
                new Vector2d(mapRegion.getLowerLeft().getX(), mapRegion.getLowerLeft().getY() + (int)(mapRegion.height() * 0.2)),
                new Vector2d(mapRegion.getUpperRight().getX(), mapRegion.getLowerLeft().getY() + (int)(mapRegion.height() * 0.8))
        );
        final double normalizationFactor = equator.area() * 0.8 + (mapRegion.area() - equator.area()) * 0.2;
        baseGrowthProbability = normalizationFactor * plantsPerDay / mapRegion.area();
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        for(int x = 0; x < mapRegion.width(); x++) {
            for(int y = 0; y < mapRegion.height(); y++) {
                double probability = 0.0;
                if(equator.contains(x, y)) {
                    probability = baseGrowthProbability * 0.8;
                }
                else {
                    probability = baseGrowthProbability * 0.2;
                }
                if(Math.random() <= probability) {
                    worldMap.addPlant(new Plant(1, new Vector2d(x, y)));
                }
            }
        }
    }
}
