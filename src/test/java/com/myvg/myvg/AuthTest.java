package com.myvg.myvg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.EntityModel.UserEntity;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.UserService;

public class AuthTest {

    private UserEntity testUser;
    private UserService userService = new UserService();

    // @BeforeEach
    // public void setUp() {
    //     testUser = new UserEntity(new UserDTO("testUser", "password", 1, List.of(
    //         new VideogameDTO("Test Game", "Test Description", 2000, List.of(
    //             new ReviewDTO("Test Review", 5, "Test Comment", "testUser"),
    //             new ReviewDTO("Test Review 2", 4, "Test Comment 2", "testUser")
    //         )),
    //         new VideogameDTO("Test Game 2", "Test Description 2", 2001, List.of(
    //             new ReviewDTO("Test Review 3", 3, "Test Comment 3", "testUser"),
    //             new ReviewDTO("Test Review 4", 2, "Test Comment 4", "testUser")
    //         ))
    //     )));
    //TEST per l'aggiunta di un videogioco tramite mapper
/*  VideogameDTO videogameDTO = new VideogameDTO();
    videogameDTO.setTitle("MapTest");
    videogameDTO.setGenre("Platform");
    videogameDTO.setReleaseYear(1985);
    videogameDTO.setPlatform(List.of("potta"));
    videogameDTO.setReviews(new ArrayList<>());
    videogameService.createVideoGame(videogameDTO);   */  
    // }

    // @AfterEach
    // public void tearDown() {
    //     userService.deleteUser(testUser);
    //     assertNull(AppContext.currentUser());
    // }

    // @Test
    // public void testRegisterAndLogin() {
    //     AuthService.register(testUser);
    //     AuthService.login("testUser", "password");
    //     assertEquals(testUser, AppContext.currentUser());
    // }
}
