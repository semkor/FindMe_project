package com.findme.C_controller;

import com.findme.B_models.User;
import com.findme.D_service.PostService;
import com.findme.D_service.UserService;
import com.findme.F_exception.NotFoundException;
import com.findme.F_exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
public class UserController {
    private UserService userService;
    private PostService postService;

    private final String sessionLogin = "userId";
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

   //-------------------------------------  первичная страница  (логин & пароль) ---------------------------------------
    @GetMapping(value="/")
    public String home() {
        return "0.0_index";
    }

    //--------------------------- lesson3_hw  (переход на форму регистрации / регистрация ) ----------------------------
    @GetMapping(value="/registration-form")
    public String registration(@ModelAttribute("user") User user){
        return "1.0_registration";
    }

    //проверка на существование Login
    @PostMapping(value="/register-valid-login")
    public ResponseEntity<String> validLogin(@RequestParam String login){
        if(!userService.validationLogin(login).isEmpty())
            return new ResponseEntity<String>("enter another login", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("ОК", HttpStatus.OK);
    }

    //проверка на существование Phone
    @PostMapping(value="/register-valid-phone")
    public ResponseEntity<String> validPhone(@RequestParam String phone){
        if(!userService.validationPhone(phone).isEmpty())
            return new ResponseEntity<String>("enter another pnone number", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("ОК", HttpStatus.OK);
    }

    //проверка на существование Email
    @PostMapping(value="/register-valid-email")
    public ResponseEntity<String> validEmail(@RequestParam String email){
        if(!userService.validationEmail(email).isEmpty())
            return new ResponseEntity<String>("enter another e-mail", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("ОК", HttpStatus.OK);
    }

    //cохранение Usera
    @PostMapping(value="/register-user")
    public String register(@ModelAttribute User user){
            user.setDateRegistered(new Date());
            user.setDateLastActive(new Date());
          userService.save(user);
        return "redirect:/";
    }

    //-------------------------------------- lesson4_hw (вход и запуск сессии) -----------------------------------------
    //проверка логина и пароля через AJAX - открытие сессиии
    @PostMapping(value="/login")
    public ResponseEntity<String> login(HttpSession session, @ModelAttribute User user){

        User userBase = userService.validationLoginPassword(user.getLogin(), user.getPassword());
        if(userBase == null)
            return new ResponseEntity<String>("Invalid login or password", HttpStatus.BAD_REQUEST);

        String sessionId = (String) session.getAttribute(sessionLogin);
        if (sessionId == null)
            sessionId = String.valueOf(userBase.getId());

        session.setAttribute(sessionLogin,sessionId);
        return new ResponseEntity<String>("Hello!!!", HttpStatus.OK);
    }

    //выход из сессии
    @GetMapping(value="/logout")
    public String logout(HttpSession session, @ModelAttribute User user){
            session.invalidate();
        return "redirect:/";
    }

    //---------------------------- lesson2_hw  (запускаем профиль  с данными по его ID) --------------------------------
    @GetMapping(value="/user")
    public String profile(HttpSession session, Model model) throws UnauthorizedException, NotFoundException {
            User user = userService.findById(Long.parseLong(getSessionId(session)));
            model.addAttribute("userModel", user);
            model.addAttribute("postList", postService.allPost(getSessionId(session)));              // c lesson 7.2
        return "2.0_profile";
    }

    //=============================================== vault ============================================================
    public String getSessionId(HttpSession session) throws UnauthorizedException {
        String sessionId = (String) session.getAttribute(sessionLogin);
        if(sessionId == null)
                throw new UnauthorizedException("Session failed");
        return sessionId;
    }
}
