package agh.oop.model.exception;

import agh.oop.model.Vector2d;

public class PlantOverlapException extends RuntimeException {
    public PlantOverlapException(Vector2d position) {
        super("Plants overlap at " + position);
    }
}
