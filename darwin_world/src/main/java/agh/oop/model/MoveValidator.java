package agh.oop.model;

import javafx.util.Pair;

/**
 * Interface used for correcting out of bounds moves.
 */
public interface MoveValidator {
    /**
     * Returns a new position if the passed one is incorrect.
     * @param position position to check.
     * @return new position (can be the same as the passed one).
     */
    Pair<Vector2d, MapDirection> correctHeading(Vector2d position, MapDirection direction);
}
