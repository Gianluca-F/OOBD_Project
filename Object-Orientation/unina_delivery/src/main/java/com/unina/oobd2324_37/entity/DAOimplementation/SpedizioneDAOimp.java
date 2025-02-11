package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OrdineDAO;
import com.unina.oobd2324_37.entity.DAO.SpedizioneDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class is used to interact with the database and retrieve the shipment data.
 */
public class SpedizioneDAOimp implements SpedizioneDAO {

    @Override
    public Spedizione getById(String idSpedizione) {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;

            ps = con.prepareStatement("SELECT * FROM spedizioni WHERE idspedizione = ?");
            ps.setString(1, idSpedizione);

            return getSpedizione(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insert(Operatore operatore, List<Ordine> ordini, String corriere, String veicolo) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getInstance();
            con.setAutoCommit(false);

            int idOperatore = retrieveIdOperatore(operatore);
            int idCorriere = retrieveIdCorriere(corriere);

            ps = con.prepareStatement(
                    "INSERT INTO spedizioni (data_sped, orario, avviata, completata, idoperatore, idcorriere, targa) " +
                        "VALUES (DEFAULT, DEFAULT, DEFAULT, DEFAULT, ?, ?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, idOperatore);
            ps.setInt(2, idCorriere);
            ps.setString(3, veicolo);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            String idSpedizione = null;
            if (rs.next()) {
                idSpedizione = rs.getString("idspedizione");
            }

            rs.close();
            ps.close();

            OrdineDAO ordineDAO = new OrdineDAOimp();
            for (Ordine ordine : ordini) {
                ordineDAO.updateSpedizione(con, ordine.getIdOrdine(), idSpedizione, idOperatore);
            }

            ps = con.prepareStatement("UPDATE spedizioni SET avviata = TRUE WHERE idspedizione = ?");
            ps.setString(1, idSpedizione);
            ps.executeUpdate();
            ps.close();

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Errore durante il rollback: " + rollbackEx.getMessage());
                }
            }
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException closeEx) {
                System.err.println("Errore nella chiusura delle risorse: " + closeEx.getMessage());
            }
        }
    }

    private Spedizione getSpedizione(Connection con, PreparedStatement ps) throws SQLException {
        Spedizione spedizione = null;
        ResultSet rs;
        rs = ps.executeQuery();

        if(rs.next()) {
            spedizione = new Spedizione(
                    rs.getString("idspedizione"),
                    rs.getDate("data_sped").toLocalDate(),
                    rs.getTimestamp("orario").toLocalDateTime(),
                    rs.getBoolean("avviata"),
                    rs.getBoolean("completata"),
                    new OperatoreDAOimp().getById(rs.getString("idoperatore")),
                    new CorriereDAOimp().getById(rs.getString("idcorriere")),
                    new VeicoloDAOimp().getByTarga(rs.getString("targa"))
            );
        }

        rs.close();
        ps.close();

        return spedizione;
    }

    /**
     * This method is used to retrieve the ID of the operator.
     * @param operatore The operator
     * @return The ID of the operator
     * @throws SQLException If an error occurs during the retrieval of the operator ID
     */
    private int retrieveIdOperatore(Operatore operatore) throws SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idOperatore = -1;

        try {
            ps = con.prepareStatement("SELECT idoperatore FROM operatori WHERE username = ?");

            ps.setString(1, operatore.getUsername());
            rs = ps.executeQuery();

            if (rs.next()) {
                idOperatore = rs.getInt("idoperatore");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'ID dell'operatore: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        System.out.println("ID Operatore: " + idOperatore);
        return idOperatore;
    }


    /**
     * This method is used to retrieve the ID of the courier.
     * @param nomeCompleto The full name of the courier
     * @return The ID of the courier
     * @throws SQLException If an error occurs during the retrieval of the courier ID
     */
    private int retrieveIdCorriere(String nomeCompleto) throws SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idCorriere = -1;

        String[] parts = nomeCompleto.trim().split("\\s+", 2);
        if (parts.length < 2) {
            System.err.println("Errore: Nome e cognome non validi");
            return -1;
        }

        String nome = parts[0];
        String cognome = parts[1];

        try {
            ps = con.prepareStatement("SELECT get_id_corriere(?, ?)");

            ps.setString(1, nome);
            ps.setString(2, cognome);
            rs = ps.executeQuery();

            if (rs.next()) {
                idCorriere = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dell'ID del corriere: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return idCorriere;
    }

}