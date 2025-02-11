package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Operatore;
import com.unina.oobd2324_37.entity.DTO.Ordine;
import com.unina.oobd2324_37.entity.DTO.Spedizione;

import java.util.List;

/**
 * This interface is used to manage the DAO of the Spedizione class.
 */
public interface SpedizioneDAO {
    public Spedizione getById(String idSpedizione);

    public boolean insert(Operatore operatore, List<Ordine> ordini, String idCorriere, String idVeicolo);
}
