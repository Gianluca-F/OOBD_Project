package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.LoginControl;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField promptPasswordField;

    @FXML
    private TextField plainPasswordField;

    @FXML
    private PasswordField palliniPasswordField;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private Label errorLabel;

    private boolean isPasswordVisible = false;
    private LoginControl loginControl;

    @FXML
    public void initialize() {
        loginControl = LoginControl.getInstance();
        loginControl.initialize(this);

        palliniPasswordField.textProperty().addListener((observable, oldValue, newValue) -> { /* lambda expression: */
            toggleButton.setVisible(!newValue.isEmpty());                               /* Show toggle button only if password is not empty */
            updatePromptPasswordFieldVisibility(newValue);
        });

        plainPasswordField.textProperty().bindBidirectional(palliniPasswordField.textProperty());

        toggleButton.setOnAction(event -> togglePasswordVisibility());
    }

    private void updatePromptPasswordFieldVisibility(String newValue) {
        if(newValue.isEmpty()) {
            promptPasswordField.setPromptText("Password");
        }
        else {
            promptPasswordField.setPromptText("");
        }
    }

    public void buttonPressed(ActionEvent actionEvent) {
        loginControl.login(usernameField.getText(), palliniPasswordField.getText());
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

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        plainPasswordField.setVisible(isPasswordVisible);
        palliniPasswordField.setVisible(!isPasswordVisible);
        eyeIcon.setImage(new Image(getClass().getResourceAsStream(isPasswordVisible ? "/images/showEye.png" : "/images/hideEye.png")));
    }
}
