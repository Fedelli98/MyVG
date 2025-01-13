package com.myvg.myvg;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.DTO.VideogameDTO;
import com.myvg.myvg.Controllers.LoginController;
import com.myvg.myvg.DAO.UserDAO;
import com.myvg.myvg.DTO.ReviewDTO;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.UserService;
import com.myvg.myvg.EntityModel.UserEntity;

@SpringBootTest
public class AuthTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginController loginController;

    private UserDTO testUser;

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO("Example", "example@gmail.com", 0, null);
    }

    //FUNCTIONAL TEST / INTEGRATION TEST
    /**
     * Test case for user registration and login functionality.
     * 
     * This test performs the following steps:
     * 1. Registers a new user with the provided username, password, and email.
     * 2. Retrieves the registered user by username and verifies that the username matches the expected value.
     * 3. Logs in with the registered user's credentials.
     * 4. Retrieves the currently logged-in user and verifies that the username matches the expected value.
     * 
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    public void testUserRegistrationAndLogin() throws Exception {
        
        //reg test
        loginController.register(testUser.getUsername(), "password", testUser.getEmail());
        UserDTO registeredUser = userService.getUserByUsername("Example");

        //assertNotNull(registeredUser);
        assertEquals(testUser.getUsername(), registeredUser.getUsername());
        
        //login test
        loginController.login("Example", "password");
        UserDTO loggedUser = AppContext.getInstance().getCurrentUser();

        //assertNotNull(loggedUser);
        assertEquals(testUser.getUsername(), loggedUser.getUsername());


    }

    //INTEGRATION TEST

    @AfterEach
    public void tearDown() {
        userService.deleteUser("Example", "password");
    }
}
