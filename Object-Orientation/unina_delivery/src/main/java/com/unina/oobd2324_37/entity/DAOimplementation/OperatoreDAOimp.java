package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Operatore;
import com.unina.oobd2324_37.entity.utils.Cryptography;

import java.sql.*;

public class OperatoreDAOimp implements OperatoreDAO {

    @Override
    public Operatore getByEmailNPass(String username, String password) throws NullPointerException {

        try {
            Connection con = DBConnection.getInstance();
            Operatore operatore = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String passCrypted = Cryptography.cryptPassword(password);

            ps = con.prepareStatement("SELECT * FROM operatori WHERE username = ? AND pass = ?");
            ps.setString(1, username);
            ps.setString(2, passCrypted);

            rs = ps.executeQuery();

            if(rs.next()) {
                operatore = new Operatore(rs.getString("nome"), rs.getString("cognome"), rs.getString("codFiscale"), rs.getString("username"), rs.getString("pass"));
            }

            rs.close();
            ps.close();
            con.close();

            return operatore;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
        }

        return null;
    }
}