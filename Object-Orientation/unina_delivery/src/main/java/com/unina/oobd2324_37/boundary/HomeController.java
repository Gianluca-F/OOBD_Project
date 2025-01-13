package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.text.DecimalFormat;
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
    private TableColumn<Ordine, String> indirizzoColumn;

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
        setTableColumns();

        // Load data
        orderList.addAll(ordineDAO.getAll());
        orderTable.setItems(orderList);
    }

    private void setTableColumns() {
        // Column initialization
        idOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Personalizzazione della colonna "Prezzo Totale"
        setprezzoTotColumn();

        prezzoTotaleColumn.setCellValueFactory(new PropertyValueFactory<>("prezzoTot"));
        clienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome() + " " + cellData.getValue().getCliente().getCognome()));
        indirizzoColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getVia() + " " + cellData.getValue().getCivico()));
        CAPColumn.setCellValueFactory(new PropertyValueFactory<>("CAP"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("citta"));
        cellulareColumn.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
        consegnatoColumn.setCellValueFactory(new PropertyValueFactory<>("consegnato"));
        idspedizioneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpedizione().getIdSpedizione()));
        operatoreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOperatore().getNome() + " " + cellData.getValue().getOperatore().getCognome()));
    }

    private void setprezzoTotColumn() {
        prezzoTotaleColumn.setCellFactory(new Callback<TableColumn<Ordine, Double>, TableCell<Ordine, Double>>() {
            @Override
            public TableCell<Ordine, Double> call(TableColumn<Ordine, Double> param) {
                return new TableCell<Ordine, Double>() {
                    private final DecimalFormat df = new DecimalFormat("€ #.00 ");  // Formato con simbolo euro e due decimali

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(df.format(item));  // Formatto il valore con simbolo euro e 2 decimali
                        }
                    }
                };
            }
        });
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
