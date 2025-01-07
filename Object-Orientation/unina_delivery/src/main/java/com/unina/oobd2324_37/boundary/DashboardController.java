package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.DashboardControl;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

/**
 * This class is used to manage the dashboard UI.
 */
public class DashboardController {

    @FXML
    private Label labelWelcome;

    @FXML
    private AnchorPane mainContentArea;

    @FXML
    private Rectangle homeIndicator;

    @FXML
    private Rectangle deliveryIndicator;

    @FXML
    private Rectangle warehouseIndicator;

    @FXML
    private Rectangle reportIndicator;

    private DashboardControl dashboardControl;

    /**
     * Initialize the dashboard controller and load the home page.
     */
    @FXML
    public void initialize() {
        dashboardControl = DashboardControl.getInstance();
        dashboardControl.initialize(this);
        setLabelWelcome();
        goToHome();
    }

    /**
     * Logout from the application.
     */
    @FXML
    public void handleLogout() {
        dashboardControl.logout();
    }

    /**
     * Go to the home page if not already on it.
     */
    @FXML
    public void goToHome() {
        if(updateIndicator(homeIndicator)) {
            dashboardControl.loadPage("Home-Page");
        }
    }

    /**
     * Go to the delivery page if not already on it.
     */
    @FXML
    public void goToDelivery() {
        if(updateIndicator(deliveryIndicator)) {
            dashboardControl.loadPage("Delivery-Page");
        }
    }

    /**
     *  Go to the warehouse page if not already on it.
     */
    @FXML
    public void goToWarehouse() {
        if(updateIndicator(warehouseIndicator)) {
            dashboardControl.loadPage("Warehouse-Page");
        }
    }

    /**
     * Go to the report page if not already on it.
     */
    @FXML
    public void goToReport() {
        if(updateIndicator(reportIndicator)) {
            dashboardControl.loadPage("Report-Page");
        }
    }

    /**
     * Set the welcome label with the name of the operator.
     */
    public void setLabelWelcome() {
        labelWelcome.setText("Benvenuto, " + dashboardControl.getOperatore().getNome() + "!");
    }

    /**
     * Reset all the indicators.
     */
    private void resetIndicators() {
        homeIndicator.setVisible(false);
        deliveryIndicator.setVisible(false);
        warehouseIndicator.setVisible(false);
        reportIndicator.setVisible(false);
    }

    /**
     * Show the indicator with a fade animation.
     * @param indicator The indicator to show
     */
    private void showIndicatorAnimated(Rectangle indicator) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), indicator);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        indicator.setVisible(true);
    }

    /**
     * Update the indicator of the selected page.
     * @param indicator The indicator of the selected page
     * @return True if the indicator is updated, false otherwise (if the indicator is already visible)
     */
    private boolean updateIndicator(Rectangle indicator) {
        if(indicator.isVisible()) {
            return false;
        }
        resetIndicators();
        showIndicatorAnimated(indicator);
        return true;
    }

    /**
     * Update the main content area with the selected page.
     * @param content The content to show
     */
    public void updateMainContentArea(Node content) {
        mainContentArea.getChildren().clear();
        mainContentArea.getChildren().add(content);
    }

    /**
     * Set the anchor parameters for the content.
     * @param content The content to set the anchor parameters for
     */
    public void setAnchorParameters(Node content) {
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
    }
}
