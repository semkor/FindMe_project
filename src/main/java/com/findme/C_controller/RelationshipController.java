package com.findme.C_controller;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.User;
import com.findme.D_service.RelationshipService;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.LimitationException;
import com.findme.F_exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;



@Controller
public class RelationshipController {
    private RelationshipService relService;
    private UserController userController;
    private static final Logger log = LoggerFactory.getLogger(RelationshipController.class);

    @Autowired
    public RelationshipController(RelationshipService relService, UserController userController) {
        this.relService = relService;
        this.userController = userController;
    }

    //---------------------------- lesson5/6_hw (добавление/обновление друзей ) ----------------------------------------
    //  - AJAX - возваращает список друзей  - (если сессии нет - из - за getSessionId() будет 404 ошибка)
    @GetMapping(value="/allFriends")
    public ResponseEntity<List<User>> Friends(HttpSession session) throws UnauthorizedException {
        return new ResponseEntity<List<User>> (relService.friends(getSessionId(session)), HttpStatus.OK);
    }

    // - AJAX - возвращает список кто мне отправил запрос
    @GetMapping(value="/incomeRequest")
    public ResponseEntity<List<User>> getIncomeRequests(HttpSession session)  throws UnauthorizedException {
        return new ResponseEntity<List<User>> (relService.incomeRequests(getSessionId(session)), HttpStatus.OK);
    }

    // - AJAX - возвращает список кому я отправил запрос
    @GetMapping(value="/outcomeRequest")
    public ResponseEntity<List<User>> getOutcomeRequests (HttpSession session) throws UnauthorizedException {
        return new ResponseEntity<List<User>> (relService.outcomeRequests(getSessionId(session)), HttpStatus.OK);
    }

    // - AJAX - отправка заявки на дружбу
    @PostMapping(value="/add")
    public ResponseEntity<String>  addRelationship(HttpSession session, @RequestParam("userToId") String userToId) throws UnauthorizedException, LimitationException {
            log.info("addRelationship: " + " UserFromId = " + getSessionId(session) + " UserToId = "+ userToId);
            relService.addRelationship(getSessionId(session), userToId);
        return new ResponseEntity<String>("Application sent", HttpStatus.OK);
    }

    // - AJAX - обновить заявку на дружбу
    @PutMapping(value="/update")
    public ResponseEntity<String>  updateRelationship(HttpSession session, @RequestParam("userToIdUpdate") String userToId, @RequestParam("statusUpdate") Status status) throws UnauthorizedException, LimitationException {
           log.info("updateRelationship: " + " UserToId = "+ userToId + " Status = " + status);
           relService.updateRelationship (getSessionId(session), userToId, status);
        return new ResponseEntity<String>("Application completed", HttpStatus.OK);
    }

    //=============================================== vault ============================================================
    private String getSessionId(HttpSession session) throws UnauthorizedException {
        return userController.getSessionId(session);
    }
}
