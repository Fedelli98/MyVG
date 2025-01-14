package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.ViewComponentModel.VideogameThumbnail;

@Controller
@Scope("prototype")
public class VideogameThumbnailWishlistController {

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView gameImage;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label releaseYearLabel;

    @FXML
    private Label platformLabel;

    @Autowired
    private UserService userService;

    @Autowired
    private WishlistController wishlistController;

    private VideogameDTO videogameDTO;

    public void setVideogame(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        VideogameThumbnail thumbnail = new VideogameThumbnail(videogameDTO);
        titleLabel.setText(thumbnail.getTitle());
        scoreLabel.setText(String.valueOf(thumbnail.getReviewScore()));
        genreLabel.setText("Genre: " + videogameDTO.getGenre());
        releaseYearLabel.setText("Released: " + videogameDTO.getReleaseYear());
        platformLabel.setText("Platforms: " + String.join(", ", videogameDTO.getPlatform()));

        //load game image
        try {
            gameImage.setImage(new Image(getClass().getResourceAsStream(videogameDTO.getVideogameCoverPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemove() {
        // Rimuovi il videogioco dalla wishlist dell'utente
        userService.removeFromWishlist(AppContext.getInstance().getCurrentUser().getId(), videogameDTO);

        // Aggiorna il contesto utente
        AppContext.getInstance().setCurrentUser(userService.getUserById(AppContext.getInstance().getCurrentUser().getId()));

        // Ridisegna la lista aggiornata
        wishlistController.displayGames();
    }
}
