package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Ordine;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface is used to manage the DAO of the Ordine class.
 */
public interface OrdineDAO {

    public List<Ordine> getAll();

    public List<Ordine> getByCustomerAndDate(String customer, LocalDate startDate, LocalDate endDate);

    public List<Ordine> getByCustomer(String customer);

    public List<Ordine> getByStartDate(LocalDate startdate);

    public List<Ordine> getByEndDate(LocalDate endDate);

    public List<Ordine> getByCustomerAndStartDate(String customer, LocalDate startDate);

    public List<Ordine> getByCustomerAndEndDate(String customer, LocalDate endDate);

    public List<Ordine> getByDate(LocalDate startDate, LocalDate endDate);
}
