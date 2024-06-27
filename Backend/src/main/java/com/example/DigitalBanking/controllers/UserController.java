package com.example.DigitalBanking.controllers;

import com.example.DigitalBanking.entites.AppUser;
import com.example.DigitalBanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AppUser registerUser(@RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}/password")
    public void updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
    }
}
