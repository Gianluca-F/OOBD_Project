package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Corriere;

/**
 * This interface is used to manage the DAO of the Corriere class.
 */
public interface CorriereDAO {
    public Corriere getById(String idCorriere);
}
