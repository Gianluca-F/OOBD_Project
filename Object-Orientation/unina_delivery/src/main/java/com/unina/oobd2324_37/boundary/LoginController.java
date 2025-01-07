package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.LoginControl;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

/**
 * This class is used to manage the login page UI.
 */
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
    private boolean isAnimationErrorLabelRunning = false;
    private LoginControl loginControl;

    /**
     * This method is called when the login page is initialized.
     */
    @FXML
    public void initialize() {
        loginControl = LoginControl.getInstance();
        loginControl.initialize(this);

        palliniPasswordField.textProperty().addListener((observable, oldValue, newValue) -> { /* lambda expression: */
            toggleButton.setVisible(!newValue.isEmpty());                                   /* Only show the toggle button if the password field is not empty */
            updatePromptPasswordFieldVisibility(newValue);
        });

        plainPasswordField.textProperty().bindBidirectional(palliniPasswordField.textProperty());

        toggleButton.setOnAction(event -> togglePasswordVisibility());
    }

    /**
     * Update the prompt text of the password field.
     * @param newValue The new value of the password field
     */
    private void updatePromptPasswordFieldVisibility(String newValue) {
        if(newValue.isEmpty()) {
            promptPasswordField.setPromptText("Password");
        }
        else {
            promptPasswordField.setPromptText("");
        }
    }

    /**
     * This method is called when the login button is pressed.
     */
    public void buttonPressed() {
        loginControl.login(usernameField.getText(), palliniPasswordField.getText());
    }

    /**
     * Show an error message when the login fails.
     * @param message The error message
     */
    public void showErrorMessage(String message) {
        errorLabel.setText(message);
        if(!isAnimationErrorLabelRunning) {
            shakeErrorLabel();
        }
    }

    /**
     * Shake the error label.
     */
    private void shakeErrorLabel() {
        isAnimationErrorLabelRunning = true;

        TranslateTransition shakingTT = new TranslateTransition(Duration.millis(100), errorLabel);
        TranslateTransition originalCoordsTT = new TranslateTransition(Duration.millis(30), errorLabel);
        shakingTT.setFromX(-5);
        shakingTT.setToX(5);
        shakingTT.setCycleCount(4);
        shakingTT.setRate(0.5);
        shakingTT.setAutoReverse(true);
        originalCoordsTT.setByX(5);
        originalCoordsTT.setRate(0.3);

        // When the shaking transition is finished, the original coordinates transition is played
        shakingTT.setOnFinished(event -> {
            originalCoordsTT.play();
            originalCoordsTT.setOnFinished(e -> isAnimationErrorLabelRunning = false);
        });

        shakingTT.play();
    }

    /**
     * Toggle the visibility of the password.
     */
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        plainPasswordField.setVisible(isPasswordVisible);
        palliniPasswordField.setVisible(!isPasswordVisible);
        eyeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(isPasswordVisible ? "/images/showEye.png" : "/images/hideEye.png"))));
    }
}
