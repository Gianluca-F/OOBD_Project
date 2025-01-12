package com.unina.oobd2324_37.entity.DAO;

import com.unina.oobd2324_37.entity.DTO.Veicolo;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VeicoloDAO {
/*
    public void insertVeicolo(Veicolo veicolo) throws SQLException;

    public void updateVeicolo(Veicolo veicolo, String targa) throws SQLException;

    public void deleteVeicolo(String targa) throws SQLException;

    public ArrayList<Veicolo> getAll() throws SQLException;
*/
    public Veicolo getByTarga(String targa);
/*
    public ArrayList<Veicolo> getByGEPesoSupp(double pesoSupportato) throws SQLException;
    // GE = greater equal
    public ArrayList<Veicolo> getByGEPesoSuppAndDisponibilita(double pesoSupportato, boolean disponibilita) throws SQLException;

    public ArrayList<Veicolo> getByDisponibilita(boolean disponibilita) throws SQLException;
*/
}