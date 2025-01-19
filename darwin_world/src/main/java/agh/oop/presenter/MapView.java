package agh.oop.presenter;

import agh.oop.model.*;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class MapView {
    private final GridPane mapGrid;
    private final WorldElementClickedListener clickedListener;
    private final Set<Vector2d> highlighted;

    private Boundary mapBoundary;
    private ArrayList<MapViewDrawable> toDraw;

    public MapView(GridPane mapGrid, WorldMap worldMap, WorldElementClickedListener clickedListener) {
        this.mapGrid = mapGrid;
        this.clickedListener = clickedListener;
        highlighted = new HashSet<>();
    }

    public void setHighlightedCells(Collection<Vector2d> highlighted) {
        this.highlighted.clear();
        this.highlighted.addAll(highlighted);
    }

    public void setToDraw(Boundary mapBoundary, ArrayList<MapViewDrawable> toDraw) {
        this.mapBoundary = mapBoundary;
        this.toDraw = toDraw;
    }

    public void updateUi() {
        if(mapBoundary != null && toDraw != null) {
            drawMap();
        }
    }

    public void highlightPlantsPreferredRegion(Boundary bounds) {
        setHighlightedCells(bounds.containedVectors());
    }

    private void drawMap() {
        clearGrid();
        final int CELL_WIDTH = 50;
        final int CELL_HEIGHT = 50;

        int columns = mapBoundary.width();
        int rows = mapBoundary.height();

        for(int y = 0; y < rows; y++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
        for(int x = 0; x < columns; x++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for(var drawable : toDraw) {
            drawShape(drawable.worldElement(), drawable.shape(), drawable.position().getX(), drawable.position().getY());
        }

        for(var pos : highlighted) {
            drawBorder(Color.RED, pos.getX(), pos.getY());
        }
    }

    private void drawShape(WorldElement element, Shape shape, int x, int y) {
        switch (shape.getType()) {
            case CIRCLE -> drawCircle(element, shape.getColor(), shape.getAlpha(), x, y);
            case SQUARE -> drawRect(element, shape.getColor(), shape.getAlpha(), x, y);
        }
    }

    private void drawBorder(Color color, int x, int y) {
        var rectangle = new Rectangle(50, 50);
        rectangle.setStroke(color);
        rectangle.setStrokeWidth(5.);
        rectangle.setFill(Color.TRANSPARENT);
        mapGrid.add(rectangle, x, y);
        GridPane.setHalignment(rectangle, HPos.CENTER);
    }

    private void drawRect(WorldElement element, Color color, double alpha, int x, int y) {
        var rectangle = new Rectangle(50, 50);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);
        rectangle.setOpacity(alpha);
        rectangle.setOnMouseClicked(mouseEvent -> onElementClicked(element));
        mapGrid.add(rectangle, x, y);
        GridPane.setHalignment(rectangle, HPos.CENTER);
    }

    private void drawCircle(WorldElement element, Color color, double alpha, int x, int y) {
        var circle = new Circle(25);
        circle.setFill(color);
        circle.setOpacity(alpha);
        circle.setOnMouseClicked(mouseEvent -> onElementClicked(element));
        mapGrid.add(circle, x, y);
        GridPane.setHalignment(circle, HPos.CENTER);
    }

    private void onElementClicked(WorldElement element) {
        clickedListener.onElementClicked(element);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}
