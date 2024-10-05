package com.unina.oobd2324_37.control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage firstStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login-Page.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 500);
        firstStage.setTitle("Unina Delivery");
        firstStage.setScene(scene);
        firstStage.setMinWidth(firstStage.getWidth());
        firstStage.setMinHeight(firstStage.getHeight());
        firstStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
