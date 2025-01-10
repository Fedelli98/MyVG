package com.myvg.myvg;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.boot.test.context.SpringBootTest;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Controllers.LoginController;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.EntityModel.UserEntity;

//@SpringBootTest
public class AuthTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AppContext appContext;

    private UserDTO testUser;
    private UserEntity usrEnt;

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO("Muccarini", "example@gmail.com", 0, null);
    }

    @Test
    public void testHandleRegister() throws Exception {
        userService.registerUser(testUser.getUsername(), "password", testUser.getEmail(), testUser.getAvatarID());

        // Check the database for the user
        UserDTO registeredUser = userService.getUserByUsername("Muccarini");
        assertNotNull(registeredUser);
        assertEquals("Muccarini", registeredUser.getUsername());

        userService.loginUser("Muccarini", "password");
        assertEquals("Muccarini", appContext.getCurrentUser().getUsername());
    }

    // @AfterEach
    // public void tearDown() {
    //     userDAO.delete(usrEnt);
    //     assertNull(AppContext.currentUser());
    // }
}
