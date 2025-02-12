package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.ReportControl;
import com.unina.oobd2324_37.entity.DTO.Ordine;
import com.unina.oobd2324_37.entity.utils.PopUpOrderFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;

import java.util.stream.IntStream;
import java.util.Locale;

/**
 * This class is used to manage the report controller.
 */
public class ReportController {

    @FXML
    private ComboBox<String> monthSelector;

    @FXML
    private ComboBox<Integer> yearSelector;

    @FXML
    private Label avgOrdersLabel;

    @FXML
    private Label maxProductsOrderLabel;

    @FXML
    private Label minProductsOrderLabel;

    @FXML
    private Button viewDetailsMaxProductsOrderButton;

    @FXML
    private Button viewDetailsMinProductsOrderButton;

    private ReportControl reportControl;

    /**
     * This method is used to initialize the report controller.
     */
    @FXML
    public void initialize() {
        reportControl = ReportControl.getInstance();
        reportControl.initialize(this);

        for (Month month : Month.values()) {
            String monthName = month.getDisplayName(java.time.format.TextStyle.FULL_STANDALONE, Locale.ITALIAN);
            monthSelector.getItems().add(monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase());
        }
        monthSelector.setVisibleRowCount(6);

        int firstOperativeYear = 2023;
        int currentYear = LocalDate.now().getYear();

        IntStream.range(firstOperativeYear, currentYear + 1).boxed().forEach(yearSelector.getItems()::add);
    }

    /**
     * This method is used to handle the generate report action.
     * @param actionEvent The action event
     */
    public void handleGenerateReport(ActionEvent actionEvent) {
        String selectedMonth = monthSelector.getValue();
        Integer selectedYear = yearSelector.getValue();

        if (selectedMonth == null || selectedYear == null) {
            return;
        }

        try {
            reportControl.generateReport(getMonthNumber(selectedMonth), selectedYear);
        } catch (Exception e) {
            System.err.println("Errore nella generazione del report: " + e.getMessage());
        }
    }

    /**
     * This method is used to handle the view details action.
     * @param actionEvent The action event
     */
    public void handleViewDetailsMaxProductsOrder(ActionEvent actionEvent) {
        String selectedMonth = monthSelector.getValue();
        Integer selectedYear = yearSelector.getValue();

        if (selectedMonth == null || selectedYear == null) {
            return;
        }

        try {
            reportControl.viewDetailsMajorOrder(getMonthNumber(selectedMonth), selectedYear);
        } catch (Exception e) {
            System.err.println("Errore nella visualizzazione dei dettagli dell'ordine maggiore: " + e.getMessage());
        }
    }

    /**
     * This method is used to handle the view details action.
     * @param selectedOrder The selected order
     */
    public void handleViewDetailsMinProductsOrder(ActionEvent actionEvent) {
        String selectedMonth = monthSelector.getValue();
        Integer selectedYear = yearSelector.getValue();

        if (selectedMonth == null || selectedYear == null) {
            return;
        }

        try {
            reportControl.viewDetailsMinorOrder(getMonthNumber(selectedMonth), selectedYear);
        } catch (Exception e) {
            System.err.println("Errore nella visualizzazione dei dettagli dell'ordine minore: " + e.getMessage());
        }
    }

    /**
     * This method is used to handle the view details action.
     * @param selectedOrder The selected order
     */
    public void handleViewDetails(Ordine selectedOrder) {
        new PopUpOrderFormat().createPopUp(selectedOrder, avgOrdersLabel.getScene());
    }

    /**
     * This method is used to set the average orders label.
     * @param avgOrders The average orders
     */
    public void setAvgOrdersLabel(double avgOrders) {
        avgOrdersLabel.setText(String.format("%.2f", avgOrders));
    }

    /**
     * This method is used to set the max products order label.
     * @param maxProductsOrder The max products order
     */
    public void setMaxProductsOrderLabel(String maxProductsOrder) {
        maxProductsOrderLabel.setText(maxProductsOrder == null || maxProductsOrder.isEmpty() ? "N/A" : maxProductsOrder);
        viewDetailsMaxProductsOrderButton.setDisable(maxProductsOrder == null || maxProductsOrder.isEmpty());
    }

    /**
     * This method is used to set the min products order label.
     * @param minProductsOrder The min products order
     */
    public void setMinProductsOrderLabel(String minProductsOrder) {
        minProductsOrderLabel.setText(minProductsOrder == null || minProductsOrder.isEmpty() ? "N/A" : minProductsOrder);
        viewDetailsMinProductsOrderButton.setDisable(minProductsOrder == null || minProductsOrder.isEmpty());
    }

    /**
     * This method is used to get the month number.
     * @param month The month
     */
    private int getMonthNumber(String month) {
        return switch (month) {
            case "Gennaio" -> 1;
            case "Febbraio" -> 2;
            case "Marzo" -> 3;
            case "Aprile" -> 4;
            case "Maggio" -> 5;
            case "Giugno" -> 6;
            case "Luglio" -> 7;
            case "Agosto" -> 8;
            case "Settembre" -> 9;
            case "Ottobre" -> 10;
            case "Novembre" -> 11;
            default -> 12;
        };
    }
}
