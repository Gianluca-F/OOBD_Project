package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.ArticoloDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Articolo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ArticoloDAOimp implements ArticoloDAO {
    @Override
    public Articolo getById(String id) {
        try {
            Connection con = DBConnection.getInstance();

            Articolo articolo = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM articoli WHERE idarticolo = ?");
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                articolo = populateArticolo(rs);
            }

            rs.close();
            st.close();

            return articolo;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private Articolo populateArticolo(ResultSet rs) {
        try {

            Articolo articolo = null;

            articolo = new Articolo(
                    rs.getString("idArticolo"),
                    rs.getString("nome"),
                    rs.getString("descrizione"),
                    rs.getInt("quantita"),
                    rs.getDouble("peso"),
                    rs.getDouble("prezzo")
            );

            return articolo;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }
}