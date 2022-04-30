package com.findme.HHHHHOMEWORKKKKK;

import com.findme.B_models.User;
import com.findme.HHHHHOMEWORKKKKK.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/register-user")
    public ResponseEntity<String> register(@ModelAttribute User user){

            String resultError = userService.validation(user.getPhone(), user.getEmail());
            if(!resultError.equals("ok")) {
                return new ResponseEntity<String>(resultError, HttpStatus.BAD_REQUEST);
            }

            userService.save(user);
        return new ResponseEntity<String>("Form submitted successfully", HttpStatus.OK);
    }
}
