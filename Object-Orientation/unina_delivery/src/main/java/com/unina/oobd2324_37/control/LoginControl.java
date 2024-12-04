package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.LoginController;
import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OperatoreDAOimp;
import com.unina.oobd2324_37.entity.DTO.Operatore;

import java.io.IOException;

public class LoginControl {

    public static LoginControl instance = null;
    private LoginController loginController;

    private LoginControl() {}

    public static LoginControl getInstance() {
        if(instance == null) {
            instance = new LoginControl();
        }
        return instance;
    }

    public void initialize(LoginController loginController) {
        this.loginController = loginController;
    }

    public void login(String username, String password) {
        if(username.isEmpty() || password.isEmpty()) {
            loginController.showErrorMessage("Username e/o password\nnon possono essere vuoti!");
        } else {
            OperatoreDAO operatoreDAO = new OperatoreDAOimp();
            Operatore operatore = operatoreDAO.getByEmailNPass(username, password);

            if(operatore != null) {
                HomeControl.getInstance().initialize(operatore);
                switchToHome();
                loginController.showErrorMessage("");
            } else {
                loginController.showErrorMessage("Username e/o password\nnon validi!");
            }
        }
    }

    private void switchToHome() {
        try {
            App.setRoot("Home-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Home-Page: " + e.getMessage());
        }
    }
}
