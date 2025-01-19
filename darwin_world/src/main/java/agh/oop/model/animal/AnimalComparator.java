package agh.oop.model.animal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Interface for an object comparing Animals in some way.
 */
public interface AnimalComparator {
    int compare(Animal a1, Animal a2);
    ArrayList<Animal> getStrongest(Collection<Animal> candidates);
}
