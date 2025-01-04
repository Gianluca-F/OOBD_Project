package com.unina.oobd2324_37.control;

import com.unina.oobd2324_37.boundary.LoginController;
import com.unina.oobd2324_37.entity.DAO.OperatoreDAO;
import com.unina.oobd2324_37.entity.DAOimplementation.OperatoreDAOimp;
import com.unina.oobd2324_37.entity.DTO.Operatore;

import java.io.IOException;

/**
 * This class is used to control the login operations.
 */
public class LoginControl {

    public static LoginControl instance = null;
    private LoginController loginController;

    /**
     * Private constructor to avoid instantiation.
     */
    private LoginControl() {}

    /**
     * This method is used to get the instance (singleton) of the LoginControl class.
     * @return The instance of the LoginControl class
     */
    public static LoginControl getInstance() {
        if(instance == null) {
            instance = new LoginControl();
        }
        return instance;
    }

    /**
     * This method is used to initialize the LoginControl class.
     * @param loginController The LoginController class
     */
    public void initialize(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * This method is used to log in the user.
     * @param username The username
     * @param password The password
     */
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

    /**
     * This method is used to switch to the home page.
     */
    private void switchToHome() {
        try {
            App.setRoot("Home-Page");
        } catch (IOException e) {
            System.err.println("Error switching to Home-Page: " + e.getMessage());
        }
    }
}
