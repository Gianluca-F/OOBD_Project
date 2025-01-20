package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Articolo;
import com.unina.oobd2324_37.entity.DTO.CompOrdine;

import java.util.List;

public interface CompOrdineDAO {
    public List<CompOrdine> getByIdOrdine(String idOrdine);

    public List<Articolo> getArticoliByIdOrdine(String idOrdine);

    CompOrdine getByIdOrdineNIdArticolo(String idOrdine, String idArticolo);
}
