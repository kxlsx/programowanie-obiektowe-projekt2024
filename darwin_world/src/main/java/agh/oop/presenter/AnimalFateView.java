package agh.oop.presenter;

import agh.oop.model.WorldElement;
import agh.oop.model.animal.Animal;
import agh.oop.simulation.AnimalTracker;
import agh.oop.simulation.StatisticsObserver;
import javafx.scene.control.Label;

import java.util.List;

public class AnimalFateView implements WorldElementClickedListener {
    private final Label genotype;
    private final Label nextMove;
    private final Label energy;
    private final Label plantsConsumed;
    private final Label numberOfChildren;
    private final Label numberOfDescendants;
    private final Label daysAlive;
    private final Label deathDay;
    private final AnimalTracker animalTracker;
    private MapView mapView;

    public AnimalFateView(
            Label genotype,
            Label nextMove,
            Label energy,
            Label plantsConsumed,
            Label numberOfChildren,
            Label numberOfDescendants,
            Label daysAlive,
            Label deathDay,
            AnimalTracker animalTracker
    ) {
        this.genotype = genotype;
        this.nextMove = nextMove;
        this.energy = energy;
        this.plantsConsumed = plantsConsumed;
        this.numberOfChildren = numberOfChildren;
        this.numberOfDescendants = numberOfDescendants;
        this.daysAlive = daysAlive;
        this.deathDay = deathDay;
        this.animalTracker = animalTracker;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public void updateUi() {
        if(animalTracker.getTrackedAnimalStatistics() != null) {
            var statistics = animalTracker.getTrackedAnimalStatistics();
            mapView.setHighlightedCells(List.of(statistics.position()));
            genotype.setText(statistics.genotype().toString());
            nextMove.setText(String.valueOf(statistics.nextMove()));
            energy.setText(String.valueOf(statistics.energy()));
            plantsConsumed.setText(String.valueOf(statistics.plantsEaten()));
            numberOfChildren.setText(String.valueOf(statistics.numberOfChildren()));
            numberOfDescendants.setText(String.valueOf(statistics.numberOfDescendants()));
            daysAlive.setText(String.valueOf(statistics.age()));
            deathDay.setText(String.valueOf(statistics.deathDay()));
        }
        else {
            mapView.setHighlightedCells(List.of());
            genotype.setText("-");
            nextMove.setText("-");
            energy.setText("-");
            plantsConsumed.setText("-");
            numberOfChildren.setText("-");
            numberOfDescendants.setText("-");
            daysAlive.setText("-");
            deathDay.setText("-");
        }
        mapView.updateUi();
    }

    @Override
    public void onElementClicked(WorldElement element) {
        if(element instanceof Animal) {
            animalTracker.setTrackedAnimal((Animal) element);
            updateUi();
        }
    }
}
