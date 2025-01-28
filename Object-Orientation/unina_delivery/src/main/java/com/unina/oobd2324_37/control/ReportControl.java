package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.ReportController;
import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;

public class ReportControl {

    private static ReportControl instance = null;
    private ReportController reportController;

    private ReportControl() {}

    public static ReportControl getInstance() {
        if(instance == null) {
            instance = new ReportControl();
        }
        return instance;
    }

    public void initialize(ReportController reportController) {
        this.reportController = reportController;
    }

    public void generateReport(int month, int year) {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        double avgOrders = ordineDAO.getAverageOrders(month, year);
        reportController.setAvgOrdersLabel(avgOrders);
    }
}
