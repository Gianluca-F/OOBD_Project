package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.SpedizioneDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Spedizione getSpedizione(Connection con, PreparedStatement ps) throws SQLException {
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
}