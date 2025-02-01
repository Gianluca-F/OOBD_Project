package com.unina.oobd2324_37.entity.utils;

import com.unina.oobd2324_37.entity.DAOimplementation.CompOrdineDAOimp;
import com.unina.oobd2324_37.entity.DTO.Articolo;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopUpOrderFormat {

    /**
     * This method is used to create the pop-up.
     * @param selectedOrder The selected order
     * @param scene2 The scene
     */
    public void createPopUp(Ordine selectedOrder, Scene scene2) {
        HBox mainLayout = getMainAreaPopUp(selectedOrder);
        Scene scene = new Scene(mainLayout);
        Stage popupStage = new Stage();

        popupStage.setTitle("Dettagli Ordine: " + selectedOrder.getIdOrdine());
        popupStage.setScene(scene);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(scene2.getWindow());
        popupStage.setResizable(false);

        popupStage.showAndWait();
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
        List<Articolo> articoli = getArticoliByIdOrdine(ordine.getIdOrdine());
        int rowIndex = 0;
        double pesoTot = 0.00;
        VBox rightPane = new VBox(10);
        rightPane.setAlignment(Pos.TOP_LEFT);
        Label titleLabel = getLabelWithStyle("Articoli Ordinati:", "-fx-font-weight: bold; -fx-font-size: 17px; -fx-text-fill: #163c65", HPos.LEFT);
        GridPane articoliGrid = getRightGridPane();
        setTableLabel(articoliGrid, rowIndex);
        ++rowIndex;

        for (Articolo articolo : articoli) {
            int quantita = getQuantitaArticoloConsegnata(ordine.getIdOrdine(), articolo.getIdArticolo());
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

    /**
     * This method is used to get the quantity of the delivered article.
     * @param idOrdine The order id
     * @param idArticolo The article id
     * @return The quantity of the delivered article
     */
    private int getQuantitaArticoloConsegnata(String idOrdine, String idArticolo) {
        return new CompOrdineDAOimp().getByIdOrdineNIdArticolo(idOrdine, idArticolo).getQuantita();
    }

    /**
     * This method is used to get the articles by order id.
     * @param idOrdine The order id
     * @return The list of articles
     */
    private List<Articolo> getArticoliByIdOrdine(String idOrdine) {
        return new CompOrdineDAOimp().getArticoliByIdOrdine(idOrdine);
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
