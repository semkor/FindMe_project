package com.findme.C_controller;

import com.findme.B_models.User;
import com.findme.D_service.PostService;
import com.findme.D_service.UserService;
import com.findme.F_exception.NotFoundException;
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
    private HttpSession sessionClass;
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
            log.info("Запуск стартовой страницы");
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
    //TODO - AJAX  при отправлении формы проверяет есть такой логин и пароль
    //            - если есть:
    //                  - открывает сессию
    //            - если нет:
    //                  - выдает ошибку
    //     - Thymeleaf перекидывает на страницу профиля по id

    //проверка логина и пароля через AJAX - открытие сессиии
    @PostMapping(value="/login")
    public ResponseEntity<String> login(HttpSession session, @ModelAttribute User user){

        User userBase = userService.validationLoginPassword(user.getLogin(), user.getPassword());
        if(userBase == null)
            return new ResponseEntity<String>("Invalid login or password", HttpStatus.BAD_REQUEST);

        sessionClass = session;
        String sessionId = (String) sessionClass.getAttribute(sessionLogin);

        if (sessionId == null){
            sessionId = String.valueOf(userBase.getId());
        }

        sessionClass.setAttribute(sessionLogin,sessionId);
        return new ResponseEntity<String>("User with this login exists", HttpStatus.OK);
    }

    // при успешном открытии сессии  нужно сразу перекинуть на страницу профиля
    // TODO можно без посредников перекидывать на /user - который выдаст страницу по id session
    @GetMapping (value="/login-valid")
    public String login(){
        System.out.println("cделал редирект");
        return "5.2_page404";
    }


    //выход из сессии
    @GetMapping(value="/logout")
    public String logout(@ModelAttribute User user){
            sessionClass.invalidate();
        return "redirect:/";
    }

//    --------------------------- lesson2_hw  (запускаем профиль  с данными по его ID) ---------------------------------
    @GetMapping(value="/user")
    public String profile(Model model){
        try {
            User user = userService.findById(Long.parseLong(getSessionId()));
            model.addAttribute("userModel", user);
            model.addAttribute("postList", postService.allPost(getSessionId()));        // c lesson 7.2
        }catch(NotFoundException | NumberFormatException e){
            log.error("profile_Error_NotFoundException | NumberFormatException",e);
            return "5.2_page404";
        }catch(Exception e){
            log.error("profile_Error_Exception",e);
            return "5.3_page500";
        }
        return "2.0_profile";
    }

    //=============================================== vault ============================================================
    //TODO  нужно, чтобы в этом методе обрабатывалась ошибка и выдавала 404 ошибку, если сессия не открыта
    public String getSessionId (){
            if (sessionClass == null)
                return "page404";
        return (String) sessionClass.getAttribute(sessionLogin);
    }
}
