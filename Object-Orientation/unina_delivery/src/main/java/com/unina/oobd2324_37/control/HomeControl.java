package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.entity.DTO.Operatore;

import java.io.IOException;

public class HomeControl {

    private static HomeControl instance = null;
    private Operatore operatore;

    private HomeControl() {}

    public static HomeControl getInstance() {
        if(instance == null) {
            instance = new HomeControl();
        }
        return instance;
    }

    public void initialize(Operatore operatore) {
        this.operatore = operatore;
    }

    public String getOperatoreName() {
        return operatore.getNome();
    }

    public void logout() {
        try {
            App.setRoot("Login-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Login-Page: " + e.getMessage());
        }
    }

    public void goToHome() {
        try {
            App.setRoot("Home-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Home-Page: " + e.getMessage());
        }
    }

    public void goToDelivery() {
        try {
            App.setRoot("Delivery-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Delivery-Page: " + e.getMessage());
        }
    }

    public void goToWarehouse() {
        try {
            App.setRoot("Warehouse-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Warehouse-Page: " + e.getMessage());
        }
    }

    public void goToReport() {
        try {
            App.setRoot("Report-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Report-Page: " + e.getMessage());
        }
    }
}
