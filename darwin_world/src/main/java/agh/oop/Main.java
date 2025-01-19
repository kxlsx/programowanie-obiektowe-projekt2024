package agh.oop;

import agh.oop.simulation.SimulationFactory;
import agh.oop.simulation.config.MapType;
import agh.oop.simulation.config.MutationMode;
import agh.oop.simulation.config.SimulationConfiguration;
import agh.oop.model.*;
import agh.oop.simulation.config.PlantGrowthMode;
import agh.oop.simulation.Simulation;
import agh.oop.simulation.StatisticsObserver;

import java.util.List;
import javafx.application.Application;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Application.launch(SimulationApp.class, args);
        /*

        Vector2d mapSize = new Vector2d(10, 10);
        SimulationConfiguration config = new SimulationConfiguration(
                mapSize,
                3,
                1,
                1,
                10,
                10,
                2,
                1,
                1,
                5,
                5,
                MapType.NORMAL,
                PlantGrowthMode.EQUATOR,
                MutationMode.FULL_RANDOM);
        StatisticsObserver so = new StatisticsObserver(new Boundary(new Vector2d(0, 0), mapSize));
        Simulation simulation = SimulationFactory.createFromConfig(config, List.of(so));

        while (true) {
            simulation.advance();
            System.out.println(so.aliveAnimalCount() + " " + so.deadAnimalCount()
            + " " + so.plantCount() + " " + so.freeCellsCount() + " " + so.averageEnergy()
            + " " + so.averageDeadLifespan() + " " + so.averageAliveChildCount());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
            }
        }
         */
    }
}