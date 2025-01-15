package com.myvg.myvg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myvg.myvg.DTO.UserDTO;
import com.myvg.myvg.Controllers.AuthController;
import com.myvg.myvg.Services.AppContext;
import com.myvg.myvg.Services.UserService;

@SpringBootTest
public class FunctionalAuthTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthController authcontroller;

    private UserDTO testUser;

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO("Example", "example@gmail.com", 
                        0, null, null);
    }
    @Test
    public void testUserRegistrationAndLogin() throws Exception {
        
        //reg test
        authcontroller.register(testUser.getUsername(), "password", testUser.getEmail());
        UserDTO registeredUser = userService.getUserByUsername("Example");

        assertNotNull(registeredUser);
        assertEquals(testUser.getUsername(), registeredUser.getUsername());
        
        //login test
        authcontroller.login("Example", "password");
        UserDTO loggedUser = AppContext.getInstance().getCurrentUser();

        assertNotNull(loggedUser);
        assertEquals(testUser.getUsername(), loggedUser.getUsername());
    }

    //INTEGRATION TEST

    @AfterEach
    public void tearDown() {
        userService.deleteUser("Example", "password");
    }
}
