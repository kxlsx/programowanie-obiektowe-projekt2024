package agh.oop.presenter;


import agh.oop.model.Boundary;
import agh.oop.model.animal.Animal;
import agh.oop.simulation.AnimalTracker;
import agh.oop.simulation.SimulationProgressListener;
import agh.oop.simulation.Simulation;
import agh.oop.simulation.StatisticsObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class SimulationPresenter implements SimulationProgressListener {
    public GridPane mapGrid;
    public Label numberOfAnimals;
    public Label numberOfPlants;
    public Label numberOfFreeCells;
    public Label mostPopularGenotype;
    public Label averageEnergy;
    public Label averageLifespan;
    public Label averageChildrenCount;
    public Label deathDay;
    public Label daysAlive;
    public Label numberOfDescendants;
    public Label numberOfChildren;
    public Label plantsConsumed;
    public Label energy;
    public Label nextMove;
    public Label genotype;
    private Simulation simulation;
    private StatisticsObserver statisticsObserver;
    private MapView mapView;
    private StatisticsView statisticsView;
    private AnimalFateView animalFateView;

    public void initialize(
            Simulation simulation,
            StatisticsObserver statisticsObserver,
            AnimalTracker animalTracker
    ) {
        this.simulation = simulation;
        this.statisticsObserver = statisticsObserver;

        statisticsView = new StatisticsView(
                numberOfAnimals,
                numberOfPlants,
                numberOfFreeCells,
                mostPopularGenotype,
                averageEnergy,
                averageLifespan,
                averageChildrenCount,
                statisticsObserver
        );

        animalFateView = new AnimalFateView(
                genotype,
                nextMove,
                energy,
                plantsConsumed,
                numberOfChildren,
                numberOfDescendants,
                daysAlive,
                deathDay,
                animalTracker
        );

        mapView = new MapView(mapGrid, simulation.getMap(), animalFateView);

        animalFateView.setMapView(mapView);
    }

    public void refresh(Boundary mapBoundary, ArrayList<MapViewDrawable> toDraw) {
        statisticsView.updateUi();
        animalFateView.updateUi();
        mapView.setToDraw(mapBoundary, toDraw);
        mapView.updateUi();
    }

    @Override
    public void afterAdvance() {
        ArrayList<MapViewDrawable> toDraw = new ArrayList<>();
        for(var pos : simulation.getMap().getBounds().containedVectors()) {
            for(var element : simulation.getMap().worldElementsAt(pos)) {
                toDraw.add(new MapViewDrawable(element.getShape(), pos.deepCopy(), element));
            }
        }
        var boundary = simulation.getMap().getBounds().deepCopy();
        Platform.runLater(() -> refresh(boundary, toDraw) );
    }

    public void onPause(ActionEvent actionEvent) {
        simulation.pause();
    }

    public void onUnpause(ActionEvent actionEvent) {
        simulation.unpause();
    }

    public void onHighlightMostPopularGenotype(ActionEvent actionEvent) {
        mapView.setHighlightedCells(statisticsObserver.getMostPopularGenotypePositions());
        mapView.updateUi();
    }

    public void onHighlightPlantsPreferredRegion(ActionEvent actionEvent) {
        mapView.highlightPlantsPreferredRegion(statisticsObserver.getPlantsPreferredRegion());
        mapView.updateUi();
    }

    @Override
    public void animalAte(Animal animal) { }

    @Override
    public void animalDied(Animal animal, long date) { }
}