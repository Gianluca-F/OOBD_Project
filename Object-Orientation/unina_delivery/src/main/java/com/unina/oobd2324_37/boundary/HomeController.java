package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.HomeControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HomeController {

    @FXML
    public Label labelWelcome;

    @FXML
    public Button logoutButton;

    @FXML
    public Button homeButton;

    @FXML
    public Button deliveryButton;

    @FXML
    public Button warehouseButton;

    @FXML
    public Button reportButton;

    @FXML
    public AnchorPane mainContentArea;

    private HomeControl homeControl;

    @FXML
    public void initialize() {
        homeControl = HomeControl.getInstance();
        setLabelWelcome();
    }

    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        homeControl.logout();
    }

    @FXML
    public void goToHome(ActionEvent actionEvent) {
        //TODO: Implement this method
    }

    @FXML
    public void goToDelivery(ActionEvent actionEvent) {
        //TODO: Implement this method
    }

    @FXML
    public void goToWarehouse(ActionEvent actionEvent) {
        //TODO: Implement this method
    }

    @FXML
    public void goToReport(ActionEvent actionEvent) {
        //TODO: Implement this method
    }

    public void setLabelWelcome() {
        labelWelcome.setText("Benvenuto, " + homeControl.getOperatoreName() + "!");
    }
}
