package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.CorriereDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Corriere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to interact with the database and retrieve the courier data.
 */
public class CorriereDAOimp implements CorriereDAO {

    @Override
    public Corriere getById(String idCorriere) {
        try {
            Connection con = DBConnection.getInstance();
            Corriere corriere = null;
            PreparedStatement st = null;
            ResultSet rs = null;
            int idCorriereInt = Integer.parseInt(idCorriere);

            st = con.prepareStatement("SELECT * FROM corrieri WHERE idcorriere = ?");
            st.setInt(1, idCorriereInt);
            rs = st.executeQuery();

            if(rs.next()) {
                corriere = populateCorriere(rs);
            }

            rs.close();
            st.close();

            return corriere;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Corriere> getDisponibili() {
        try {
            Connection con = DBConnection.getInstance();

            List<Corriere> corrieri = new LinkedList<>();
            PreparedStatement st = null;
            ResultSet rs = null;

            st = con.prepareStatement("SELECT * FROM corrieri WHERE disponibilita = true");
            rs = st.executeQuery();

            while(rs.next()) {
                corrieri.add(populateCorriere(rs));
            }

            rs.close();
            st.close();

            return corrieri;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private Corriere populateCorriere(ResultSet rs) throws SQLException {
        try {
            Corriere corriere = null;

            corriere = new Corriere(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("codFiscale"),
                    rs.getString("cellulare"),
                    rs.getBoolean("disponibilita"));

            return corriere;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }
}
