package com.example.ioBuilders.bank.rest.user;

import com.example.ioBuilders.bank.domain.user.User;
import com.example.ioBuilders.bank.domain.user.UserServiceImpl;
import com.example.ioBuilders.bank.rest.user.controller.UserController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author i.fernandez@nchain.com
 *
 * Testing Suite for the REST Endpoints for Users
 */

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl usersService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * We check Response Status is OK when retrieve an Existing User
     * @throws Exception
     */
    @Test
    public void whenGetExistingUser_thenStatusIsOK() throws Exception {
        User user = new User("01", "Alice");
        when(usersService.getUser(any())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/v1/users/01").contentType("application/json")).andExpect(status().isOk());
    }

    /**
     * We check Response Status is 404 when retrieve a Non-Existing User
     * @throws Exception
     */
    @Test
    public void whenGetNonExistingUser_thenStatusIsNOTFOUND() throws Exception {
        when(usersService.getUser(any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/users/01").contentType("application/json")).andExpect(status().isNotFound());
    }

    /**
     * We check Response Status is OK when retrieving Users
     * @throws Exception
     */
    @Test
    public void whenGetUsers_thenStatusIsOK() throws Exception {
        mockMvc.perform(get("/v1/users").contentType("application/json")).andExpect(status().isOk());
    }

    /**
     * We check that Users are returned by the REST interface in the form of JSON String.
     * We don't check the actual content (that is business logic test), only that Users data is there
     * @throws Exception
     */
    @Test
    public void whenGetUsers_thenJsonContainsUsers() throws Exception {
        // The Service will return a Dummy List of Users:
        List<User> dummyUsers = new ArrayList<>() {{
            add(new User("01", "Alice"));
            add(new User("02", "Bob"));
        }};
        when(usersService.getUsers()).thenReturn(dummyUsers);

        // We run the REST Endpoint and check the JSON String contains Users in it.
        // In order to check that, we only verify that the JSON can be converted into List<User>,
        // but checking the content is not the purpose of this test.
        MvcResult result = mockMvc.perform(get("/v1/users").contentType("application/json")).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Set<User> users = new HashSet<>(mapper.readValue(json, new TypeReference<List<User>>(){}));
    }
}
