package agh.oop.simulation;

import agh.oop.model.Boundary;
import agh.oop.model.MapChangeListener;
import agh.oop.model.Vector2d;
import agh.oop.model.WorldMap;
import agh.oop.model.animal.*;
import agh.oop.model.plant.PlantCreator;
import agh.oop.model.plant.PlantCreatorBountifulHarvest;
import agh.oop.model.plant.PlantCreatorEquator;
import agh.oop.simulation.config.MutationMode;
import agh.oop.simulation.config.PlantGrowthMode;
import agh.oop.simulation.config.SimulationConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SimulationFactory {
    /**
     * Create a new Simulation based on the passed SimulationConfig
     * @param config the config to parse.
     * @param mapObservers a list of observers for the created map
     * @return a new Simulation.
     */
    public static Simulation createFromConfig(SimulationConfiguration config, List<MapChangeListener> mapObservers) {
        Boundary mapBoundary = new Boundary(new Vector2d(0, 0), config.mapSize());
        WorldMap map = new WorldMap(mapBoundary);
        ArrayList<Animal> animals = new ArrayList<>();

        mapObservers.forEach(ob -> map.addObserver(ob));

        GenotypeCreator genotypeCreator = switch (config.mutationMode()) {
            case MutationMode.FULL_RANDOM -> new GenotypeCreatorFullRandom(config.genomeLength());
            case MutationMode.INCREMENTAL -> new GenotypeCreatorIncremental(config.genomeLength());
        };

        AnimalCreator animalCreator = new AnimalCreatorNormal(genotypeCreator, config.reproductionCost(), config.initialAnimalEnergy());

        AnimalComparator animalComparator = new AnimalComparatorMaxEnergy();

        PlantCreator plantCreator = switch (config.plantGrowthMode()) {
            case PlantGrowthMode.EQUATOR -> new PlantCreatorEquator(config.plantGrowthPerDay(), mapBoundary);
            case PlantGrowthMode.BOUNTIFUL_HARVEST ->
                    new PlantCreatorBountifulHarvest(
                            config.plantGrowthPerDay(),
                            new Boundary(new Vector2d(0, 0), new Vector2d(3, 3)), // TODO
                            mapBoundary
                    );
        };

        return new Simulation(
                map, animals, animalComparator, animalCreator, plantCreator, config.energyFromPlant(), config.reproductionEnergyThreshold(),
                config.initialNumberOfAnimals(), config.initialNumberOfPlants()
        );
    }
}
