package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Operatore;
import com.unina.oobd2324_37.boundary.DashboardController;

import javafx.scene.Node;

import java.io.IOException;

/**
 * This class is used to control the dashboard operations.
 */
public class DashboardControl {

    private static DashboardControl instance = null;
    private Operatore operatore;
    private DashboardController dashboardController;

    /**
     * Private constructor to avoid instantiation.
     */
    private DashboardControl() {}

    /**
     * This method is used to get the instance (singleton) of the DashboardControl class.
     * @return The instance of the DashboardControl class
     */
    public static DashboardControl getInstance() {
        if(instance == null) {
            instance = new DashboardControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the DashboardControl class.
     * @param dashboardController The instance of DashboardController class
     */
    public void initialize(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * This method is used to set the operator.
     * @param operatore The operator
     */
    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    /**
     * This method is used to get the operator.
     * @return The operator
     */
    public Operatore getOperatore() {
        return operatore;
    }

    /**
     * This method is used to log out from the application.
     */
    public void logout() {
        try {
            App.setRoot("Login-Page");
            DBConnection.closeConnection();
        } catch (IOException e) {
            System.err.println("Error switching to Login-Page: " + e.getMessage());
        }
    }

    /**
     * This method is used to load the page.
     * @param page The page to load
     */
    public void loadPage(String page) {
        try {
            Node content = App.loadFXML(page).load();
            dashboardController.updateMainContentArea(content);
            dashboardController.setAnchorParameters(content);
        } catch (IOException e) {
            System.err.println("Error switching to " + page + ", tried loading FXML file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("FXML file not found: " + e.getMessage());
        }
    }
}
