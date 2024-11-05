package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.javafx.StageListener;

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
    @Autowired
    private StageListener stageListener;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.loginUser(username, password)) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stageListener.SwitchScene(stage, "/fxml/UserProfile.fxml", 
            (UserProfileController controller) ->
                {
                    controller.setUser(userService.getUserByUsername(username).getUsername(), userService.getUserByUsername(username).getAvatarID()); 
                });
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
        try{
            userService.registerUser(username, password, email , -1);
        }catch(Exception e){
            showAlert("Registration Failed", e.getMessage());
            return;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
