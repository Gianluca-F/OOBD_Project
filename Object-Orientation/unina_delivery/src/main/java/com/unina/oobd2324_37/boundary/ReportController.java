package com.unina.oobd2324_37.boundary;

import com.unina.oobd2324_37.control.ReportControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;

import java.util.stream.IntStream;
import java.util.Locale;

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

    private ReportControl reportControl;

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


    public void handleGenerateReport(ActionEvent actionEvent) {
    }
}
