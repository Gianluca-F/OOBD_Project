package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Operatore;
import java.sql.SQLException;

public interface OperatoreDAO {
    public Operatore getByEmailNPass(String username, String password) throws SQLException;
}
