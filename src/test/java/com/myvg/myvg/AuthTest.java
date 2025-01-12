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

    //FUNCTIONAL TEST
    @Test
    public void testLogin() throws Exception {
        
        //userService.registerUser(testUser.getUsername(), "password", testUser.getEmail(), testUser.getAvatarID());

        loginController.register(testUser.getUsername(), "password", testUser.getEmail());
        //loginController.login("Example", "password"); throw exception on switch scene afte login

        //functional test
        assertEquals(loginController, AppContext.getInstance().getCurrentUser());

        //integration test
        UserDTO registeredUser = userService.getUserByUsername("Example");
        assertNotNull(registeredUser);
        assertEquals("Example", registeredUser.getUsername());

    }

    //INTEGRATION TEST

    @AfterEach
    public void tearDown() {
        userService.deleteUser("Example", "password");
        assertNull(AppContext.getInstance().getCurrentUser());
    }
}
