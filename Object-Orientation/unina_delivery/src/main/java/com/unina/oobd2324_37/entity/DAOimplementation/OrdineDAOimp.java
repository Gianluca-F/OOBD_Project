package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Ordine;

import java.sql.*;
import java.time.LocalDate;
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
            rs = st.executeQuery("SELECT * FROM ordini ORDER BY idordine ASC");

            while(rs.next()) {
                ordini.add(populateOrdine(rs));
            }

            rs.close();
            st.close();

            return ordini;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    @Override
    public List<Ordine> getByCustomerAndDate(String customer, LocalDate startDate, LocalDate endDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM ordini AS O NATURAL JOIN (SELECT idcliente, nome, cognome FROM clienti) AS C " +
                                            "WHERE (C.nome LIKE ? OR C.cognome LIKE ?) AND " +
                                            "O.data_ord BETWEEN ? AND ? " +
                                            "ORDER BY idordine ASC");
            customer += "%";
            st.setString(1, customer);
            st.setString(2, customer);
            st.setDate(3, Date.valueOf(startDate));
            st.setDate(4, Date.valueOf(endDate));
            rs = st.executeQuery();

            while(rs.next()) {
                ordini.add(populateOrdine(rs));
            }

            rs.close();
            st.close();

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
                    rs.getString("idoperatore") != null ?
                            new OperatoreDAOimp().getById(rs.getString("idoperatore")) :
                            null,
                    rs.getString("idspedizione") != null ?
                            new SpedizioneDAOimp().getById(rs.getString("idspedizione")) :
                            null,
                    rs.getBoolean("consegnato"));

            return ordine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }
}
