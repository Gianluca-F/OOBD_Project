package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Ordine;

import java.time.LocalDate;
import java.util.List;

public interface OrdineDAO {
    public List<Ordine> getAll();

    public List<Ordine> getByCustomerAndDate(String customer, LocalDate startDate, LocalDate endDate);
}
