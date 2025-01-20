package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.HomeController;
import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.CompOrdineDAOimp;
import com.unina.oobd2324_37.entity.DAOimplementation.OrdineDAOimp;
import com.unina.oobd2324_37.entity.DTO.Articolo;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is used to manage the HomeControl class.
 */
public class HomeControl {

    private static HomeControl instance = null;
    private HomeController homeController;

    /**
     * Private constructor to avoid instantiation.
     */
    private HomeControl() {}

    /**
     * This method is used to get the instance (singleton) of the HomeControl class.
     * @return The instance of the HomeControl class
     */
    public static HomeControl getInstance() {
        if(instance == null) {
            instance = new HomeControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the HomeControl class.
     * @param homeController The instance of HomeController class
     */
    public void initialize(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * This method is used to apply the filter to the table.
     * @param customer The customer name
     * @param startDate The start date
     * @param endDate The end date
     */
    public void applyFilter(String customer, LocalDate startDate, LocalDate endDate) {
        OrdineDAO ordineDAO = new OrdineDAOimp();

        // without filter
        if(!isClienteValid(customer) && startDate == null && endDate == null) {
            homeController.updateTable(ordineDAO::getAll);
            return;
        }

        // with just customer filter
        if(isClienteValid(customer) && startDate == null && endDate == null) {
            homeController.updateTable(() -> ordineDAO.getByCustomer(customer));
            return;
        }

        // with just start date filter
        if(!isClienteValid(customer) && startDate != null && endDate == null) {
            homeController.updateTable(() -> ordineDAO.getByStartDate(startDate));
            return;
        }

        // with just end date filter
        if(!isClienteValid(customer) && startDate == null && endDate != null) {
            homeController.updateTable(() -> ordineDAO.getByEndDate(endDate));
            return;
        }

        // with customer and start date filters
        if(isClienteValid(customer) && startDate != null && endDate == null) {
            homeController.updateTable(() -> ordineDAO.getByCustomerAndStartDate(customer, startDate));
            return;
        }

        // with customer and end date filters
        if(isClienteValid(customer) && startDate == null && endDate != null) {
            homeController.updateTable(() -> ordineDAO.getByCustomerAndEndDate(customer, endDate));
            return;
        }

        // with start date and end date filters
        if(!isClienteValid(customer) && startDate != null && endDate != null) {
            homeController.updateTable(() -> ordineDAO.getByDate(startDate, endDate));
            return;
        }

        // with all filters
        if(isClienteValid(customer) && startDate != null && endDate != null) {
            homeController.updateTable(() -> ordineDAO.getByCustomerAndDate(customer, startDate, endDate));
        }
    }

    public List<Articolo> getArticoliByIdOrdine(String idOrdine) {
        return new CompOrdineDAOimp().getArticoliByIdOrdine(idOrdine);
    }

    /**
     * This method is used to check if the customer is valid.
     * @param cliente The customer name
     * @return True if the customer is valid, false otherwise
     */
    private boolean isClienteValid(String cliente) {
        return cliente != null && !cliente.isEmpty();
    }

    public int getQuantitaArticoloConsegnata(String idOrdine, String idArticolo) {
        return new CompOrdineDAOimp().getByIdOrdineNIdArticolo(idOrdine, idArticolo).getQuantita();
    }
}
