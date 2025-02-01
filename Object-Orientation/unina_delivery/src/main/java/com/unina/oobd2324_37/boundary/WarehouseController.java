package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.WarehouseControl;
import com.unina.oobd2324_37.entity.DTO.Articolo;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;

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

    @FXML
    private Label editLabel;

    private boolean isAnimationEditLabelRunning = false;

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
        setQuantityColumn();

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
            warehouseControl.editProductQuantity(selectedProduct);
        }
    }

    /**
     * This method is used to set the edit label.
     * @param text The text of the label
     */
    public void setEditLabel(String text) {
        if(!isAnimationEditLabelRunning) {
            showTemporaryMessageWithFade(editLabel, text, 3);
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

    /**
     * This method is used to show a temporary message with fade effect.
     * @param label The label
     * @param seconds The seconds
     */
    private void showTemporaryMessageWithFade(Label label, String text, int seconds) {
        isAnimationEditLabelRunning = true;

        label.setText(text);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeIn.setOnFinished(event -> {
            FadeTransition delayFadeOut = new FadeTransition(Duration.seconds(0.5), label);
            delayFadeOut.setFromValue(1);
            delayFadeOut.setToValue(0);
            delayFadeOut.setDelay(Duration.seconds(seconds));
            delayFadeOut.play();
            delayFadeOut.setOnFinished(e -> isAnimationEditLabelRunning = false);
        });

        fadeIn.play();
    }

    /**
     * This method is used to set the "Quantità" column.
     */
    private void setQuantityColumn() {
        quantitaColumn.setCellFactory(column -> new TableCell<Articolo, Integer>() {
            private final TextField textField = new TextField();
            private final Button incrementButton = new Button("+");
            private final Button decrementButton = new Button("-");
            private final HBox hBox = new HBox(decrementButton, textField, incrementButton);

            {
                hBox.setSpacing(5);
                hBox.setAlignment(javafx.geometry.Pos.CENTER); // Centra gli elementi
                textField.setPrefWidth(50);

                incrementButton.getStyleClass().clear();
                decrementButton.getStyleClass().clear();
                incrementButton.setStyle("-fx-font-size: 14px; -fx-padding: 3px;");
                decrementButton.setStyle("-fx-font-size: 14px; -fx-padding: 3px;");
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
                            if(newQuantity < 0) {
                                newQuantity = 0;
                            }
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
    }
}
