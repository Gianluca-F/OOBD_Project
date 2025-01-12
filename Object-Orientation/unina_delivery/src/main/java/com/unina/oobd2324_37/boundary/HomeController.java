package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class HomeController {

    @FXML
    private TableView<Ordine> orderTable;

    @FXML
    private TableColumn<Ordine, String> idOrdineColumn;

    @FXML
    private TableColumn<Ordine, LocalDate> dataOrdineColumn;

    @FXML
    private TableColumn<Ordine, Double> prezzoTotaleColumn;

    @FXML
    private TableColumn<Ordine, String> clienteColumn;

    @FXML
    private TableColumn<Ordine, String> viaColumn;

    @FXML
    private TableColumn<Ordine, String> civicoColumn;

    @FXML
    private TableColumn<Ordine, String> CAPColumn;

    @FXML
    private TableColumn<Ordine, String> cittaColumn;

    @FXML
    private TableColumn<Ordine, String> cellulareColumn;

    @FXML
    private TableColumn<Ordine, Boolean> consegnatoColumn;

    @FXML
    private TableColumn<Ordine, String> idspedizioneColumn;

    @FXML
    private TableColumn<Ordine, String> operatoreColumn;

    private OrdineDAO ordineDAO = new OrdineDAOimp();
    private ObservableList<Ordine> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Column initialization
        idOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        prezzoTotaleColumn.setCellValueFactory(new PropertyValueFactory<>("prezzoTot"));
        clienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome() + " " + cellData.getValue().getCliente().getCognome()));
        viaColumn.setCellValueFactory(new PropertyValueFactory<>("via"));
        civicoColumn.setCellValueFactory(new PropertyValueFactory<>("civico"));
        CAPColumn.setCellValueFactory(new PropertyValueFactory<>("CAP"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("citta"));
        cellulareColumn.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
        consegnatoColumn.setCellValueFactory(new PropertyValueFactory<>("consegnato"));
        idspedizioneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpedizione().getIdSpedizione()));
        operatoreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperatore().getNome() + " " + cellData.getValue().getOperatore().getCognome()));

        // Load data
        orderList.addAll(ordineDAO.getAll());
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
