package com.myvg.myvg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Controllers.LoginController;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.UserService;

import java.util.List;

public class AuthTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private UserService userService;

    private UserDTO testUser;

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO("Test User", "password", 1, List.of(
            new VideogameDTO("Test Game 1", "Test Genre", 2000, List.of("Test Platform"), List.of(
                new ReviewDTO("Test User", "Test Game", 10, "Test Comment", 3),
                new ReviewDTO("Test User", "Test Game", 10, "Test Comment", 3)
            )),
            new VideogameDTO("Test Game 2", "Test Genre", 2000, List.of("Test Platform"), List.of(
                new ReviewDTO("Test User", "Test Game", 10, "Test Comment", 3),
                new ReviewDTO("Test User", "Test Game", 10, "Test Comment", 3)
            ))
        ));
    }

    @Test
    public void testRegisterAndLogin() {
        loginController.register("Test User", "password", "email@gmail.com");
        loginController.login("Test User", "password");

        assertEquals("Test User", AppContext.getInstance().getCurrentUser().getUsername());
    }
    
    @AfterEach
    public void tearDown() {
        userService.removeUser(testUser.getUsername(), "password");
        assertNull(AppContext.getInstance().getCurrentUser());
    }

}
