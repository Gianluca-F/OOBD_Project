package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.ReportController;
import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;

/**
 * This class is used to manage the report control.
 */
public class ReportControl {

    private static ReportControl instance = null;
    private ReportController reportController;

    /**
     * Private constructor to avoid instantiation.
     */
    private ReportControl() {}

    /**
     * This method is used to get the instance (singleton) of the ReportControl class.
     * @return The instance of the ReportControl class
     */
    public static ReportControl getInstance() {
        if(instance == null) {
            instance = new ReportControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the ReportControl class.
     */
    public void initialize(ReportController reportController) {
        this.reportController = reportController;
    }

    /**
     * This method is used to generate the report.
     * @param month The month of the report
     * @param year The year of the report
     */
    public void generateReport(int month, int year) {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        double avgOrders = ordineDAO.getAverageOrders(month, year);
        reportController.setAvgOrdersLabel(avgOrders);
        reportController.setMaxProductsOrderLabel(ordineDAO.getMaxProductsOrder(month, year));
        reportController.setMinProductsOrderLabel(ordineDAO.getMinProductsOrder(month, year));
    }

    /**
     * This method is used to view the details of the major order.
     * @param month The month of the report
     * @param year The year of the report
     */
    public void viewDetailsMajorOrder(int month, int year) {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        reportController.handleViewDetails(ordineDAO.getById(ordineDAO.getMaxProductsOrder(month, year)));
    }

    /**
     * This method is used to view the details of the minor order.
     * @param monthNumber The month of the report
     * @param selectedYear The year of the report
     */
    public void viewDetailsMinorOrder(int monthNumber, Integer selectedYear) {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        reportController.handleViewDetails(ordineDAO.getById(ordineDAO.getMinProductsOrder(monthNumber, selectedYear)));
    }
}
