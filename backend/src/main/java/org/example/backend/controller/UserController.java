package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.UserDto;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured")
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public String getUsername(Principal principal) {
        if (principal == null) {
            return "you are not authorized";
        }
        return principal.getName();
    }

    @GetMapping("/friends")
    public List<UserDto> getFriends(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("you are not authorized");
        }

        return userService.getFriendsList(principal.getName());
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("you are not authorized");
        }

        return userService.getUsersList(principal.getName());
    }

    @PostMapping("/friends/add/{friendId}")
    public void addFriend(@PathVariable String friendId, Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("you are not authorized");
        }
        Long friend = Long.parseLong(friendId);
        userService.addFriend(principal.getName(), friend);
    }
}
