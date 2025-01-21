package agh.oop.simulation;

import agh.oop.model.animal.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalTracker implements SimulationProgressListener{
    private final Map<Animal, Integer> animalEatCount;
    private final Map<Animal, Long> animalDiedDate;
    private Animal trackedAnimal;

    public AnimalTracker() {
        animalEatCount = new HashMap<>();
        animalDiedDate = new HashMap<>();
    }

    public synchronized void setTrackedAnimal(Animal animal) {
        trackedAnimal = animal;
    }

    public synchronized AnimalFateStatistics getTrackedAnimalStatistics() {
        if(trackedAnimal == null) {
            return null;
        }
        var plantsEatenNullable =  animalEatCount.get(trackedAnimal);
        var diedDateNullable = animalDiedDate.get(trackedAnimal);

        var plantsEaten = (plantsEatenNullable == null) ? 0 : plantsEatenNullable;
        var diedDate = (diedDateNullable == null) ? 0 : diedDateNullable;

        return new AnimalFateStatistics(
                trackedAnimal.getPosition().deepCopy(),
                trackedAnimal.getGenes().deepCopy(),
                trackedAnimal.getGenes().getNextValue(),
                trackedAnimal.getEnergy(),
                plantsEaten, // TODO
                trackedAnimal.getChildren().size(),
                trackedAnimal.countDescendants(),
                trackedAnimal.getBirthDate(),
                diedDate
        );
    }

    public void animalAte(Animal animal) {
        animalEatCount.putIfAbsent(animal, 0);

        animalEatCount.put(
                animal,
                animalEatCount.get(animal) + 1
        );
    }

    public void animalDied(Animal animal, long time) {
        animalDiedDate.put(animal, time);
    }

    public void afterAdvance() { }
}
