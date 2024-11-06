package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.myvg.myvg.DAO.ReviewDAO;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.Services.VideogameService;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.DTO.UserDTO;

import com.myvg.myvg.Services.ReviewService;
import com.myvg.myvg.Services.SceneService;
@Controller
public class ReviewPageController {
    @FXML
    private Spinner<Integer> ratingSpinner;
    
    @FXML
    private TextArea commentArea;

    @Autowired
    private UserService userService;

    @Autowired
    private VideogameService videogameService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private ReviewService reviewService;

    private UserDTO userDTO; // This should be set when user logs in
    private VideogameDTO videogameDTO; // This should be set when review page is opened for a specific game

    public void setGameContext(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        this.userDTO = userService.getUserByUsername("Muccarini");
    }

    @FXML
    public void handlePostReview() {
        int rating = ratingSpinner.getValue();
        String comment = commentArea.getText();

        if (comment == null || comment.trim().isEmpty()) {
            sceneService.showAlert("Error", "Please write a comment befor e posting.");
            return;
        }

        if (userDTO == null || videogameDTO == null) {
            sceneService.showAlert("Error", "Missing user or game information.");
            return;
        }

        // Save to database
        try {
            reviewService.postReview(new ReviewDTO(this.userDTO, this.videogameDTO, rating, comment.trim()));
            sceneService.showAlert("Success", "Your review has been successfully posted!");

            //update videogameDTO with new vgDTO revied
            videogameService.findGameById(this.videogameDTO.getId())
            .ifPresent(vgEntity -> {
                this.videogameDTO = new VideogameDTO(vgEntity);
            });

            sceneService.switchScene("/fxml/VideogamePage.fxml", (VideogamePageController controller) -> {
                controller.setVideogame(this.videogameDTO);
            });

            // Clear forms
            commentArea.clear();
            ratingSpinner.getValueFactory().setValue(5);

        } catch (Exception e) {
            sceneService.showAlert("Error", e.getMessage());
        }
    }
}
