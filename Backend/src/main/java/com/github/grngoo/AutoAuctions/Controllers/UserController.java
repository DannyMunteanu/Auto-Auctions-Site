package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.Models.Users;
import com.github.grngoo.AutoAuctions.Services.UsersService;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        Optional<Users> userOpt = usersService.loginUser(username, password);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Invalid Username");
        }
    }

    //Reminder: Next commit I will need to use DTOs to clean up controllers!
    @PostMapping("/register")
    public ResponseEntity<String> registerUser
            (@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String telephone,
            @RequestParam String postal_code,
            @RequestParam String country)
    {
        Users user = new Users(username,password, email, telephone, postal_code, country);
        if (usersService.registerUser(user) != null){
            return ResponseEntity.ok("Account created successfully");
        } else {
            return ResponseEntity.status(401).body("Unable to make Account");
        }
    }
}
