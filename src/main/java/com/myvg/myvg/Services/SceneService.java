package com.myvg.myvg.Services;

import java.io.IOException;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@Service
@Scope("singleton")
public class SceneService {

    private final ApplicationContext applicationContext;
    private Stage stage;

    public SceneService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

/**
 * The method {@code switchScene} simplifies switching between scenes in a JavaFX application.
 * It loads the FXML file specified by {@code fxmlPath}, sets the corresponding controller through
 * the Spring {@code applicationContext} 
 * and calls the provided {@code beforeShowing} callback with the controller instance before 
 * displaying the new scene.
 *
 * @param fxmlPath The path to the FXML file for the new scene.
 * @param beforeShowing A {@link Consumer} callback that is called with the controller of the 
 *                     loaded scene. This parameter can be {@code null} if no additional 
 *                     configuration is needed before showing the scene.
 * @param <T> The type of the controller for the loaded FXML.
 * 
 * @throws IOException if an I/O error occurs while loading the FXML file.
 * 
 * @author Luca Pandolfini
 */
    @SuppressWarnings("unchecked")
    public <T> void switchScene(String fxmlPath, Consumer<T> initializer) {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            stage.setScene(new Scene(root));

            var controller = loader.getController();

            if(initializer != null && controller != null){
                initializer.accept((T)controller);
            }

            stage.show();

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void switchScene(String fxmlPath) {
        switchScene(fxmlPath, null);
    }

}
