package agh.oop.model.animal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * An object comparing Animals by their energy values.
 */
public class AnimalComparatorMaxEnergy implements AnimalComparator{
    private final Comparator<Animal> cmp;

    /**
     * Initialize a new AnimalComparatorMaxEnergy
     */
    public AnimalComparatorMaxEnergy() {
        cmp = Comparator.comparing(Animal::getEnergy).thenComparing((Animal a) -> -a.getBirthDate()).thenComparing(Animal::countDescendants);
    }

    /**
     * Compare two animals (the one with
     * a bigger energy value is greater)
     * @param a1 animal 1
     * @param a2 animal 2
     * @return a positive value if a1 > a2,
     * negative if a1 < a2,
     * zero if a1 == a2.
     */
    public int compare(Animal a1, Animal a2) {
        return cmp.compare(a1, a2);
    }

    /**
     * Get an Array of the maximal Animals in the passed
     * collection.
     * @param candidates a collection of animals
     * @return A list of Animals with (the same) the max energy value.
     */
    public ArrayList<Animal> getStrongest(Collection<Animal> candidates) {
        ArrayList<Animal> strongest = new ArrayList<Animal>(candidates);
        strongest.sort(cmp);
        int first = 0;
        for(int i = 1; i < strongest.size(); i++) {
            if(cmp.compare(strongest.get(i), strongest.get(i - 1)) != 0) {
                Collections.shuffle(strongest.subList(first, i));
                first = i;
            }
        }
        Collections.shuffle(strongest.subList(first, strongest.size()));
        return strongest;
    }
}
