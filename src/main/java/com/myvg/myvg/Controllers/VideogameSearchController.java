package com.myvg.myvg.Controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.DAO.VideogameDAO;
import com.myvg.myvg.DTO.VideogameDTO;

@Controller
public class VideogameSearchController 
{
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SceneService sceneService;
    
    @Autowired
    private VideogameDAO videogameDAO;

    @FXML
    private FlowPane gamesContainer;

    private List<VideogameDTO> gamesToDisplay = new ArrayList<>();
    
    @FXML
    private void onBack() 
    {
        sceneService.switchScene("/fxml/UserPage.fxml");
    }

    /**
     * Sets the search query and displays matching videogames thumbnails.
     * Searches by title, genre and platform.
     * Creates thumbnails for each matching game and displays them in the games container.
     * 
     * @param query The search term to filter games by
     */
    public void setQuery(String query) 
    {

        gamesToDisplay.addAll(videogameDAO.findByTitle(query)
            .stream()
            .map(VideogameDTO::new)
            .collect(Collectors.toList()));

            gamesToDisplay.addAll(videogameDAO.findByGenre(query)
            .stream()
            .map(VideogameDTO::new)
            .collect(Collectors.toList()));

            gamesToDisplay.addAll(videogameDAO.findByPlatform(query)
            .stream()
            .map(VideogameDTO::new)
            .collect(Collectors.toList()));

            if(query == "")
            {
                gamesToDisplay.clear();
                gamesToDisplay.addAll(videogameDAO.findAll()
                .stream()
                .map(VideogameDTO::new)
                .collect(Collectors.toList()));
            }

        // Clear existing thumbnails
        gamesContainer.getChildren().clear();

        // Add game thumbnails to container
        for (VideogameDTO game : gamesToDisplay) {
            try {
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VideogameThumbnail.fxml"));
                loader.setControllerFactory(applicationContext::getBean);
                Node thumbnail = loader.load();

                VideogameThumbnailController vgTNController = loader.getController();
                vgTNController.setVideogame(game);

                gamesContainer.getChildren().add(thumbnail);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
