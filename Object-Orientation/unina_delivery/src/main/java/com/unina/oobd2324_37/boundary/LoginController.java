package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.LoginControl;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private LoginControl loginControl;

    @FXML
    public void initialize() {
        loginControl = new LoginControl(this);
    }

    public void buttonPressed(ActionEvent actionEvent) {
        loginControl.login(usernameField.getText(), passwordField.getText());
    }

    public void showErrorMessage(String message) {
        errorLabel.setText(message);
        shakeErrorLabel();
    }

    private void shakeErrorLabel() {
        TranslateTransition shakingTT = new TranslateTransition(Duration.millis(100), errorLabel);
        TranslateTransition originalCoordsTT = new TranslateTransition(Duration.millis(30), errorLabel);
        shakingTT.setFromX(-5);
        shakingTT.setToX(5);
        shakingTT.setCycleCount(4);
        shakingTT.setRate(0.5);
        shakingTT.setAutoReverse(true);
        originalCoordsTT.setByX(5);
        originalCoordsTT.setRate(0.3);
        shakingTT.setOnFinished(event -> originalCoordsTT.play());
        shakingTT.play();
    }

}
