package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrdineDAOimp implements OrdineDAO {

    @Override
    public List<Ordine> getAll() {

        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            Statement st = null;
            ResultSet rs = null;

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM ordini");

            while(rs.next()) {
                ordini.add(populateOrdine(rs));
            }

            rs.close();
            st.close();
            con.close();

            return ordini;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    private Ordine populateOrdine(ResultSet rs) {
        try {

            Ordine ordine = null;

            ordine = new Ordine(
                    rs.getString("idordine"),
                    rs.getDate("data_ord").toLocalDate(),
                    rs.getString("via"),
                    rs.getString("civico"),
                    rs.getString("cap"),
                    rs.getString("citta"),
                    rs.getString("cellulare"),
                    rs.getDouble("prezzoTot"),
                    new ClienteDAOimp().getById(rs.getString("idcliente")),
                    new OperatoreDAOimp().getById(rs.getString("idoperatore")),
                    new SpedizioneDAOimp().getById(rs.getString("idspedizione")),
                    rs.getBoolean("consegnato"));

            return ordine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }
}
