package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OperatoreDAOimp;
import com.unina.oobd2324_37.entity.DTO.Operatore;

import java.sql.SQLException;

public class LoginControl {

    public static void login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            // TODO: Show error message in the GUI
            System.out.println("Username and password cannot be empty");
        } else {
            try {
                OperatoreDAO operatoreDAO = new OperatoreDAOimp();
                Operatore operatore = operatoreDAO.getByEmailNPass(username, password);

                if(operatore != null) {
                    // TODO: Show next GUI
                    System.out.println("Login successful");
                } else {
                    System.out.println("Login failed");
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName()+": "+ e.getMessage());
            }
        }
    }
}
