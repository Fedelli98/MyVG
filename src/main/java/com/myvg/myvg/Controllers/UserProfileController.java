package com.myvg.myvg.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserProfileController {

    @FXML
    private ImageView profileImageView;


    @FXML
    private TextField usernameField;

    @FXML
    private TextField gameSearchField;

    @FXML
    private TextField userSearchField;

    @FXML
    private GridPane avatarGrid;

    private static final int IMAGES_PER_PAGE = 10;

    private int currentPage = 0;

    private List<Image> avatars;

    @FXML
    public void initialize() {
        avatars = ImageLoader.loadAllImages();
        loadAvatarPage();
    }

    // Metodo per caricare le immagini degli avatar nella GridPane
    private void loadAvatarPage() {

        avatarGrid.getChildren().clear();
        
        int start = currentPage * IMAGES_PER_PAGE;
        int end = Math.min(start + IMAGES_PER_PAGE, avatars.size());

        int column = 0;
        int row = 0;

        for (int i = start; i < end; i++) {
            ImageView avatarView = new ImageView(avatars.get(i));
            avatarView.setFitHeight(50);
            avatarView.setFitWidth(50);
            int index = i;
            avatarView.setOnMouseClicked(event -> profileImageView.setImage(avatars.get(index)));
            avatarGrid.add(avatarView, column, row);

            column++;
            if (column == 4) {
                column = 0;
                row++;
            }
        }
    }

    @FXML
    private void nextPage() {
        if ((currentPage + 1) * IMAGES_PER_PAGE < avatars.size()) {
            currentPage++;
            loadAvatarPage();
        }
    }

    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadAvatarPage();
        }
    }

    @FXML
    private void onCreateWishlist() {
        showAlert("Wishlist Created", "Your wishlist has been created successfully.");
    }

    @FXML
    private void onGameSearch() {
        String searchText = gameSearchField.getText();
        showAlert("Game Search", "Searching for games: " + searchText);
    }

    @FXML
    private void onUserSearch() {
        String searchText = userSearchField.getText();
        showAlert("User Search", "Searching for users: " + searchText);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setUser(String username, int avatarID) { 
        usernameField.setText(username);
        if (avatarID >=0) {
            profileImageView.setImage(avatars.get(avatarID));
        }
    }


    public class ImageLoader {
        public static final String IMAGE_PATH = "/images/avatars/";
    
        public static List<Image> loadAllImages() {
            List<Image> images = new ArrayList<>();
            try {
                Path path = Paths.get(Objects.requireNonNull(ImageLoader.class.getResource(IMAGE_PATH)).toURI());
                List<String> imageFiles = Files.list(path)
                        .filter(Files::isRegularFile)
                        .map(Path::toString)
                        .filter(file -> file.endsWith(".png") || file.endsWith(".jpg") || file.endsWith(".jpeg"))
                        .collect(Collectors.toList());

                for (String filePath : imageFiles) {
                    images.add(new Image("file:" + filePath));
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
            return images;
        }
    }
}
