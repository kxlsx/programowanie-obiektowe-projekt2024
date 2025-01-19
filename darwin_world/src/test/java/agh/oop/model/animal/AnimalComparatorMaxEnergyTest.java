package agh.oop.model.animal;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalComparatorMaxEnergyTest {
    @Test
    public void compare() {
        AnimalComparator cmp = new AnimalComparatorMaxEnergy();
        AnimalCreator animalCreator =  new AnimalCreatorNormal(new GenotypeCreatorIncremental(10), 10, 10);
        Animal a1 = animalCreator.create(0);
        Animal a2 = animalCreator.create(0);

        assertEquals(cmp.compare(a1, a2), 0);
        assertEquals(cmp.compare(a2, a1), 0);

        a1.loseEnergy();
        assertTrue(cmp.compare(a1, a2) < 0);
        assertTrue(cmp.compare(a2, a1) > 0);

        a1.addEnergy(10);
        assertTrue(cmp.compare(a1, a2) > 0);
        assertTrue(cmp.compare(a2, a1) < 0);
    }

    @Test
    public void sortContains() {
        AnimalComparator cmp = new AnimalComparatorMaxEnergy();
        AnimalCreator animalCreator =  new AnimalCreatorNormal(new GenotypeCreatorIncremental(10), 10, 10);
        ArrayList<Animal> animals = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            animals.add(animalCreator.create(i));
        }

        HashSet<Animal> strongest = new HashSet<>(cmp.sort(animals));
        assertTrue(strongest.containsAll(animals));
    }

    @Test
    public void sortOneGreatest() {
        AnimalComparator cmp = new AnimalComparatorMaxEnergy();
        AnimalCreator animalCreator =  new AnimalCreatorNormal(new GenotypeCreatorIncremental(10), 10, 10);
        ArrayList<Animal> animals = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            animals.add(animalCreator.create(i));
        }

        Animal a0 = animals.getFirst();
        a0.addEnergy(10);
        assertEquals(a0, cmp.sort(animals).getLast());
    }
}
