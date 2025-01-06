package agh.oop.model;

/**
 * Interface used for correcting out of bounds moves.
 */
public interface MoveValidator {
    /**
     * Returns a new position if the passed one is incorrect.
     * @param position position to check.
     * @return new position (can be the same as the passed one).
     */
    Vector2d correctPosition(Vector2d position);
}
