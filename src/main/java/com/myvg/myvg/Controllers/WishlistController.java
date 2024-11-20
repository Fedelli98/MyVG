package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.Services.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class WishlistController {
    @FXML
    private FlowPane wishlistContainer;
    
    @Autowired
    private SceneService sceneService;
    
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;
    
    
    public void displayGames() {
        List<VideogameDTO> wishlist = AppContext.getInstance().getCurrentUser().getWishlist();
        try {
            if (wishlist != null) {
                wishlistContainer.getChildren().clear();
                for (VideogameDTO game : wishlist) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideogameThumbnail.fxml"));
                        loader.setControllerFactory(applicationContext::getBean);
                        Node thumbnail = loader.load();
                        
                        VideogameThumbnailController controller = loader.getController();
                        controller.setVideogame(game);
                        
                        wishlistContainer.getChildren().add(thumbnail);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onBack() {
        //Update context
        AppContext.getInstance().setCurrentUser(userService.getUserById(AppContext.getInstance().getCurrentUser().getId()));
        
        sceneService.switchScene("/fxml/UserPage.fxml", 
            (UserPageController controller) -> {
                controller.setUser();
            });
    }
}
