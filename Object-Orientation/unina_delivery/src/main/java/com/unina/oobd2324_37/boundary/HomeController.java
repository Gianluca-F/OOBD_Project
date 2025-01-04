package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.HomeControl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

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

    @FXML
    private Rectangle homeIndicator;

    @FXML
    private Rectangle deliveryIndicator;

    @FXML
    private Rectangle warehouseIndicator;

    @FXML
    private Rectangle reportIndicator;

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

    private void showIndicator(Rectangle indicator) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), indicator);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        indicator.setVisible(true);
    }

    private void resetIndicators() {
        homeIndicator.setVisible(false);
        deliveryIndicator.setVisible(false);
        warehouseIndicator.setVisible(false);
        reportIndicator.setVisible(false);
    }

    @FXML
    public void goToHome(ActionEvent actionEvent) {
        //TODO: Implement this method
        if(homeIndicator.isVisible()) {
            return;
        }
        resetIndicators();
        showIndicator(homeIndicator);
    }

    @FXML
    public void goToDelivery(ActionEvent actionEvent) {
        //TODO: Implement this method
        if(deliveryIndicator.isVisible()) {
            return;
        }
        resetIndicators();
        showIndicator(deliveryIndicator);
    }

    @FXML
    public void goToWarehouse(ActionEvent actionEvent) {
        //TODO: Implement this method
        if(warehouseIndicator.isVisible()) {
            return;
        }
        resetIndicators();
        showIndicator(warehouseIndicator);
    }

    @FXML
    public void goToReport(ActionEvent actionEvent) {
        //TODO: Implement this method
        if(reportIndicator.isVisible()) {
            return;
        }
        resetIndicators();
        showIndicator(reportIndicator);
    }

    public void setLabelWelcome() {
        labelWelcome.setText("Benvenuto, " + homeControl.getOperatoreName() + "!");
    }
}
