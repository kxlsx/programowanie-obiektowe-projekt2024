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
        numberOfAnimals.setText(String.valueOf(statistics.aliveAnimalCount()));
        numberOfPlants.setText(String.valueOf(statistics.plantCount()));
        numberOfFreeCells.setText(String.valueOf(statistics.freeCellsCount()));
        mostPopularGenotype.setText(String.valueOf(statistics.animalWithMostDescendants().getGenes()));
        averageEnergy.setText(String.valueOf(statistics.averageEnergy()));
        averageLifespan.setText(String.valueOf(statistics.averageDeadLifespan()));
        averageChildrenCount.setText(String.valueOf(statistics.averageAliveChildCount()));
    }
}
