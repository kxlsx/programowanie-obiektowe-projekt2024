package agh.oop.presenter;

import agh.oop.SimulationConfiguration;
import agh.oop.model.MapType;
import agh.oop.model.MutationMode;
import agh.oop.model.PlantGrowthMode;
import agh.oop.model.Vector2d;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class SimulationLauncher {
    public TextField movesInput;
    public Button start;
    public TextField mapSizeXField;
    public TextField mapSizeYField;
    public Slider initialNumberOfPlantsSlider;
    public Label initialNumberOfPlantsLabel;
    public Slider energyFromPlantSlider;
    public Label energyFromPlantLabel;
    public Slider plantGrowthPerDaySlider;
    public Label plantGrowthPerDayLabel;
    public Slider initialNumberOfAnimalsSlider;
    public Label initialNumberOfAnimalsLabel;
    public Slider initiaAnimalEnergySlider;
    public Label initiaAnimalEnergyLabel;
    public Slider reproductionEnergyThresholdSlider;
    public Label reproductionEnergyThresholdLabel;
    public Slider reproductionCostSlider;
    public Label reproductionCostLabel;
    public Slider genomeLengthSlider;
    public Label genomeLengthLabel;
    public ChoiceBox<String> mapTypeChoiceBox;
    public ChoiceBox<String> plantGrowthModeChoiceBox;
    public ChoiceBox<String> mutationModeChoiceBox;
    private SimulationConfiguration configuration;
//    private final SimulationsEngine engine = new SimulationsEngine();

    public SimulationLauncher() {
        Vector2d mapSize = new Vector2d(10, 10);
        configuration = new SimulationConfiguration(
            mapSize,
            3,
            1,
            1,
            10,
            10,
            2,
            1,
            1,
            MapType.NORMAL,
            PlantGrowthMode.EQUATOR,
            MutationMode.FULL_RANDOM);
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        try {
            Stage stage = new Stage();
            BorderPane viewRoot = loader.load();
            configureStage(stage, viewRoot);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
//        SimulationPresenter presenter = loader.getController();
        //initPresenter(presenter);
    }

    private void initPresenter(/*SimulationPresenter presenter*/) {
//        GrassField map = new GrassField(5);
//        presenter.setWorldMap(map);
//        map.addListener(presenter);
//        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
//        List<MoveDirection> directions = OptionsParser.parse(List.of(movesInput.getText().split(" ")));
//        engine.addAndRunAsync(new Simulation(positions, directions, map));
    }

    public void init() {
        bindLabelsToSliders();
        initializeListeners();
        loadUiFromConfiguration();
    }

    private void bindLabelsToSliders() {
        initialNumberOfPlantsLabel.textProperty().bind(
                initialNumberOfPlantsSlider.valueProperty().asString("%.0f"));
        energyFromPlantLabel.textProperty().bind(
                energyFromPlantSlider.valueProperty().asString("%.0f"));
        plantGrowthPerDayLabel.textProperty().bind(
                plantGrowthPerDaySlider.valueProperty().asString("%.0f"));
        initialNumberOfAnimalsLabel.textProperty().bind(
                initialNumberOfAnimalsSlider.valueProperty().asString("%.0f"));
        initiaAnimalEnergyLabel.textProperty().bind(
                initiaAnimalEnergySlider.valueProperty().asString("%.0f"));
        reproductionEnergyThresholdLabel.textProperty().bind(
                reproductionEnergyThresholdSlider.valueProperty().asString("%.0f"));
        reproductionCostLabel.textProperty().bind(
                reproductionCostSlider.valueProperty().asString("%.0f"));
        genomeLengthLabel.textProperty().bind(
                genomeLengthSlider.valueProperty().asString("%.0f"));
    }

    private void initializeListeners() {
        mapSizeXField.textProperty().addListener((observable, oldValue, newValue) -> onTextChanged("mapSizeX", newValue));
        mapSizeYField.textProperty().addListener((observable, oldValue, newValue) -> onTextChanged("mapSizeY", newValue));

        // sliders
        initialNumberOfPlantsSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("initialNumberOfPlants", newValue.intValue()));
        energyFromPlantSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("energyFromPlant", newValue.intValue()));
        plantGrowthPerDaySlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("plantGrowthPerDay", newValue.intValue()));
        initialNumberOfAnimalsSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("initialNumberOfAnimals", newValue.intValue()));
        initiaAnimalEnergySlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("initialAnimalEnergy", newValue.intValue()));
        reproductionEnergyThresholdSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("reproductionEnergyThreshold", newValue.intValue()));
        reproductionCostSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("reproductionCost", newValue.intValue()));
        genomeLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderMoved("genomeLength", newValue.intValue()));
    }

    private static Optional<Integer> getIntegerIfValid(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void onTextChanged(String fieldName, String value) {
        var asInt = getIntegerIfValid(value);
        if(asInt.isPresent()) {
            switch (fieldName) {
                case "mapSizeX" -> configuration.setMapSize(new Vector2d(asInt.get(), configuration.getMapSize().getY()));
                case "mapSizeY" -> configuration.setMapSize(new Vector2d(configuration.getMapSize().getX(), asInt.get()));
            }
        }
        else {
            switch (fieldName) {
                case "mapSizeX" -> mapSizeXField.textProperty().set(String.valueOf(configuration.getMapSize().getX()));
                case "mapSizeY" -> mapSizeYField.textProperty().set(String.valueOf(configuration.getMapSize().getY()));
            }
        }

    }

    private void onSliderMoved(String fieldName, int newValue) {
        switch (fieldName) {
            case "initialNumberOfPlants" -> configuration.setInitialNumberOfPlants(newValue);
            case "energyFromPlant" -> configuration.setEnergyFromPlant(newValue);
            case "plantGrowthPerDay" -> configuration.setPlantGrowthPerDay(newValue);
            case "initialNumberOfAnimals" -> configuration.setInitialNumberOfAnimals(newValue);
            case "initialAnimalEnergy" -> configuration.setInitialAnimalEnergy(newValue);
            case "reproductionEnergyThreshold" -> configuration.setReproductionEnergyThreshold(newValue);
            case "reproductionCost" -> configuration.setReproductionCost(newValue);
            case "genomeLength" -> configuration.setGenomeLength(newValue);
        }
    }

    public void loadUiFromConfiguration() {
        // text fields
        mapSizeXField.setText(String.valueOf(configuration.getMapSize().getX()));
        mapSizeYField.setText(String.valueOf(configuration.getMapSize().getY()));

        // sliders
        initialNumberOfPlantsSlider.setValue(configuration.getInitialNumberOfPlants());
        energyFromPlantSlider.setValue(configuration.getEnergyFromPlant());
        plantGrowthPerDaySlider.setValue(configuration.getPlantGrowthPerDay());
        initialNumberOfAnimalsSlider.setValue(configuration.getInitialNumberOfAnimals());
        initiaAnimalEnergySlider.setValue(configuration.getInitialAnimalEnergy());
        reproductionEnergyThresholdSlider.setValue(configuration.getReproductionEnergyThreshold());
        reproductionCostSlider.setValue(configuration.getReproductionCost());
        genomeLengthSlider.setValue(configuration.getGenomeLength());

        // choice boxes
        mapTypeChoiceBox.getSelectionModel().select(configuration.getMapType().toString());
        plantGrowthModeChoiceBox.getSelectionModel().select(configuration.getPlantGrowthMode().toString());
        mutationModeChoiceBox.getSelectionModel().select(configuration.getMutationMode().toString());
    }

    private void configureStage(Stage stage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation app");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        stage.show();
    }

    public void saveConfiguration(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration");
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null) {
            try {
                FileOutputStream fout = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(configuration);
                out.close();
                fout.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadConfiguration(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Configuration");
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null) {
            try {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fin);
                configuration = (SimulationConfiguration) in.readObject();
                in.close();
                fin.close();
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        loadUiFromConfiguration();
    }

    public void handleRun(ActionEvent actionEvent) {
        
    }
}