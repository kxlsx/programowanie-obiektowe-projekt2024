package agh.oop.model.animal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalCreatorNormalTest {
    @Test
    public void energySum() {
        AnimalCreator creator = new AnimalCreatorNormal(new GenotypeCreatorIncremental(5), 5, 10);

        Animal parent1 = creator.create(0);
        Animal parent2 = creator.create(1);
        int energySum = parent1.getEnergy() + parent2.getEnergy();

        Animal child = creator.reproduce(parent1, parent2, 2);

        assertEquals(parent1.getEnergy() + parent2.getEnergy() + child.getEnergy(), energySum);
    }
}
