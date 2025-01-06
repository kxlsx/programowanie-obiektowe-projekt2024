package agh.oop.model.exception;

import agh.oop.model.Vector2d;

public class OutOfBoundsException extends RuntimeException {
    public OutOfBoundsException(Vector2d position) {
        super(position + " is out of bounds.");
    }
}
