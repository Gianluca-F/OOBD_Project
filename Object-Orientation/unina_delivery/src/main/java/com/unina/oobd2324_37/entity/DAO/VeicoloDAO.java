package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Veicolo;

import java.util.List;

/**
 * Interfaccia per la gestione dei veicoli
 */
public interface VeicoloDAO {
    public Veicolo getByTarga(String targa);

    public List<Veicolo> getDisponibili();
}