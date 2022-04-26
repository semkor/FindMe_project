package com.findme.C_controller;

import com.findme.B_models.User;
import com.findme.D_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //---------------------------------------------- пример lesson2 ----------------------------------------------------
//    @GetMapping(value="/user/{id}")
//    public String profile(@PathVariable String id, Model model){
//        User user = new User();
//            user.setFirstName("Sergey");
//            user.setCity("Soul");
//            model.addAttribute("userModel",user);
//    return "profile";
//    }

    //------------------------------------------------------------------------------------------------------------------




}
