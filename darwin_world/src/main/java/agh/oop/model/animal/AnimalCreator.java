package agh.oop.model.animal;

public interface AnimalCreator {
    Animal create(long time);
    Animal reproduce(Animal parent1, Animal parent2, long time);
}
