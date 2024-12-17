package agh.oop;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation implements Runnable {
  //private WorldMap map;
  //private List<Animal> animals;
  private SimulationConfiguration config;
  //private GenotypeCreator genotypeCreator;
  private PlantCreator plantCreator;

  private final AtomicBoolean running = new AtomicBoolean(true);

  public void stop() {
    running.set(false);
  }

  @Override
  public void run() {
    while(running.get()) {
      advance();
    }
  }

  public void advance() {
    removeDeadAnimals();
    moveAnimals();
    feedAnimals();
    reproduceAnimals();
    growPlants();
  }

  private void removeDeadAnimals() {

  }

  private void moveAnimals() {

  }

  private void feedAnimals() {

  }

  private void reproduceAnimals() {

  }

  private void growPlants() {}

}
