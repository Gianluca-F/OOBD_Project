package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.CorriereDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Corriere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to interact with the database and retrieve the courier data.
 */
public class CorriereDAOimp implements CorriereDAO {

    @Override
    public Corriere getById(String idCorriere) {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;
            int idCorriereInt = Integer.parseInt(idCorriere);

            ps = con.prepareStatement("SELECT * FROM corrieri WHERE idcorriere = ?");
            ps.setInt(1, idCorriereInt);

            return getCorriere(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private Corriere getCorriere(Connection con, PreparedStatement ps) throws SQLException {
        Corriere corriere = null;
        ResultSet rs;
        rs = ps.executeQuery();

        if(rs.next()) {
            corriere = new Corriere(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("codFiscale"),
                    rs.getString("cellulare"),
                    rs.getBoolean("disponibilita"));
        }

        rs.close();
        ps.close();

        return corriere;
    }
}
