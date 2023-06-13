package com.ioBuilders.bank.infrastructure.rest.user;

import com.ioBuilders.bank.domain.user.model.User;
import com.ioBuilders.bank.domain.user.model.UserException;
import com.ioBuilders.bank.domain.user.ports.in.UserService;
import com.ioBuilders.bank.infrastructure.rest.ResourceNotFoundException;
import com.ioBuilders.bank.infrastructure.rest.RestApiError;
import com.ioBuilders.bank.infrastructure.rest.account.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 *
 * REST Interface for Users
 */
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    private UserView toView(User user) {
        Link linkSelf = linkTo(methodOn(UserController.class).getUser(user.getDni())).withSelfRel();
        Link linkAccounts = linkTo(methodOn(AccountController.class).getAccounts(user.getDni())).withRel("accounts");
        UserView userView = new UserView(user);
        userView.add(linkSelf);
        userView.add(linkAccounts);
        return userView;
    }

    @PostMapping(path = "")
    public ResponseEntity<UserView> createUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>(toView(user), HttpStatus.OK);
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<UserView>> getUsers() {
        List<UserView> usersView = this.userService.getUsers().stream().map(u -> toView(u)).collect(Collectors.toList());
        return new ResponseEntity<>(usersView, HttpStatus.OK);
    }

    @GetMapping(path = "/{dni}", produces = "application/json")
    public ResponseEntity<UserView> getUser(@PathVariable String dni) {

        Optional<User> user = userService.getUser(dni);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User");
        }
        return new ResponseEntity<>(toView(user.get()), HttpStatus.OK);
    }
}
