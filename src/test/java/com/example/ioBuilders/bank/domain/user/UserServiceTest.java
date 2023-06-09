package com.example.ioBuilders.bank.domain.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author i.fernandez@nchain.com
 *
 * Testing suite for the UserService
 */
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserStorage userStorage;

    @Test
    public void givenUSersAreCreated_thenWeCanGetThem() {
        when(userStorage.getUsers()).thenReturn(null);
        List<User> users = userService.getUsers();
        System.out.println("End");
    }
}
