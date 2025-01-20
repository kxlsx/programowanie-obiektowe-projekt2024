package agh.oop.presenter;

import agh.oop.simulation.StatisticsObserver;
import javafx.scene.control.Label;

public class StatisticsView {
    private final Label numberOfAnimals;
    private final Label numberOfPlants;
    private final Label numberOfFreeCells;
    private final Label mostPopularGenotype;
    private final Label averageEnergy;
    private final Label averageLifespan;
    private final Label averageChildrenCount;
    private final StatisticsObserver statistics;

    public StatisticsView(Label numberOfAnimals, Label numberOfPlants, Label numberOfFreeCells, Label mostPopularGenotype, Label averageEnergy, Label averageLifespan, Label averageChildrenCount, StatisticsObserver statistics) {
        this.numberOfAnimals = numberOfAnimals;
        this.numberOfPlants = numberOfPlants;
        this.numberOfFreeCells = numberOfFreeCells;
        this.mostPopularGenotype = mostPopularGenotype;
        this.averageEnergy = averageEnergy;
        this.averageLifespan = averageLifespan;
        this.averageChildrenCount = averageChildrenCount;
        this.statistics = statistics;
    }

    void updateUi() {
        var simulationStatistics = statistics.getSimulationStatistics();
        numberOfAnimals.setText(String.valueOf(simulationStatistics.aliveAnimalCount()));
        numberOfPlants.setText(String.valueOf(simulationStatistics.plantCount()));
        numberOfFreeCells.setText(String.valueOf(simulationStatistics.freeCellsCount()));
        mostPopularGenotype.setText(simulationStatistics.mostPopularGenotype().toString());
        averageEnergy.setText(String.valueOf(simulationStatistics.averageEnergy()));
        averageLifespan.setText(String.valueOf(simulationStatistics.averageDeadLifespan()));
        averageChildrenCount.setText(String.valueOf(simulationStatistics.averageAliveChildCount()));
    }
}
