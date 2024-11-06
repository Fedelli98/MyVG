package com.myvg.myvg.javafx;

import com.myvg.myvg.MyvgApplication.StageReadyEvent;
import com.myvg.myvg.Services.SceneService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


//Classe per la dependency injection di Spring nel primo stage dell'applicazione
@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final ApplicationContext applicationContext;
    private final SceneService sceneService;

    public StageListener(
        @Value("${spring.application.ui.title}") String applicationTitle, 
        ApplicationContext ac, 
        SceneService sceneService) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = ac;
        this.sceneService = sceneService;
    }
    
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        sceneService.setStage(event.getStage());
        SwitchScene(event.getStage(), "/fxml/Login.fxml", null);
    }

    public <T> void SwitchScene(Stage stage, String fxmlPath, SceneInitializer<T> initializer) {
        try {
            // Caricamento dinamico del file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            // Ottieni il controller
            T controller = loader.getController();
            
            // Applica l'inizializzatore, se fornito
            if (initializer != null) {
                initializer.accept(controller);
            }

            // Imposta la scena
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funzionale per passare parametri personalizzati ai controller
    @FunctionalInterface
    public interface SceneInitializer<T> {
        void accept(T controller);
    }
}
