package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Alert;

import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.ViewComponentModel.VideogameThumbnail;
import com.myvg.myvg.DTO.VideogameDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;


//TODO: ADD SORT OF USER CONTEXT (AFTER LOGIN) AND VIDEOGAME CONTEXT (AFTER ENTERING IN A VIDEOGAME PAGE)

@Controller
@Scope("prototype")
public class VideogameThumbnailController {

    @Autowired
    private SceneService sceneService;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView gameImage;

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
            
            return new WritableImage(100, 100); // Ritorna un'immagine vuota come fallback
        }
    }

    public void setVideogame(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        VideogameThumbnail thumbnail = new VideogameThumbnail(videogameDTO);
        titleLabel.setText(thumbnail.getTitle());
        
        // Carica l'immagine del gioco
        try {
            gameImage.setImage(loadGameImage(videogameDTO.getVideogameCoverPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleGameButtonClick() {
        sceneService.switchScene("/fxml/VideogamePage.fxml", 
        (VideogamePageController vgPageController) -> 
        {
            vgPageController.setVideogame(videogameDTO);
        });
    }

}
