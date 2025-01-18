package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.HomeControl;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.util.function.Supplier;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class is used to manage the Home Controller.
 */
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

    @FXML
    private Button viewDetailsButton;

    private final ObservableList<Ordine> orderList = FXCollections.observableArrayList();

    private HomeControl homeControl;

    /**
     * This method is used to initialize the HomeController class.
     */
    @FXML
    public void initialize() {
        homeControl = HomeControl.getInstance();
        homeControl.initialize(this);

        formatDatePicker();
        setTableColumns();
        homeControl.applyFilter(null, null, null);

        // Disabilita il pulsante se non ci sono righe selezionate
        viewDetailsButton.disableProperty().bind(
                orderTable.getSelectionModel().selectedItemProperty().isNull()
        );

        // Resetta la selezione quando il TableView o VisualizzaOrdine peronoe il focus
        orderTable.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!orderTable.isFocused() && !viewDetailsButton.isFocused()) {
                    orderTable.getSelectionModel().clearSelection();
                }
            }
        });
    }

    /**
     * This method is used to handle the apply filter action.
     * @param actionEvent The action event
     */
    public void handleApplyFilter(ActionEvent actionEvent) {
        homeControl.applyFilter(customerFilterField.getText(), startDatePicker.getValue(), endDatePicker.getValue());
    }

    /**
     * This method is used to handle the reset filter action.
     * @param actionEvent The action event
     */
    public void handleResetFilter(ActionEvent actionEvent) {
        customerFilterField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    /**
     * This method is used to handle the view details action.
     * @param actionEvent The action event
     */
    public void handleViewDetails(ActionEvent actionEvent) {
        Ordine selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        homeControl.showOrderDetails(selectedOrder);
    }

    /**
     * This method is used to update the table.
     * @param orderSupplier The order supplier (lambda expression)
     */
    public void updateTable(Supplier<List<Ordine>> orderSupplier) {
        loadOrders(orderSupplier.get());
    }

    /**
     * This method is used to set the table columns.
     */
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
                    "N/A";
            return new SimpleStringProperty(idSpedizione);
        });
        operatoreColumn.setCellValueFactory(cellData -> {
            String idOperatore = cellData.getValue().getOperatore() != null ?
                    cellData.getValue().getOperatore().getNome() + " " + cellData.getValue().getOperatore().getCognome() :
                    "N/A";
            return new SimpleStringProperty(idOperatore);
        });
    }

    /**
     * This method is used to set the "Prezzo Totale" column.
     */
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

    /**
     * This method is used to load the orders.
     * @param orders The list of orders
     */
    private void loadOrders(List<Ordine> orders) {
        orderList.clear();
        orderList.addAll(orders);
        orderTable.setItems(orderList);
    }

    /**
     * This method is used to format the date picker.
     */
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
