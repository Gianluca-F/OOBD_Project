package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Veicolo;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VeicoloDAO {
    public Veicolo getByTarga(String targa);

}