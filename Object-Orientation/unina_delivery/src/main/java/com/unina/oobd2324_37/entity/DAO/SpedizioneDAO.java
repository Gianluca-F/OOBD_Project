package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Spedizione;

/**
 * This interface is used to manage the DAO of the Spedizione class.
 */
public interface SpedizioneDAO {
    public Spedizione getById(String idSpedizione);
}
