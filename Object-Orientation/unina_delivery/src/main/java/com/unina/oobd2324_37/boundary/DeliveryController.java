package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.DeliveryControl;
import com.unina.oobd2324_37.entity.DTO.Corriere;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import com.unina.oobd2324_37.entity.DTO.Veicolo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

public class DeliveryController {

    @FXML
    private TableView<Ordine> orderTable;

    @FXML
    private TableColumn<Ordine, String> idOrdineColumn;

    @FXML
    private TableColumn<Ordine, LocalDate> dataOrdineColumn;

    @FXML
    private TableColumn<Ordine, String> clienteColumn;

    @FXML
    private TableColumn<Ordine, String> indirizzoColumn;

    @FXML
    private TableColumn<Ordine, String> CAPColumn;

    @FXML
    private TableColumn<Ordine, String> cittaColumn;

    @FXML
    private ComboBox<String> corriereComboBox;

    @FXML
    private ComboBox<String> veicoloComboBox;

    @FXML
    private Button generateDeliveryButton;

    @FXML
    private Label deliveryGeneratedLabel;

    private final ObservableList<Ordine> orderList = FXCollections.observableArrayList();

    private DeliveryControl deliveryControl;

    /**
     * This method is used to initialize the DeliveryController class.
     */
    @FXML
    public void initialize() {
        deliveryControl = DeliveryControl.getInstance();
        deliveryControl.initialize(this);

        setTableColumns();
        setComboBox();
        deliveryControl.updateTable();

        // Listener per la selezione della TableView
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            checkButtonState();
        });

        // Aggiungi listener anche per le ComboBox
        corriereComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            checkButtonState();
        });

        veicoloComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            checkButtonState();
        });
    }

    /**
     * This method is used to update the table.
     * @param orderSupplier The order supplier (lambda expression)
     */
    public void updateTable(Supplier<List<Ordine>> orderSupplier) {
        loadOrders(orderSupplier.get());
    }

    /**
     * This method is used to handle the generate delivery button.
     * @param actionEvent The action event
     */
    public void handleGenerateDelivery(ActionEvent actionEvent) {
        List<Ordine> selectedOrders = orderTable.getSelectionModel().getSelectedItems();
        String corriere = corriereComboBox.getValue();
        String veicolo = veicoloComboBox.getValue();

        deliveryControl.generateDelivery(selectedOrders, corriere, veicolo);
    }

    /**
     * This method is used to show the delivery generated message.
     */
    public void showDeliveryGenerated() {
        deliveryGeneratedLabel.setText("Delivery generata con successo!\nAggiornare la pagina.");
        deliveryGeneratedLabel.setStyle("-fx-text-fill: #2e6f40");
        deliveryGeneratedLabel.setVisible(true);
    }

    /**
     * This method is used to show the delivery not generated message.
     */
    public void showDeliveryNotGenerated() {
        deliveryGeneratedLabel.setText("Errore nella generazione della delivery!\nSi consiglia di aggiornare\nla pagina e riprovare.");
        deliveryGeneratedLabel.setStyle("-fx-text-fill: #ff0000");
        deliveryGeneratedLabel.setVisible(true);
    }

    /**
     * This method is used to set the table columns.
     */
    private void setTableColumns() {
        idOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        dataOrdineColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        clienteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome() + " " + cellData.getValue().getCliente().getCognome()));
        indirizzoColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getVia() + " " + cellData.getValue().getCivico()));
        CAPColumn.setCellValueFactory(new PropertyValueFactory<>("CAP"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("citta"));

        orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
     * This method is used to set the ComboBox of couriers and vehicles.
     */
    private void setComboBox() {
        List<Corriere> corrieri = deliveryControl.getCorrieriDispobibili();
        corriereComboBox.getItems().setAll(corrieri.stream().map(corriere -> corriere.getNome() + " " + corriere.getCognome()).toList());

        List<Veicolo> veicoli = deliveryControl.getVeicoliDisponibili();
        veicoloComboBox.getItems().setAll(veicoli.stream().map(Veicolo::getTarga).toList());
    }

    /**
     * This method is used to check the button state of generate delivery.
     */
    private void checkButtonState() {
        boolean isRowSelected = orderTable.getSelectionModel().getSelectedItem() != null;
        boolean isCorriereSelected = corriereComboBox.getValue() != null;
        boolean isVeicoloSelected = veicoloComboBox.getValue() != null;

        // Abilita o disabilita il bottone in base alle condizioni
        generateDeliveryButton.setDisable(!(isRowSelected && isCorriereSelected && isVeicoloSelected));
    }
}
