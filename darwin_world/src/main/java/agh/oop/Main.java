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
    }
}