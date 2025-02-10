package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Operatore;
import com.unina.oobd2324_37.entity.utils.Cryptography;

import java.sql.*;

/**
 * This class is used to interact with the database and retrieve the operator data.
 */
public class OperatoreDAOimp implements OperatoreDAO {

    @Override
    public Operatore getByUserNPass(String username, String password) throws NullPointerException {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;
            String passCrypted = Cryptography.cryptPassword(password);

            ps = con.prepareStatement("SELECT * FROM operatori WHERE username = ? AND pass = ?");
            ps.setString(1, username);
            ps.setString(2, passCrypted);

            return getOperatore(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }
    
    @Override
    public Operatore getById(String idOperatore) {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;
            int idOperatoreInt = Integer.parseInt(idOperatore);

            ps = con.prepareStatement("SELECT * FROM operatori WHERE idoperatore = ?");
            ps.setInt(1, idOperatoreInt);

            return getOperatore(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    private Operatore getOperatore(Connection con, PreparedStatement ps) throws SQLException {
        Operatore operatore = null;
        ResultSet rs;
        rs = ps.executeQuery();

        if(rs.next()) {
            operatore = new Operatore(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("codFiscale"),
                    rs.getString("username"),
                    rs.getString("pass"));
        }

        rs.close();
        ps.close();

        return operatore;
    }
}