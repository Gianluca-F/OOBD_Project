package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Operatore;

/**
 * This interface is used to manage the DAO of the Operatore class.
 */
public interface OperatoreDAO {
    public Operatore getByUserNPass(String username, String password) throws NullPointerException;

    public Operatore getById(String idOperatore);
}
