package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.CompOrdineDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Articolo;
import com.unina.oobd2324_37.entity.DTO.CompOrdine;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompOrdineDAOimp implements CompOrdineDAO {

    @Override
    public List<CompOrdine> getByIdOrdine(String idOrdine) {
        try {
            Connection con = DBConnection.getInstance();

            List<CompOrdine> composizioneOrdine = new LinkedList<CompOrdine>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM compordine WHERE idOrdine = ?");
            st.setString(1, idOrdine);
            rs = st.executeQuery();

            while(rs.next()) {
                composizioneOrdine.add(populateComposizione(rs));
            }

            rs.close();
            st.close();

            return composizioneOrdine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    @Override
    public List<Articolo> getArticoliByIdOrdine(String idOrdine) {
        try {
            Connection con = DBConnection.getInstance();

            List<Articolo> articoli = new LinkedList<Articolo>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM compordine WHERE idOrdine = ?");
            st.setString(1, idOrdine);
            rs = st.executeQuery();

            while(rs.next()) {
                articoli.add(new ArticoloDAOimp().getById(rs.getString("idArticolo")));
            }

            rs.close();
            st.close();

            return articoli;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    @Override
    public CompOrdine getByIdOrdineNIdArticolo(String idOrdine, String idArticolo) {
        try {
            Connection con = DBConnection.getInstance();

            CompOrdine compOrdine = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM compordine WHERE idOrdine = ? AND idArticolo = ?");
            st.setString(1, idOrdine);
            st.setString(2, idArticolo);
            rs = st.executeQuery();

            if (rs.next()) {
                compOrdine = populateComposizione(rs);
            }

            rs.close();
            st.close();

            return compOrdine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    private CompOrdine populateComposizione(ResultSet rs) {
        try {

            CompOrdine compOrdine = null;

            compOrdine = new CompOrdine(
                    new OrdineDAOimp().getById(rs.getString("idOrdine")),
                    new ArticoloDAOimp().getById(rs.getString("idArticolo")),
                    rs.getInt("quantita"),
                    rs.getDouble("pesoPar"),
                    rs.getDouble("prezzoPar")
            );

            return compOrdine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }
}
