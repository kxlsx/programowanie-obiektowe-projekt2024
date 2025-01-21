package agh.oop.simulation;

import agh.oop.model.Vector2d;
import agh.oop.model.animal.Genotype;

public record AnimalFateStatistics(
        Vector2d position,
        Genotype genotype,
        int nextMove,
        int energy,
        int plantsEaten,
        int numberOfChildren,
        int numberOfDescendants,
        long age,
        long deathDay) {}
