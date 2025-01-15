package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.myvg.myvg.Services.VideogameService;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Mapper.MapperProfile;
import com.myvg.myvg.Mapper.MapperProfileFactory;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.ReviewService;
import com.myvg.myvg.Services.SceneService;
import com.myvg.myvg.Mapper.MapperProfileFactory.MapperProfileEnum;

import java.util.ArrayList;

@Controller
public class ReviewPageController {
    @FXML
    private Spinner<Integer> ratingSpinner;
    
    @FXML
    private TextArea commentArea;

    @Autowired
    private VideogameService videogameService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private ReviewService reviewService;

    private UserDTO userDTO; // This should be set when user logs in
    private VideogameDTO videogameDTO; // This should be set when review page is opened for a specific game

    private final MapperProfile mapperVg = MapperProfileFactory.createMapperProfile(MapperProfileEnum.VIDEOGAME);

    public void setGameContext() {
        videogameDTO = AppContext.getInstance().getCurrentVideogame();
        userDTO = AppContext.getInstance().getCurrentUser();
    }

    @FXML
    public void handlePostReview() {
        int rating = ratingSpinner.getValue();
        String comment = commentArea.getText();

        if (comment == null || comment.trim().isEmpty()) {
            sceneService.showAlert("Error", "Please write a comment before posting.");
            return;
        }

        if (userDTO == null || videogameDTO == null) {
            sceneService.showAlert("Error", "Missing user or game information.");
            return;
        }

        // Save to database
        try {
            reviewService.postReview(new ReviewDTO(this.userDTO.getUsername(), this.videogameDTO.getTitle(), rating, comment.trim(),0));

            //update videogameDTO with new vgDTO reviewd
            videogameService.getGameById(this.videogameDTO.getId())
            .ifPresent(vgEntity -> {
                this.videogameDTO = mapperVg.map(vgEntity, new VideogameDTO());
            });

            //update AppContext current Videogame and update queryList
            AppContext.getInstance().setCurrentVideogame(this.videogameDTO);

            AppContext.getInstance().getCurrentQuery().forEach(vgQuery -> 
            {
                if(vgQuery.getId().equals(this.videogameDTO.getId())){
                    vgQuery = this.videogameDTO;
                }
            }
            );

            //update queryList
            var oldQuery = AppContext.getInstance().getCurrentQuery();

            var newQuery = new ArrayList<VideogameDTO>();

            oldQuery.forEach(oldVg ->
            {
                if(oldVg.getId().equals(this.videogameDTO.getId())){
                    newQuery.add(this.videogameDTO);
                }else{
                    newQuery.add(oldVg);
                }
            });

            AppContext.getInstance().setCurrentQuery(newQuery);

            //switch scene
            sceneService.switchScene("/fxml/VideogamePage.fxml", 
            (VideogamePageController controller) -> {
                controller.setVideogame();
            });

            // Clear forms
            commentArea.clear();
            ratingSpinner.getValueFactory().setValue(5);

        } catch (Exception e) {
            sceneService.showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onBack() {
        sceneService.switchScene("/fxml/VideogamePage.fxml", 
        (VideogamePageController controller) -> {
            controller.setVideogame();
        });
    }
}
