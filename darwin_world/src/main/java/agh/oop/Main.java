package agh.oop;

import agh.oop.model.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SimulationConfiguration config = new SimulationConfiguration(
                new Vector2d(10, 10),
                3,
                2,
                1,
                2,
                10,
                3,
                2,
                1,
                5,
                5,
                MapType.NORMAL,
                PlantGrowthMode.EQUATOR,
                MutationMode.FULL_RANDOM);
        Simulation simulation = new Simulation(config);
        simulation.run();
    }
}