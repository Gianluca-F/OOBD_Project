package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.VeicoloDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Veicolo;

import java.sql.*;

/**
 * This class is used to interact with the database and retrieve the vehicle data.
 */
public class VeicoloDAOimp implements VeicoloDAO {

    @Override
    public Veicolo getByTarga(String targa) {

        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;

            ps = con.prepareStatement("SELECT * FROM veicoli WHERE targa = ?");
            ps.setString(1, targa);

            return getVeicolo(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private Veicolo getVeicolo(Connection con, PreparedStatement ps) throws SQLException {
        Veicolo veicolo = null;
        ResultSet rs;
        rs = ps.executeQuery();

        if(rs.next()) {
            veicolo = new Veicolo(
                    rs.getString("targa"),
                    rs.getDouble("pesoSupportato"),
                    rs.getBoolean("disponibilita"));
        }

        rs.close();
        ps.close();

        return veicolo;
    }
}
