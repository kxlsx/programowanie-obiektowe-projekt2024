package agh.oop.presenter;


import agh.oop.model.Shape;
import agh.oop.model.SimulationProgressListener;
import agh.oop.simulation.Simulation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SimulationPresenter implements SimulationProgressListener {
    public GridPane mapGrid;
    private Simulation simulation;

    public void initialize(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawMap() {
        clearGrid();
        final int CELL_WIDTH = 50;
        final int CELL_HEIGHT = 50;

        var map = simulation.getMap();

        int columns = map.getBounds().width();
        int rows = map.getBounds().height();

        for(int y = 0; y < rows; y++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for(int x = 0; x < columns; x++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for(var position : map.getBounds().containedVectors()) {
            var elements = map.worldElementsAt(position);
            for(var element : elements) {
                drawShape(element.getShape(), position.getX(), position.getY());
            }
        }
    }

    private void drawShape(Shape shape, int x, int y) {
        switch (shape.getType()) {
            case CIRCLE -> drawCircle(shape.getColor(), shape.getAlpha(), x, y);
            case SQUARE -> drawRect(shape.getColor(), shape.getAlpha(), x, y);
        }
    }

    private void drawRect(Color color, double alpha, int x, int y) {
        var rectangle = new Rectangle(50, 50);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);
        rectangle.setOpacity(alpha);
        mapGrid.add(rectangle, x, y);
        GridPane.setHalignment(rectangle, HPos.CENTER);
    }

    private void drawCircle(Color color, double alpha, int x, int y) {
        var circle = new Circle(25);
        circle.setFill(color);
        circle.setOpacity(alpha);
        mapGrid.add(circle, x, y);
        GridPane.setHalignment(circle, HPos.CENTER);
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void afterAdvance() {
        Platform.runLater(this::drawMap);
    }

    public void onPause(ActionEvent actionEvent) {
        simulation.pause();
    }

    public void onUnpause(ActionEvent actionEvent) {
        simulation.unpause();
    }
}