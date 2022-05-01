package com.findme.HHHHHOMEWORKKKKK;

import com.findme.B_models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;


@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //------------------------------------------------ hw lesson2 ------------------------------------------------------
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

    //---------------------------------------------- lesson3 hw --------------------------------------------------------
    @PostMapping(value="/register-user")
    public ResponseEntity<String> register(@ModelAttribute User user){

            String resultError = userService.validation(user.getPhone(), user.getEmail());
            if(!resultError.equals("ok")) {
                return new ResponseEntity<String>(resultError, HttpStatus.BAD_REQUEST);
            }

            userService.save(user);
        return new ResponseEntity<String>("Form submitted successfully", HttpStatus.OK);
    }

    //----------------------------------------------- lesson4hw --------------------------------------------------------
    @PostMapping(value="/login")
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                        @ModelAttribute User user){

        User userBase = userService.validationLogin(user.getLogin(), user.getPassword());
        if(userBase == null)
            return new ResponseEntity<String>("Invalid login or password", HttpStatus.BAD_REQUEST);

        Long sessionId = (Long) session.getAttribute("userId");
        if (sessionId == null)
            sessionId = userBase.getId();
        session.setAttribute("userId",sessionId);

        return new ResponseEntity<String>("User with this login exists", HttpStatus.OK);
    }

    @GetMapping(value="/logout")
    public ResponseEntity<String> logout(HttpSession session, @ModelAttribute User user){
            session.invalidate();
        return new ResponseEntity<String>("session is closed", HttpStatus.OK);
    }
}
