package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.Services.VideogameService;
import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;


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
    private SceneService sceneService;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(login(username, password) == true){
            sceneService.switchScene("/fxml/UserPage.fxml", 
            (UserPageController controller) -> 
            {
                controller.setUser();
            });
        }
    }

    public boolean login(String username, String password) {
        if (userService.loginUser(username, password)) 
        {
            UserDTO user = userService.getUserByUsername(username);
            AppContext.getInstance().setCurrentUser(user);
            return true;
        } 
        else 
        {
            sceneService.showAlert("Login Failed", "Invalid username or password.");
            return false;
        }
    }


    @FXML
    public void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String email = emailField.getText();

        register(username, password, email);
    }

    public void register(String username, String password, String email) {
        if (username == "" && password == "" && email == "") {
            sceneService.showAlert("Registration Failed", "Please fill in all fields.");
            return;
        }
        try{
            userService.registerUser(username, password, email , -1);
        }catch(Exception e){
            sceneService.showAlert("Registration Failed", e.getMessage());
            return;
        }
    }

}
