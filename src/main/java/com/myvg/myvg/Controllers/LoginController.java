package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.Services.VideogameService;
import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;


@Controller
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

    @Autowired
    private VideogameService videogameService;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.loginUser(username, password)) 
        {
            UserDTO user = userService.getUserByUsername(username);
            AppContext.getInstance().setCurrentUser(user);
            sceneService.switchScene("/fxml/UserPage.fxml", 
            (UserPageController controller) -> 
            {
                controller.setUser();
                //TEST per l'aggiunta di un videogioco tramite mapper
                /*VideogameDTO videogameDTO = new VideogameDTO();
                videogameDTO.setTitle("MapTest");
                videogameDTO.setGenre("Platform");
                videogameDTO.setReleaseYear(1985);
                videogameDTO.setPlatform(List.of("potta"));
                videogameDTO.setReviews(new ArrayList<>());
                videogameService.createVideoGame(videogameDTO);*/
            });
        } 
        else 
        {
            sceneService.showAlert("Login Failed", "Invalid username or password.");
        }
    }


    @FXML
    public void handleRegister() {
        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        String email = emailField.getText();
        
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
