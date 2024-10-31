package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myvg.myvg.Services.UserService;

@Component
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField registerUsernameField;
    @FXML
    private PasswordField registerPasswordField;

    @Autowired
    private UserService userService;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.loginUser(username, password)) {
            showAlert("Login Success", "Login was successful!");
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    public void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String email = emailField.getText();
        if (username == "" && password == "" && email == "") {
            showAlert("Registration Failed", "Please fill in all fields.");
            return;
        }
        userService.registerUser(username, password, email);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
