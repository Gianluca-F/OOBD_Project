package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.WarehouseControl;
import com.unina.oobd2324_37.entity.DTO.Articolo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Supplier;

/**
 * This class is used to manage the WarehouseController.
 */
public class WarehouseController {

    @FXML
    private TextField productFilterField;

    @FXML
    private CheckBox availabilityCheckBox;

    @FXML
    private TableView<Articolo> productTable;

    @FXML
    private TableColumn<Articolo, String> idArticoloColumn;

    @FXML
    private TableColumn<Articolo, String> nomeColumn;

    @FXML
    private TableColumn<Articolo, String> descrizioneColumn;

    @FXML
    private TableColumn<Articolo, Integer> quantitaColumn;

    @FXML
    private TableColumn<Articolo, Double> prezzoColumn;

    @FXML
    private TableColumn<Articolo, Double> pesoColumn;

    @FXML
    private Button editButton;

    private final ObservableList<Articolo> productList = FXCollections.observableArrayList();

    private WarehouseControl warehouseControl;

    @FXML
    private void initialize() {

        warehouseControl = WarehouseControl.getInstance();
        warehouseControl.initialize(this);

        setTableColumns();
        warehouseControl.applyFilter(null, false);

        // Disabilita il pulsante se non ci sono righe selezionate
        editButton.disableProperty().bind(
                productTable.getSelectionModel().selectedItemProperty().isNull()
        );

        // Resetta la selezione quando il TableView o VisualizzaOrdine peronoe il focus
        productTable.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!productTable.isFocused() && !editButton.isFocused()) {
                    productTable.getSelectionModel().clearSelection();
                }
            }
        });

        // Configura la colonna Quantità
        quantitaColumn.setCellFactory(column -> new TableCell<Articolo, Integer>() {
            private final TextField textField = new TextField();
            private final Button incrementButton = new Button("+");
            private final Button decrementButton = new Button("-");
            private final HBox hBox = new HBox(decrementButton, textField, incrementButton);

            {
                hBox.setSpacing(5);
                textField.setPrefWidth(50);

                // Rimuovi gli stili personalizzati
                incrementButton.getStyleClass().clear();
                decrementButton.getStyleClass().clear();
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    textField.setText(String.valueOf(item));

                    incrementButton.setOnAction(event -> {
                        Articolo articolo = getTableView().getItems().get(getIndex());
                        int newQuantity = articolo.getQuantita() + 1;
                        articolo.setQuantita(newQuantity);
                        textField.setText(String.valueOf(newQuantity));
                        getTableView().refresh();
                    });

                    decrementButton.setOnAction(event -> {
                        Articolo articolo = getTableView().getItems().get(getIndex());
                        int newQuantity = Math.max(0, articolo.getQuantita() - 1);
                        articolo.setQuantita(newQuantity);
                        textField.setText(String.valueOf(newQuantity));
                        getTableView().refresh();
                    });

                    textField.setOnAction(event -> {
                        try {
                            int newQuantity = Integer.parseInt(textField.getText());
                            Articolo articolo = getTableView().getItems().get(getIndex());
                            articolo.setQuantita(newQuantity);
                            getTableView().refresh();
                        } catch (NumberFormatException e) {
                            textField.setText("0");
                        }
                    });

                    setGraphic(hBox);
                }
            }
        });

        // Configura il pulsante di modifica
        editButton.setOnAction(event -> handleEditProduct());
    }

    /**
     * This method is used to apply the filter.
     * @param actionEvent The action event
     */
    public void handleApplyFilter(ActionEvent actionEvent) {
        warehouseControl.applyFilter(productFilterField.getText(), availabilityCheckBox.isSelected());
    }

    /**
     * This method is used to reset the filter.
     * @param actionEvent The action event
     */
    public void handleResetFilter(ActionEvent actionEvent) {
        productFilterField.clear();
        availabilityCheckBox.setSelected(false);
    }

    /**
     * This method is used to handle the edit (quantity) product.
     */
    public void handleEditProduct() {
        Articolo selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Salva nel database (simulazione)
            System.out.println("Salvata modifica: " + selectedProduct.getQuantita());
            // Qui puoi aggiungere la logica per aggiornare il database
        }
    }

    /**
     * This method is used to update the table.
     * @param productSupplier The order supplier (lambda expression)
     */
    public void updateTable(Supplier<List<Articolo>> productSupplier) {
        loadProducts(productSupplier.get());
    }

    /**
     * This method is used to set the table columns.
     */
    private void setTableColumns() {
        // Column initialization
        idArticoloColumn.setCellValueFactory(new PropertyValueFactory<>("idArticolo"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        quantitaColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));

        // Personalizzazione della colonna "Peso"
        setPesoColumn();

        pesoColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));

        // Personalizzazione della colonna "Prezzo"
        setPrezzoColumn();

        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
    }

    /**
     * This method is used to set the "Peso" column.
     */
    private void setPesoColumn() {
        pesoColumn.setCellFactory(new Callback<TableColumn<Articolo, Double>, TableCell<Articolo, Double>>() {
            @Override
            public TableCell<Articolo, Double> call(TableColumn<Articolo, Double> param) {
                return new TableCell<Articolo, Double>() {
                    private final DecimalFormat df = new DecimalFormat("0.00 Kg ");

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });
    }

    /**
     * This method is used to set the "Prezzo" column.
     */
    private void setPrezzoColumn() {
        prezzoColumn.setCellFactory(new Callback<TableColumn<Articolo, Double>, TableCell<Articolo, Double>>() {
            @Override
            public TableCell<Articolo, Double> call(TableColumn<Articolo, Double> param) {
                return new TableCell<Articolo, Double>() {
                    private final DecimalFormat df = new DecimalFormat("€ 0.00 ");

                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });
    }

    /**
     * This method is used to load the products in the table.
     * @param articoli The list of products
     */
    private void loadProducts(List<Articolo> articoli) {
        productList.clear();
        productList.addAll(articoli);
        productTable.setItems(productList);
    }
}
