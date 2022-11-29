package ru.kolesnikov.desiccisionfarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolesnikov.desiccisionfarm.controller.dto.UserDTO;
import ru.kolesnikov.desiccisionfarm.model.User;
import ru.kolesnikov.desiccisionfarm.service.UserService;

/**
 * This will be used for registration
 * add user as authorized
 * delete user as logout
 * it is only example because of low time in task
 */

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/user")
    public void addUser(@RequestBody UserDTO user) {
        userService.addUser(new User(user.login(), user.password(), user.name()));
    }

    @DeleteMapping("/api/v1/user/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("login") String login) {
        userService.deleteUser(login);
    }


}
