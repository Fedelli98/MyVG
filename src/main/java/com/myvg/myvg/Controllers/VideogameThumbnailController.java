package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;

import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.ViewComponentModel.VideogameThumbnail;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;
import com.myvg.myvg.Services.AppContext;

@Controller
@Scope("prototype")
public class VideogameThumbnailController {

    @Autowired
    private SceneService sceneService;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView gameImage;

    @FXML
    private ProgressBar ratingBar;

    @FXML
    private Label scoreLabel;

    MapperProfile mapperVgThumbnail = MapperProfileFactory.createMapperProfile(MapperProfileFactory.MapperProfileEnum.VGTHUMBNAIL);

    private VideogameDTO videogameDTO;

    private Image loadGameImage(String path) {
                
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Immagine non trovata");
            alert.setContentText("Non è stata trovata nessuna immagine per questo videogioco");
            alert.showAndWait();
            
            return new WritableImage(100, 100); // Ritorna un'immagine vuota come callback
        }
    }

    public void setVideogame(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        VideogameThumbnail thumbnail = mapperVgThumbnail.map(videogameDTO, new VideogameThumbnail());
        titleLabel.setText(thumbnail.getTitle());
        
        // Imposta il rating
        double rating = thumbnail.getReviewScore() / 10.0; // Converte il rating in un valore tra 0 e 10
        ratingBar.setProgress(rating);
        scoreLabel.setText(thumbnail.getReviewScore() + "/10");
        
        // Carica l'immagine del gioco
        try {
            gameImage.setImage(loadGameImage(videogameDTO.getVideogameCoverPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleGameButtonClick() {
        AppContext.getInstance().setCurrentVideogame(videogameDTO);
        sceneService.switchScene("/fxml/VideogamePage.fxml", 
        (VideogamePageController vgPageController) -> 
        {
            vgPageController.setVideogame();
        });
    }

}
