package com.unina.oobd2324_37.boundary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.unina.oobd2324_37.control.LoginControl;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void buttonPressed(ActionEvent actionEvent) {
        LoginControl.login(usernameField.getText(), passwordField.getText());
    }
}
