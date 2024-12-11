package com.myvg.myvg.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.EntityModel.ReviewEntity;
import com.myvg.myvg.EntityModel.VideogameEntity;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;

import org.springframework.beans.factory.annotation.Autowired;

import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.Services.VideogameService;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.ReviewService;

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
    @FXML
    private Button addToWishlistButton;

    private VideogameDTO videogameDTO;
    
    private final MapperProfile mapperProfile = MapperProfileFactory.createMapperProfile(MapperProfileEnum.VIDEOGAME);

    @Autowired
    private VideogameService videogameService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    
        public void setVideogame() {
            this.videogameDTO = AppContext.getInstance().getCurrentVideogame();
    
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
    
            displayReviews();
        }
    
        @FXML
        private void onAddReview() {
            sceneService.switchScene("/fxml/ReviewPage.fxml", 
            (ReviewPageController controller) -> {
                controller.setGameContext();
            });
        }
    
        @FXML
        private void onBack() {
            AppContext.getInstance().clearCurrentVideogame();
    
            sceneService.switchScene("/fxml/VideogameSearch.fxml", 
            (VideogameSearchController controller) -> {
                controller.setGames();
            });
        }
    
        @FXML
        private void onAddToWishlist() {
            //Retrieve context
            UserDTO currentUser = AppContext.getInstance().getCurrentUser();
            VideogameDTO currentGame = AppContext.getInstance().getCurrentVideogame();
            
            //Check if game already in wishlist
            if(currentUser.getWishlist().stream()
                    .anyMatch(game -> game.getTitle().equals(currentGame.getTitle()))) {
                sceneService.showAlert("Already in Wishlist", "This game is already in your wishlist!");
                return;
            }
            
            //Add to wishlist
            videogameService.addToWishlist(currentUser.getId(), currentGame);
            
            //Update context
            UserDTO user = userService.getUserById(currentUser.getId());
            AppContext.getInstance().setCurrentUser(user);
        }
    
        @FXML
        private void handleLikeReview(String reviewId) {
            UserDTO currentUser = AppContext.getInstance().getCurrentUser();
            if (currentUser != null) {
                reviewService.likeReview(reviewId, currentUser.getId());
                
                //aggiorna il conesto (non funziona il mapper)
                VideogameEntity currentvg = videogameService.findGameById(AppContext.getInstance().getCurrentVideogame().getId()).get();
                VideogameDTO upVideogamedto = mapperProfile.map(currentvg, new VideogameDTO());
                AppContext.getInstance().setCurrentVideogame(upVideogamedto);
                // Aggiorna la visualizzazione delle recensioni
                displayReviews();
            } else {
                sceneService.showAlert("Error", "You must be logged in to like a review.");
            }
        }

        private void displayReviews(){
            this.videogameDTO = AppContext.getInstance().getCurrentVideogame();

            reviewContainer.getChildren().clear();
            if (videogameDTO.getReviews() != null) {
                System.out.println(videogameDTO.getReviews().size());
                for (ReviewEntity review : videogameDTO.getReviews()) {
                    VBox reviewBox = new VBox(5);
                    reviewBox.setStyle("-fx-padding: 10; -fx-background-color: #f8f8f8; -fx-background-radius: 5;");
                    
                    Text usernameText = new Text(review.getUsername());
                    usernameText.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                    
                    Text reviewText = new Text(review.getComment());
                    reviewText.setWrappingWidth(400);
                    
                    Text scoreText = new Text("Score: " + review.getRating() + "/10");
                    scoreText.setStyle("-fx-font-weight: bold;");
                    
                    Text likesText = new Text("Likes: " + review.getLikes());
                    
                    ToggleButton likeButton = new ToggleButton("Like");
                    likeButton.setOnAction(e -> handleLikeReview(review.getId()));
                    
                    reviewBox.getChildren().addAll(usernameText, scoreText, reviewText, likesText, likeButton);
                    reviewContainer.getChildren().add(reviewBox);
                }
            }
        }
}
