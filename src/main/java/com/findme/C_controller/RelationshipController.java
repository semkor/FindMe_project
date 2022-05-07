package com.findme.C_controller;

import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import com.findme.D_service.RelationshipService;
import com.findme.F_exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RelationshipController {
    private RelationshipService relService;
    private UserController userController;

    @Autowired
    public RelationshipController(RelationshipService relService, UserController userController) {
        this.relService = relService;
        this.userController = userController;
    }

    //---------------------------- lesson5/6_hw (добавление/обновление друзей ) ----------------------------------------
    //  - AJAX - возваращает список друзей  - (если сессии нет - из - за getSessionId() будет 404 ошибка)
    @GetMapping(value="/allFriends")
    public ResponseEntity<List<User>> Friends(){
        return new ResponseEntity<List<User>> (relService.friends(getSessionId()), HttpStatus.OK);
    }

    // - AJAX - возвращает список кто мне отправил запрос
    @GetMapping(value="/incomeRequest")
    public ResponseEntity<List<User>> getIncomeRequests(){
        return new ResponseEntity<List<User>> (relService.incomeRequests(getSessionId()), HttpStatus.OK);
    }

    // - AJAX - возвращает список кому я отправил запрос
    @GetMapping(value="/outcomeRequest")
    public ResponseEntity<List<User>> getOutcomeRequests (){
        return new ResponseEntity<List<User>> (relService.outcomeRequests(getSessionId()), HttpStatus.OK);
    }

    // - AJAX - отправка заявки на дружбу
    @PostMapping(value="/add")
    public ResponseEntity<String>  addRelationoship (@ModelAttribute Relationship reship){
            try {
                relService.addRelationoship(reship.getUserFromId(), reship.getUserToId());
            }catch (BadRequestException e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<String>("Application sent", HttpStatus.OK);
    }

    // - AJAX - обновить заявку на дружбу
    @PutMapping(value="/update")
    public ResponseEntity<String>  updateRelationship (@ModelAttribute Relationship reship){
        System.out.println(reship);
            try {
                relService.updateRelationship (reship.getUserFromId(), reship.getUserToId(), reship.getStatus());
            }catch (BadRequestException e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<String>("Application completed", HttpStatus.OK);
    }


    //=============================================== vault ============================================================
    private String getSessionId(){
        return userController.getSessionId();
    }
}
