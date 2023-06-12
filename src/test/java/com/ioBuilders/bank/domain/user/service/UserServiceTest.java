package com.ioBuilders.bank.domain.user.service;

import com.ioBuilders.bank.app.rest.BankApplication;
import com.ioBuilders.bank.domain.user.model.User;
import com.ioBuilders.bank.domain.user.ports.out.UserStorage;
import com.ioBuilders.bank.domain.user.service.UserServiceImpl;
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
@SpringBootTest(classes = BankApplication.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserStorage userStorage;

    @Test
    public void givenUsersAreCreated_thenWeCanGetThem() {
        when(userStorage.getUsers()).thenReturn(null);
        List<User> users = userService.getUsers();
        System.out.println("End");
    }
}
