package com.findme.hw;

import com.findme.B_models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    @GetMapping(value="/user/{userId}")
    public String profile(@PathVariable long userId, Model model) throws FileNotFoundException, IOException {
            User user = userService.findById(userId);
            if (user == null)
                throw new FileNotFoundException ("User not found");
            model.addAttribute("userModel", user);
        return "profile";
        }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "FileNotFoundException check arguments!")
    @ExceptionHandler(FileNotFoundException.class)
    public void handlerFileNotFoundException(){
        System.err.println("FileNotFoundException check arguments!");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "IOException ")
    @ExceptionHandler(IOException.class)
    public void handlerIOException(){
        System.err.println("IOException");
    }
}
