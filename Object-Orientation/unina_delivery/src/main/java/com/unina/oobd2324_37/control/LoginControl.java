package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.LoginController;
import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OperatoreDAOimp;
import com.unina.oobd2324_37.entity.DTO.Operatore;

import java.sql.SQLException;

public class LoginControl {

    private LoginController loginController;

    public LoginControl(LoginController loginController) {
        this.loginController = loginController;
    }

    public void login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            loginController.showErrorMessage("Username e/o password\nnon possono essere vuoti!");
        } else {
            try {
                OperatoreDAO operatoreDAO = new OperatoreDAOimp();
                Operatore operatore = operatoreDAO.getByEmailNPass(username, password);

                if(operatore != null) {
                    // TODO: Show next GUI
                    System.out.println("Login successful");
                } else {
                    loginController.showErrorMessage("Username e/o password\nnon validi!");
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName()+": "+ e.getMessage());
            }
        }
    }
}
