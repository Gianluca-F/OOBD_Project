package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.HomeControl;
import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeController {

    @FXML
    private TextField customerFilterField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

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

    private final OrdineDAO ordineDAO = new OrdineDAOimp();
    private final ObservableList<Ordine> orderList = FXCollections.observableArrayList();

    private HomeControl homeControl;

    @FXML
    public void initialize() {
        homeControl = HomeControl.getInstance();
        homeControl.initialize(this);

        formatDatePicker();
        updateTable();
    }

    public void handleApplyFilter(ActionEvent actionEvent) {
        homeControl.applyFilter(customerFilterField.getText(), startDatePicker.getValue(), endDatePicker.getValue());
    }

    public void handleResetFilter(ActionEvent actionEvent) {
        customerFilterField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    public void handleViewDetails(ActionEvent actionEvent) {
    }

    public void handleDeleteOrder(ActionEvent actionEvent) {
    }

    public void updateTable() {
        setTableColumns();
        loadOrders(ordineDAO.getAll());
    }

    public void updateTable(String cliente, LocalDate startDate, LocalDate endDate) {
        setTableColumns();
        loadOrders(ordineDAO.getByCustomerAndDate(cliente, startDate, endDate));
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
        idspedizioneColumn.setCellValueFactory(cellData -> {
            String idSpedizione = cellData.getValue().getSpedizione() != null ?
                    cellData.getValue().getSpedizione().getIdSpedizione() :
                    "null";
            return new SimpleStringProperty(idSpedizione);
        });
        operatoreColumn.setCellValueFactory(cellData -> {
            String idOperatore = cellData.getValue().getOperatore() != null ?
                    cellData.getValue().getOperatore().getNome() + " " + cellData.getValue().getOperatore().getCognome() :
                    "null";
            return new SimpleStringProperty(idOperatore);
        });
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

    private void loadOrders(List<Ordine> orders) {
        orderList.clear();
        orderList.addAll(orders);
        orderTable.setItems(orderList);
    }

    private void formatDatePicker() {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                return (date == null) ? "" : dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return (string == null || string.isEmpty()) ? null : LocalDate.parse(string, dateFormatter);
            }
        };

        startDatePicker.setConverter(converter);
        endDatePicker.setConverter(converter);
    }
}
