package agh.oop;

import agh.oop.presenter.SimulationLauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SimulationApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationLauncher.fxml"));
        AnchorPane viewRoot = loader.load();
        SimulationLauncher launcher = loader.getController();
        launcher.init();
        configureStage(primaryStage, viewRoot);
    }

    private void configureStage(Stage primaryStage, AnchorPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        //primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        //primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}