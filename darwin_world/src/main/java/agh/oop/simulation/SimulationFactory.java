package agh.oop.simulation;

import agh.oop.model.*;
import agh.oop.model.animal.*;
import agh.oop.model.plant.PlantCreator;
import agh.oop.model.plant.PlantCreatorBountifulHarvest;
import agh.oop.model.plant.PlantCreatorEquator;
import agh.oop.simulation.config.MutationMode;
import agh.oop.simulation.config.PlantGrowthMode;
import agh.oop.simulation.config.SimulationConfiguration;

import java.util.List;

public class SimulationFactory {
    /**
     * Create a new Simulation based on the passed SimulationConfig
     * @param config the config to parse.
     * @param mapObservers a list of observers for the created map
     * @return a new Simulation.
     */
    public static Simulation createFromConfig(
            SimulationConfiguration config,
            List<MapChangeListener> mapObservers,
            List<SimulationProgressListener> simulationObservers
    ) {
        Boundary mapBoundary = new Boundary(new Vector2d(0, 0), config.getMapSize());
        WorldMap map = new WorldMap(mapBoundary);

        mapObservers.forEach(ob -> map.addObserver(ob));

        GenotypeCreator genotypeCreator = switch (config.getMutationMode()) {
            case MutationMode.FULL_RANDOM -> new GenotypeCreatorFullRandom(config.getGenomeLength());
            case MutationMode.INCREMENTAL -> new GenotypeCreatorIncremental(config.getGenomeLength());
        };

        AnimalCreator animalCreator = new AnimalCreatorNormal(genotypeCreator, config.getReproductionCost(), config.getInitialAnimalEnergy());

        AnimalComparator animalComparator = new AnimalComparatorMaxEnergy();

        PlantCreator plantCreator = switch (config.getPlantGrowthMode()) {
            case PlantGrowthMode.EQUATOR -> new PlantCreatorEquator(config.getPlantGrowthPerDay(), mapBoundary);
            case PlantGrowthMode.BOUNTIFUL_HARVEST ->
                    new PlantCreatorBountifulHarvest(
                            config.getPlantGrowthPerDay(),
                            new Boundary(new Vector2d(0, 0), new Vector2d(3, 3)), // TODO
                            mapBoundary
                    );
        };

        var simulation = new Simulation(
                map, animalComparator, animalCreator, plantCreator, config.getEnergyFromPlant(), config.getReproductionEnergyThreshold(),
                config.getInitialNumberOfAnimals(), config.getInitialNumberOfPlants()
        );

        simulationObservers.forEach(simulation::addSimulationProgressListener);

        return simulation;
    }
}
