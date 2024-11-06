package com.myvg.myvg.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.ReviewEntity;

import org.springframework.beans.factory.annotation.Autowired;

import com.myvg.myvg.Services.SceneService;
import org.springframework.stereotype.Controller;

@Controller
public class VideogamePageController {
    @FXML
    private Text titleText;
    @FXML 
    private Text genreText;
    @FXML
    private Text releaseYearText;
    @FXML
    private FlowPane platformContainer;
    @FXML
    private VBox reviewContainer;

    private VideogameDTO videogameDTO;

    @Autowired
    private SceneService sceneService;

    public void setVideogame(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        titleText.setText(videogameDTO.getTitle());
        genreText.setText("Genre: " + videogameDTO.getGenre());
        releaseYearText.setText("Released: " + videogameDTO.getReleaseYear());
    
        // Display platforms
        platformContainer.getChildren().clear();
        for (String platform : videogameDTO.getPlatform()) {
            Label platformLabel = new Label(platform);
            platformLabel.setStyle("-fx-padding: 5; -fx-background-color: #f0f0f0; -fx-background-radius: 5;");
            platformContainer.getChildren().add(platformLabel);
        }

        // Display reviews
        reviewContainer.getChildren().clear();
        if (videogameDTO.getReviews() != null) {
            for (ReviewEntity review : videogameDTO.getReviews()) {
                VBox reviewBox = new VBox(5);
                reviewBox.setStyle("-fx-padding: 10; -fx-background-color: #f8f8f8; -fx-background-radius: 5;");
                
                Text reviewText = new Text(review.getComment());
                reviewText.setWrappingWidth(400);
                
                Text scoreText = new Text("Score: " + review.getRating() + "/10");
                scoreText.setStyle("-fx-font-weight: bold;");
                
                reviewBox.getChildren().addAll(scoreText, reviewText);
                reviewContainer.getChildren().add(reviewBox);
            }
        }
    }

    @FXML
    private void onAddReview() {
        sceneService.switchScene("/fxml/ReviewPage.fxml", (ReviewPageController controller) -> {
            controller.setGameContext(this.videogameDTO);
        });
    }

}
