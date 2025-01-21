package agh.oop.simulation;

import agh.oop.model.animal.Animal;
import agh.oop.model.animal.Genotype;

public record SimulationStatistics(int aliveAnimalCount, int deadAnimalCount, int plantCount, double averageEnergy, double averageDeadLifespan, double averageAliveChildCount, int freeCellsCount, Genotype mostPopularGenotype) {
}
