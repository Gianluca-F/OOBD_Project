package com.unina.oobd2324_37.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is used to start the JavaFX application.
 */
public final class App extends Application {

    private static final int INITIAL_SCENE_WIDTH = 800;
    private static final int INITIAL_SCENE_HEIGHT = 600;
    private static final int MIN_SCENE_WIDTH = 700;
    private static final int MIN_SCENE_HEIGHT = 550;
    private static Scene scene;

    /**
     * Start the JavaFX application
     * @param firstStage The first stage of the application
     */
    @Override
    public void start(Stage firstStage) {
        try {
            scene = new Scene(loadFXML("Login-Page").load(), INITIAL_SCENE_WIDTH, INITIAL_SCENE_HEIGHT);
            firstStage.setTitle("Unina Delivery");
            firstStage.setScene(scene);
            firstStage.setMinWidth(MIN_SCENE_WIDTH);
            firstStage.setMinHeight(MIN_SCENE_HEIGHT);
            firstStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("FXML file not found: " + e.getMessage());
        }
    }

    /**
     * Main method to start the JavaFX application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Application.launch(); // Start the JavaFX application
    }

    /**
     * Load the FXML file
     * @param fxml The name of the FXML file
     * @return The FXMLLoader object
     * @throws NullPointerException If the FXML file is not found
     */
    public static FXMLLoader loadFXML(String fxml) throws NullPointerException {
        return new FXMLLoader(App.class.getResource("/FXML/" + fxml + ".fxml"));
    }

    /**
     * Set the root of the scene to the FXML file
     * @param fxml The name of the FXML file
     * @throws IOException If the FXML file is not found
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load()); // Load the new FXML file
    }
}
