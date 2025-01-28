package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Ordine;
import com.unina.oobd2324_37.entity.utils.StringFormat;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to interact with the database and retrieve the orders.
 */
public class OrdineDAOimp implements OrdineDAO {

    /**
     * This method is used to get all the orders from the database.
     * @return A list of all the orders
     */
    @Override
    public List<Ordine> getAll() {

        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            Statement st = null;
            ResultSet rs = null;

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM ordini ORDER BY idordine DESC");

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
    public Ordine getById(String id) {
        try {
            Connection con = DBConnection.getInstance();
            Ordine ordine = null;
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM ordini WHERE idordine = ?");
            st.setString(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                ordine = populateOrdine(rs);
            }

            rs.close();
            st.close();

            return ordine;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    /**
     * This method is used to get the orders by customer and date.
     * @param customer The name of the customer
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of orders filtered by customer and date
     */
    @Override
    public List<Ordine> getByCustomerAndDate(String customer, LocalDate startDate, LocalDate endDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;
            customer = StringFormat.capitalizeWords(customer) + "%";

            st = con.prepareStatement("SELECT * FROM ordini AS O NATURAL JOIN (SELECT idcliente, nome, cognome FROM clienti) AS C " +
                                            "WHERE (C.nome LIKE ? OR C.cognome LIKE ?) AND " +
                                            "O.data_ord BETWEEN ? AND ? " +
                                            "ORDER BY idordine DESC");

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

    /**
     * This method is used to get the orders by customer.
     * @param customer The name of the customer
     * @return A list of orders filtered by customer
     */
    @Override
    public List<Ordine> getByCustomer(String customer) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;
            customer = StringFormat.capitalizeWords(customer) + "%";

            st = con.prepareStatement("SELECT * FROM ordini AS O NATURAL JOIN (SELECT idcliente, nome, cognome FROM clienti) AS C " +
                                            "WHERE C.nome LIKE ? OR C.cognome LIKE ? " +
                                            "ORDER BY idordine DESC");

            st.setString(1, customer);
            st.setString(2, customer);
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

    /**
     * This method is used to get the orders by start date.
     * @param startdate The start date
     * @return A list of orders filtered by start date
     */
    @Override
    public List<Ordine> getByStartDate(LocalDate startdate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM ordini WHERE data_ord >= ? ORDER BY idordine DESC");

            st.setDate(1, Date.valueOf(startdate));
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

    /**
     * This method is used to get the orders by end date.
     * @param endDate The end date
     * @return A list of orders filtered by end date
     */
    @Override
    public List<Ordine> getByEndDate(LocalDate endDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM ordini WHERE data_ord <= ? ORDER BY idordine DESC");

            st.setDate(1, Date.valueOf(endDate));
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

    /**
     * This method is used to get the orders by customer and start date.
     * @param customer The name of the customer
     * @param startDate The start date
     * @return A list of orders filtered by customer and start date
     */
    @Override
    public List<Ordine> getByCustomerAndStartDate(String customer, LocalDate startDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;
            customer = StringFormat.capitalizeWords(customer) + "%";

            st = con.prepareStatement("SELECT * FROM ordini AS O NATURAL JOIN (SELECT idcliente, nome, cognome FROM clienti) AS C " +
                                            "WHERE (C.nome LIKE ? OR C.cognome LIKE ?) AND " +
                                            "O.data_ord >= ? " +
                                            "ORDER BY idordine DESC");

            st.setString(1, customer);
            st.setString(2, customer);
            st.setDate(3, Date.valueOf(startDate));
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

    /**
     * This method is used to get the orders by customer and end date.
     * @param customer The name of the customer
     * @param endDate The end date
     * @return A list of orders filtered by customer and end date
     */
    @Override
    public List<Ordine> getByCustomerAndEndDate(String customer, LocalDate endDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;
            customer = StringFormat.capitalizeWords(customer) + "%";

            st = con.prepareStatement("SELECT * FROM ordini AS O NATURAL JOIN (SELECT idcliente, nome, cognome FROM clienti) AS C " +
                                            "WHERE (C.nome LIKE ? OR C.cognome LIKE ?) AND " +
                                            "O.data_ord <= ? " +
                                            "ORDER BY idordine DESC");

            st.setString(1, customer);
            st.setString(2, customer);
            st.setDate(3, Date.valueOf(endDate));
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

    /**
     * This method is used to get the orders by date.
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of orders filtered by date
     */
    @Override
    public List<Ordine> getByDate(LocalDate startDate, LocalDate endDate) {
        try {
            Connection con = DBConnection.getInstance();

            List<Ordine> ordini = new LinkedList<Ordine>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM ordini WHERE data_ord BETWEEN ? AND ? ORDER BY idordine DESC");

            st.setDate(1, Date.valueOf(startDate));
            st.setDate(2, Date.valueOf(endDate));
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

    /**
     * This method is used to get the average orders in a determined month and year.
     * @param month The month
     * @param year The year
     * @return The average orders
     */
    @Override
    public double getAverageOrders(int month, int year) {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement st = null;
            ResultSet rs = null;
            int totalDays = YearMonth.of(year, month).lengthOfMonth();
            double avg = 0;

            st = con.prepareStatement("SELECT COUNT(*)::FLOAT / ? AS avg FROM ordini WHERE EXTRACT(MONTH FROM data_ord) = ? AND EXTRACT(YEAR FROM data_ord) = ?");
            st.setInt(1, totalDays);
            st.setInt(2, month);
            st.setInt(3, year);
            rs = st.executeQuery();

            if(rs.next()) {
                avg = rs.getDouble("avg");
            }

            rs.close();
            st.close();

            return avg;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return 0;
        }
    }

    /**
     * This method is used to populate the order object.
     * @param rs The result set
     * @return The order object
     */
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
