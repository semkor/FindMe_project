package com.findme.C_controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path="/")
    public String home(){
            System.out.println("запускается стартовая страница");
        return "index";
    }

    @GetMapping(path="/test-ajax")
    public ResponseEntity<String> testAjax(){
        System.out.println("работает кнопка Exit");
        return new ResponseEntity<String> ("trouble", HttpStatus.NOT_FOUND);
    }



    //---------------------------------------------- lesson2 -----------------------------------------------------------
//    @GetMapping(value="/user/{id}")
//    public String profile(@PathVariable String id, Model model){
//        User user = new User();
//            user.setFirstName("Sergey");
//            user.setCity("Soul");
//        model.addAttribute("userModel",user);
//        return "profile";
//    }

    //------------------------------------------------ hw lesson2 ------------------------------------------------------



//    @GetMapping(value="/user/{userId}")
//    public String profile(@PathVariable long userId, Model model){
//        try {
//            User user = userService.findById(userId);
//            model.addAttribute("userModel", user);
//        }catch(BadRequestException e){
//            return "page400";
//        }catch(NotFoundException e){
//            return "page404";
//        }catch(Exception e){
//            return "page500";
//        }
//        return "profile";
//    }


}