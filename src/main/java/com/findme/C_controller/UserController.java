package com.findme.C_controller;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import com.findme.D_service.UserService;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.NotFoundException;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    private UserService userService;
    private HttpSession sessionClass;
    private final String sessionLogin = "userId";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

   // -------------------------------------------- первичная страница --------------------------------------------------
    @GetMapping(value="/")
    public String home(){
        System.out.println("запускается стартовая страница");
        return "index";
    }

    //------------------------------------------------ lesson2_hw ------------------------------------------------------
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

    //---------------------------------------------- lesson3_hw --------------------------------------------------------
    @PostMapping(value="/register-user")
    public ResponseEntity<String> register(@ModelAttribute User user){

            String resultError = userService.validation(user.getPhone(), user.getEmail());
            if(!resultError.equals("ok")) {
                return new ResponseEntity<String>(resultError, HttpStatus.BAD_REQUEST);
            }

            userService.save(user);
        return new ResponseEntity<String>("Form submitted successfully", HttpStatus.OK);
    }

    //----------------------------------------------- lesson4_hw -------------------------------------------------------
    @PostMapping(value="/login")
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                        @ModelAttribute User user){
        sessionClass = session;

        String sessionId = (String) sessionClass.getAttribute(sessionLogin);
        if (sessionId == null){
            User userBase = userService.validationLogin(user.getLogin(), user.getPassword());
            if(userBase == null)
                return new ResponseEntity<String>("Invalid login or password", HttpStatus.BAD_REQUEST);
            sessionId = String.valueOf(userBase.getId());
        }

        sessionClass.setAttribute(sessionLogin,sessionId);

        return new ResponseEntity<String>("User with this login exists", HttpStatus.OK);
    }

    @GetMapping(value="/logout")
    public ResponseEntity<String> logout(@ModelAttribute User user){
            if(sessionClass.getAttribute(sessionLogin) == null)
                return new ResponseEntity<String>("Session is not opened", HttpStatus.BAD_REQUEST);

            sessionClass.invalidate();
        return new ResponseEntity<String>("Session is closed", HttpStatus.OK);
    }

    //----------------------------------------------- lesson5_hw -------------------------------------------------------
    // поиск списка моих друзей
    @GetMapping(value="/allFriends")
    public ResponseEntity<List<Relationship>> Friends(){
        return new ResponseEntity<List<Relationship>> (userService.friends(getSessionId()), HttpStatus.OK);
    }

    // список кто мне отправил запрос
    @GetMapping(value="/incomeRequest")
    public ResponseEntity<List<Relationship>> getIncomeRequests(){
        return new ResponseEntity<List<Relationship>> (userService.incomeRequests(getSessionId()), HttpStatus.OK);
    }

    // список кому я отправил запрос
    @GetMapping(value="/outcomeRequest")
    public ResponseEntity<List<Relationship>> getOutcomeRequests (){
         return new ResponseEntity<List<Relationship>> (userService.outcomeRequests(getSessionId()), HttpStatus.OK);
    }

    // отправка заявки на дружбу   //еще проверка, а может он в друзьях
    @PostMapping(value="/add")
    public ResponseEntity<String>  addRelationoship (@ModelAttribute Relationship reship){
        try {
            userService.addRelationoship(reship.getUserFromId(), reship.getUserToId());
        }catch (Exception e){
            return new ResponseEntity<String>("Application has already been sent", HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<String>("Application sent", HttpStatus.OK);
    }

    // обновить заявку на дружбу
    @PutMapping(value="/update")
    public ResponseEntity<String>  updateRelationship (@ModelAttribute Relationship reship){
            userService.updateRelationship (reship.getUserFromId(), reship.getUserToId(), reship.getStatus());
        return new ResponseEntity<String>("Application completed", HttpStatus.OK);
    }


    //=============================================== vault ============================================================
    private String getSessionId (){
            if (sessionClass == null)
                //TODO настроить, чтобы открывалась страница 404, когда нет сессии, что не позволит заходить на страницу
                return "page404";
        return (String) sessionClass.getAttribute(sessionLogin);
    }
}
