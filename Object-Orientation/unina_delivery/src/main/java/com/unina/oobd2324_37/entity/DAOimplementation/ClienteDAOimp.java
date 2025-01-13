package com.unina.oobd2324_37.entity.DAOimplementation;

import com.unina.oobd2324_37.entity.DAO.ClienteDAO;
import com.unina.oobd2324_37.entity.DBconfig.DBConnection;
import com.unina.oobd2324_37.entity.DTO.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAOimp implements ClienteDAO {

    @Override
    public Cliente getByEmailNPass(String username, String password) throws NullPointerException {
        return null;
    }

    @Override
    public Cliente getById(String idCliente) {
        try {
            Connection con = DBConnection.getInstance();
            PreparedStatement ps = null;
            int idClienteInt = Integer.parseInt(idCliente);

            ps = con.prepareStatement("SELECT * FROM clienti WHERE idcliente = ?");
            ps.setInt(1, idClienteInt);

            return getCliente(con, ps);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
            return null;
        }
    }

    private Cliente getCliente(Connection con, PreparedStatement ps) throws SQLException {
        Cliente cliente = null;
        ResultSet rs;
        rs = ps.executeQuery();

        if(rs.next()) {
            cliente = new Cliente(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("via"),
                    rs.getString("civico"),
                    rs.getString("cap"),
                    rs.getString("cellulare"),
                    rs.getString("email"));
        }

        rs.close();
        ps.close();

        return cliente;
    }

}
