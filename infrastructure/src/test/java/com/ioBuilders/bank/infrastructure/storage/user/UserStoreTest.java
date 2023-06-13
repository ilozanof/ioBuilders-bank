package com.ioBuilders.bank.infrastructure.storage.user;


import com.ioBuilders.bank.domain.user.model.User;
import com.ioBuilders.bank.domain.user.model.UserException;
import com.ioBuilders.bank.infrastructure.BankApplicationTest;
import com.ioBuilders.bank.infrastructure.TestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author i.fernandez@nchain.com
 *
 * Testing Suite for the User Store Adapter (H2 in-memory DB)
 */
@SpringBootTest(classes = BankApplicationTest.class)
@Import(TestConfiguration.class)
public class UserStoreTest {

    @Autowired
    private UserStoreForTesting store;


    @BeforeEach
    private void beforeEach() {
        store.clear();
    }

    @Test
    public void givenUserIsCreated_thenICanRetrieveIt() {
        store.createUser(new User("1", "Alice"));
        User user = store.findUser("1").get();
        assertNotNull(user);
    }

    @Test
    public void givenUsersAreCreated_thenICanRetrieveThem() {
        List<User> usersStored = new ArrayList<>() {{
            add(new User("1", "Alice"));
            add(new User("2", "Bob"));
        }};
        usersStored.forEach(u -> store.createUser(u));

        Set<User> usersRead = new HashSet<>(store.getUsers());
        assertNotNull(usersRead);
        assertTrue(usersRead.size() == usersStored.size());
        assertTrue(usersStored.stream().allMatch(u -> usersRead.contains(u)));
    }

    @Test
    public void givenUserDoNotExist_thenICanNotRetrieveIt() {
        Optional<User> user = store.findUser("1");
        assertTrue(user.isEmpty());
    }

    @Test
    public void givenUserExists_thenICanNOtCreateAnotherUserWithSameId() {
        try {
            store.createUser(new User("1", "Alice"));
            store.createUser(new User("1", "Bob")); // Same Id!!
            fail(); // It shouldn't get here
        } catch (UserException e) {
            // pass
        }
    }
}
