package com.unina.oobd2324_37.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class App extends Application {

    private static final int INITIAL_SCENE_WIDTH = 650;
    private static final int INITIAL_SCENE_HEIGHT = 480;
    private static final int MIN_SCENE_WIDTH = 500;
    private static final int MIN_SCENE_HEIGHT = 420;
    private static Scene scene;

    @Override
    public void start(Stage firstStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login-Page.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, INITIAL_SCENE_WIDTH, INITIAL_SCENE_HEIGHT);
        firstStage.setTitle("Unina Delivery");
        firstStage.setScene(scene);
        firstStage.setMinWidth(MIN_SCENE_WIDTH);
        firstStage.setMinHeight(MIN_SCENE_HEIGHT);
        firstStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
