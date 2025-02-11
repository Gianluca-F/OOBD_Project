package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Corriere;

import java.util.List;

/**
 * This interface is used to manage the DAO of the Corriere class.
 */
public interface CorriereDAO {
    public Corriere getById(String idCorriere);

    public List<Corriere> getDisponibili();
}
