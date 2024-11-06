package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    private VideogameDTO videogameDTO;

    public void setVideogame(VideogameDTO videogameDTO) {
        this.videogameDTO = videogameDTO;
        VideogameThumbnail thumbnail = new VideogameThumbnail(videogameDTO);
        titleLabel.setText(thumbnail.getTitle());

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
