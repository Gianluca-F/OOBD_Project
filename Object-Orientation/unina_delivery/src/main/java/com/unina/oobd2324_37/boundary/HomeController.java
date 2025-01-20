package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.HomeControl;
import com.unina.oobd2324_37.entity.DTO.Articolo;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

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
        HBox mainLayout = getMainAreaPopUp(selectedOrder);
        Scene scene = new Scene(mainLayout);
        Stage popupStage = new Stage();

        popupStage.setTitle("Dettagli Ordine: " + selectedOrder.getIdOrdine());
        popupStage.setScene(scene);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(orderTable.getScene().getWindow());
        popupStage.setResizable(false);

        popupStage.showAndWait();
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

    /**
     * This method is used to get the main area pop-up.
     * @param selectedOrder The selected order
     * @return The HBox object
     */
    private @NotNull HBox getMainAreaPopUp(Ordine selectedOrder) {
        GridPane leftPane = createLeftPane(selectedOrder);
        VBox rightPane = createRightPane(selectedOrder);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);

        HBox mainLayout = new HBox(20, leftPane, separator, rightPane);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        return mainLayout;
    }

    /**
     * This method is used to create the left pane.
     * @param ordine The order
     * @return The GridPane object
     */
    private GridPane createLeftPane(Ordine ordine) {
        int rowIndex = -1;
        GridPane leftPane = new GridPane();
        leftPane.setHgap(10);
        leftPane.setVgap(10);

        addLabelRow(leftPane, ++rowIndex, "Data:", ordine.getData().toString());
        addLabelRow(leftPane, ++rowIndex, "Cliente:", ordine.getCliente().getNome() + " " + ordine.getCliente().getCognome());
        addLabelRow(leftPane, ++rowIndex, "Cellulare:", ordine.getCellulare());
        addLabelRow(leftPane, ++rowIndex, "E-mail:", ordine.getCliente().getEmail());
        addLabelRow(leftPane, ++rowIndex, "Indirizzo:", ordine.getVia() + " " + ordine.getCivico() + ", " + ordine.getCitta() + " " + ordine.getCAP());
        addLabelRow(leftPane, ++rowIndex, "Stato Consegna:", ordine.isConsegnato() ? "Consegnato (" + ordine.getSpedizione().getData() + ")" : "In Attesa");

        if (ordine.getSpedizione() != null) {
            // Aggiungi le informazioni di spedizione
            addLabelRow(leftPane, ++rowIndex, "A carico di:", ordine.getSpedizione().getCorriere().getNome() + " " + ordine.getSpedizione().getCorriere().getCognome());
            addLabelRow(leftPane, ++rowIndex, "Veicolo adoperato:", ordine.getSpedizione().getVeicolo().getTarga());
            addLabelRow(leftPane, ++rowIndex, "Gestito da:", ordine.getOperatore().getNome() + " " + ordine.getOperatore().getCognome());
        }

        return leftPane;
    }

    /**
     * This method is used to create the right pane.
     * @param ordine The order
     * @return The VBox object
     */
    private VBox createRightPane(Ordine ordine) {
        List<Articolo> articoli = homeControl.getArticoliByIdOrdine(ordine.getIdOrdine());
        int rowIndex = 0;
        double pesoTot = 0.00;
        VBox rightPane = new VBox(10);
        rightPane.setAlignment(Pos.TOP_LEFT);
        Label titleLabel = getLabelWithStyle("Articoli Ordinati:", "-fx-font-weight: bold; -fx-font-size: 17px; -fx-text-fill: #163c65", HPos.LEFT);
        GridPane articoliGrid = getRightGridPane();
        setTableLabel(articoliGrid, rowIndex);
        ++rowIndex;

        for (Articolo articolo : articoli) {
            int quantita = homeControl.getQuantitaArticoloConsegnata(ordine.getIdOrdine(), articolo.getIdArticolo());
            pesoTot += articolo.getPeso() * quantita;

            articoliGrid.add(getLabelWithStyle(articolo.getNome(), "-fx-font-weight: bold;", HPos.LEFT), 0, rowIndex);
            articoliGrid.add(getLabelWithStyle("x" + quantita, "", HPos.RIGHT), 1, rowIndex);
            articoliGrid.add(getLabelWithStyle("€" + String.format("%.2f", articolo.getPrezzo()), "", HPos.RIGHT), 2, rowIndex);
            articoliGrid.add(getLabelWithStyle(String.format("%.2f", articolo.getPeso()) + " Kg", "", HPos.RIGHT), 3, rowIndex);
            articoliGrid.add(getLabelWithStyle(articolo.getDescrizione(), "-fx-font-style: italic;", HPos.LEFT), 4, rowIndex);
            ++rowIndex;
        }
        rightPane.getChildren().addAll(titleLabel, articoliGrid);

        addLabelRow(rightPane, "", ""); // Spazio vuoto
        addLabelRow(rightPane, "Prezzo Totale:", "€" + String.format("%.2f", ordine.getPrezzoTot()));
        addLabelRow(rightPane, "Peso Totale:", String.format("%.2f", pesoTot) + " Kg");

        return rightPane;
    }

    private static @NotNull Label getLabelWithStyle(String text, String style, HPos pos) {
        Label nomeLabel = new Label(text);
        nomeLabel.setStyle(style);
        GridPane.setHalignment(nomeLabel, pos);
        return nomeLabel;
    }

    /**
     * This method is used to set the table label.
     * @param articoliGrid The GridPane object
     * @param rowIndex The row index
     */
    private static void setTableLabel(GridPane articoliGrid, int rowIndex) {
        Label nomLabel = new Label("Nome");
        Label quantitaLabel = new Label("Q.tà");
        Label prezzoLabel = new Label("Prezzo");
        Label pesoLabel = new Label("Peso");
        Label descrizioneLabel = new Label("Descrizione");

        nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
        quantitaLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-alignment: center-right;");
        prezzoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-alignment: center-right;");
        pesoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-alignment: center-right;");
        descrizioneLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");

        articoliGrid.add(nomLabel, 0, rowIndex);
        articoliGrid.add(quantitaLabel, 1, rowIndex);
        articoliGrid.add(prezzoLabel, 2, rowIndex);
        articoliGrid.add(pesoLabel, 3, rowIndex);
        articoliGrid.add(descrizioneLabel, 4, rowIndex);
    }

    /**
     * This method is used to create the right pane.
     * @return The VBox object
     */
    private static @NotNull GridPane getRightGridPane() {
        GridPane articoliGrid = new GridPane();
        articoliGrid.setVgap(5); // Distanza tra le righe
        articoliGrid.setHgap(25); // Distanza tra le colonne
        articoliGrid.setStyle("-fx-padding: 10px;");

        return articoliGrid;
    }

    /**
     * This method is used to add a label row.
     * @param pane The GridPane object
     * @param rowIndex The row index
     * @param labelText The label text
     * @param valueText The value text
     */
    private void addLabelRow(GridPane pane, int rowIndex, String labelText, String valueText) {
        Label label = new Label(labelText);
        Label value = new Label(valueText);

        // Stile custom per le label
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #163c65");

        // Aggiungi le label al pannello
        pane.addRow(rowIndex, label, value);
    }

    /**
     * This method is used to add a label row.
     * @param vBox The VBox object
     * @param labelText The label text
     * @param valueText The value text
     */
    private void addLabelRow(VBox vBox, String labelText, String valueText) {
        Label label = new Label(labelText);
        Label value = new Label(valueText);

        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #163c65");

        HBox row = new HBox(10, label, value);
        vBox.getChildren().add(row);
    }
}
