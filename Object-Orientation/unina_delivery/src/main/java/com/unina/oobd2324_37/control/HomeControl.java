package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.DashboardController;
import com.unina.oobd2324_37.boundary.HomeController;

import java.time.LocalDate;

public class HomeControl {

    private static HomeControl instance = null;
    private HomeController homeController;

    /**
     * Private constructor to avoid instantiation.
     */
    private HomeControl() {}

    /**
     * This method is used to get the instance (singleton) of the HomeControl class.
     * @return The instance of the HomeControl class
     */
    public static HomeControl getInstance() {
        if(instance == null) {
            instance = new HomeControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the HomeControl class.
     * @param homeController The instance of HomeController class
     */
    public void initialize(HomeController homeController) {
        this.homeController = homeController;
    }

    public void applyFilter(String cliente, LocalDate startDate, LocalDate endDate) {
        if(!isClienteValid(cliente) && startDate == null && endDate == null) {
            homeController.updateTable();
        } else {
            homeController.updateTable(cliente, startDate, endDate);
        }
    }

    private boolean isClienteValid(String cliente) {
        return cliente != null && !cliente.isEmpty();
    }
}
