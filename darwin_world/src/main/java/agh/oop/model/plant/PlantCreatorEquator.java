package agh.oop.model.plant;

import agh.oop.model.Boundary;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldMap;

public class PlantCreatorEquator implements PlantCreator {
    private final double baseGrowthProbability;
    private final Boundary equator;


    public PlantCreatorEquator(int plantsPerDay, Boundary mapRegion) {
        equator = new Boundary(
                new Vector2d(mapRegion.getLowerLeft().getX(), mapRegion.getLowerLeft().getY() + (int)(mapRegion.height() * 0.4)),
                new Vector2d(mapRegion.getUpperRight().getX(), mapRegion.getLowerLeft().getY() + (int)(mapRegion.height() * 0.6))
        );
        final double normalizationFactor = equator.area() * 0.8 + (mapRegion.area() - equator.area()) * 0.2;
        baseGrowthProbability = normalizationFactor * plantsPerDay / mapRegion.area();
    }

    private boolean diceGrow(WorldMap worldMap, Vector2d position) {
        double probability = 0.0;
        if(equator.contains(position.getX(), position.getY())) {
            probability = baseGrowthProbability * 0.8;
        }
        else {
            probability = baseGrowthProbability * 0.2;
        }
        double tmp = Math.random();
        return Math.random() <= probability && worldMap.plantAt(new Vector2d(position.getX(), position.getY())) == null;
    }

    @Override
    public void createPlants(WorldMap worldMap) {
        for(var position : worldMap.getBounds().containedVectors()) {
            if(diceGrow(worldMap, position)) {
                worldMap.addPlant(new Plant(1, new Vector2d(position.getX(), position.getY())));
            }
        }
    }

    @Override
    public Boundary getPreferredRegion() {
        return equator;
    }
}
