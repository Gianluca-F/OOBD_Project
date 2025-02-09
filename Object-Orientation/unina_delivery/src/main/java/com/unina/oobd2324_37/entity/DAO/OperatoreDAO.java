package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Operatore;

public interface OperatoreDAO {
    public Operatore getByUserNPass(String username, String password) throws NullPointerException;

    public Operatore getById(String idOperatore);
}
