package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

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
            loadUserProfile();
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void loadUserProfile() {
    try {
        // Carica il file FXML per la pagina profilo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserProfile.fxml"));
        
        // Carica il layout
        Parent profileRoot = loader.load();
        
        // Ottieni il controller della pagina profilo
        UserProfileController profileController = loader.getController();
        
        // Passa i dati dell'utente al controller della pagina profilo, se necessario
        profileController.setUser(usernameField.getText(), 0);

        // Ottieni la scena corrente e sostituiscila con la nuova
        Scene profileScene = new Scene(profileRoot);
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.setScene(profileScene);
        currentStage.show();

    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "Failed to load user profile.");
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
        userService.registerUser(username, password, email , -1);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
