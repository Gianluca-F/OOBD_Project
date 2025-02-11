package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.VeicoloDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Veicolo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to interact with the database and retrieve the vehicle data.
 */
public class VeicoloDAOimp implements VeicoloDAO {

    @Override
    public Veicolo getByTarga(String targa) {

        try {
            Connection con = DBConnection.getInstance();
            Veicolo veicolo = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            ps = con.prepareStatement("SELECT * FROM veicoli WHERE targa = ?");
            ps.setString(1, targa);
            rs = ps.executeQuery();

            if(rs.next()) {
                veicolo = populateVeicolo(rs);
            }

            rs.close();
            ps.close();

            return veicolo;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Veicolo> getDisponibili() {
        try {
            Connection con = DBConnection.getInstance();

            List<Veicolo> veicoli = new LinkedList<>();
            PreparedStatement ps = null;
            ResultSet rs = null;

            ps = con.prepareStatement("SELECT * FROM veicoli WHERE disponibilita = true");
            rs = ps.executeQuery();

            while(rs.next()) {
                veicoli.add(populateVeicolo(rs));
            }

            rs.close();
            ps.close();

            return veicoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private Veicolo populateVeicolo(ResultSet rs) {
        try {
            Veicolo veicolo = null;

            veicolo = new Veicolo(
                    rs.getString("targa"),
                    rs.getDouble("pesoSupportato"),
                    rs.getBoolean("disponibilita"));

            return veicolo;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

}
