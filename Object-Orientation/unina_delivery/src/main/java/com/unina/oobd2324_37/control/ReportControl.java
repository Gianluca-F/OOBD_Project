package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.ReportController;

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

    public void generateReport() {
    }
}
