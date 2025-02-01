package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.ArticoloDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Articolo;
import com.unina.oobd2324_37.entity.utils.StringFormat;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ArticoloDAOimp implements ArticoloDAO {

    @Override
    public List<Articolo> getAll() {
        try {
            Connection con = DBConnection.getInstance();

            List<Articolo> articoli = new LinkedList<>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM articoli ORDER BY idarticolo");
            rs = st.executeQuery();

            while (rs.next()) {
                articoli.add(populateArticolo(rs));
            }

            rs.close();
            st.close();

            return articoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

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

    @Override
    public List<Articolo> getByNome(String articolo) {
        try {
            Connection con = DBConnection.getInstance();

            List<Articolo> articoli = new LinkedList<>();
            PreparedStatement st = null;
            ResultSet rs = null;
            articolo = StringFormat.capitalizeWords(articolo) + "%";

            st = con.prepareStatement("SELECT * FROM articoli WHERE nome LIKE ? ORDER BY idarticolo");
            st.setString(1, articolo);
            rs = st.executeQuery();

            while (rs.next()) {
                articoli.add(populateArticolo(rs));
            }

            rs.close();
            st.close();

            return articoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Articolo> getByDisponibilita() {
        try {
            Connection con = DBConnection.getInstance();

            List<Articolo> articoli = new LinkedList<>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM articoli WHERE quantita > 0 ORDER BY idarticolo");
            rs = st.executeQuery();

            while (rs.next()) {
                articoli.add(populateArticolo(rs));
            }

            rs.close();
            st.close();

            return articoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Articolo> getByNomeNDisponibilita(String articolo) {
        try {
            Connection con = DBConnection.getInstance();

            List<Articolo> articoli = new LinkedList<>();
            PreparedStatement st = null;
            ResultSet rs = null;
            articolo = StringFormat.capitalizeWords(articolo) + "%";

            st = con.prepareStatement("SELECT * FROM articoli WHERE nome LIKE ? AND quantita > 0 ORDER BY idarticolo");
            st.setString(1, articolo);
            rs = st.executeQuery();

            while (rs.next()) {
                articoli.add(populateArticolo(rs));
            }

            rs.close();
            st.close();

            return articoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Articolo articolo) {
        try {
            Connection con = DBConnection.getInstance();

            PreparedStatement st = null;

            st = con.prepareStatement("UPDATE articoli SET quantita = ? WHERE idarticolo = ?");
            st.setInt(1, articolo.getQuantita());
            st.setString(2, articolo.getIdArticolo());
            st.executeUpdate();

            st.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
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