package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.entity.DTO.Order;
import javafx.event.ActionEvent;
/*
public class HomeController {
    public void handleApplyFilter(ActionEvent actionEvent) {
    }

    public void handleResetFilter(ActionEvent actionEvent) {
    }

    public void handleViewDetails(ActionEvent actionEvent) {
    }

    public void handleDeleteOrder(ActionEvent actionEvent) {
    }
} */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> idOrdineColumn;

    @FXML
    private TableColumn<Order, String> dataOrdineColumn;

    @FXML
    private TableColumn<Order, String> prezzoTotaleColumn;

    @FXML
    private TableColumn<Order, String> utenteColumn;

    @FXML
    private TableColumn<Order, String> emailColumn;

    @FXML
    private TableColumn<Order, String> indirizzoColumn;

    @FXML
    private TableColumn<Order, String> cittaColumn;

    @FXML
    private TableColumn<Order, String> cellulareColumn;

    @FXML
    private TableColumn<Order, String> consegnatoColumn;

    @FXML
    private TableColumn<Order, String> idspedizioneColumn;

    @FXML
    private TableColumn<Order, String> operatoreColumn;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Collega le colonne con i campi del modello
        idOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("dataOrdine"));
        prezzoTotaleColumn.setCellValueFactory(new PropertyValueFactory<>("prezzoTotale"));
        utenteColumn.setCellValueFactory(new PropertyValueFactory<>("utente"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("citta"));
        cellulareColumn.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
        consegnatoColumn.setCellValueFactory(new PropertyValueFactory<>("consegnato"));
        idspedizioneColumn.setCellValueFactory(new PropertyValueFactory<>("idSpedizione"));
        operatoreColumn.setCellValueFactory(new PropertyValueFactory<>("operatore"));

        // Aggiungi dati di esempio
        orderList.add(new Order("001", "2025-01-01", "100.50", "Mario Rossi", "mario.rossi@example.com",
                "Via Roma 10", "Napoli", "3331234567", "Sì", "SP001", "Luca Bianchi"));
        orderList.add(new Order("002", "2025-01-02", "150.00", "Anna Verdi", "anna.verdi@example.com",
                "Corso Italia 5", "Milano", "3456789012", "No", "SP002", "Giulia Neri"));

        // Collega la lista alla tabella
        orderTable.setItems(orderList);
    }



    public void handleApplyFilter(ActionEvent actionEvent) {
    }

    public void handleResetFilter(ActionEvent actionEvent) {
    }

    public void handleViewDetails(ActionEvent actionEvent) {
    }

    public void handleDeleteOrder(ActionEvent actionEvent) {
    }
}
