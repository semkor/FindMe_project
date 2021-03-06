package com.findme.HW;

import com.findme.C_controller.UserController;
import com.findme.F_exception.LimitationException;
import com.findme.F_exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class MessageController{
    private MessageService messageService;
    private UserController userController;

    @Autowired
    public MessageController(MessageService messageService, UserController userController) {
        this.messageService = messageService;
        this.userController = userController;
    }

    //------------------------------------------------ lesson11 --------------------------------------------------------
    @PostMapping(value = "/sentMessage")        // отправить сообщение (размер ограничиваем на HTML)
    public ResponseEntity<String>  sentMessage (HttpSession session, @RequestParam("message") String message, @RequestParam("userTo") String userTo) throws UnauthorizedException, LimitationException {
            messageService.sentMessage(getSessionId(session), userTo, message);
        return new ResponseEntity<String> ("Message sent", HttpStatus.OK);
    }

    @PutMapping(value = "/updateMessage")   // редактировать сообщение (размер ограничиваем на HTML)
    public ResponseEntity<String>  updateMessage (HttpSession session, @RequestParam("message") String message, @RequestParam("id") long idMessage) throws UnauthorizedException, LimitationException {
            getSessionId(session);
            messageService.updateMessage(idMessage, message);
        return new ResponseEntity<String> ("Message update", HttpStatus.OK);
    }

    @GetMapping(value = "/getMessage")      // прочитать сообщение
    public String  getMessage (HttpSession session, @RequestParam("id") long idMessage , Model model) throws UnauthorizedException {
            getSessionId(session);
            model.addAttribute("message", messageService.getMessage(idMessage));
        return "2.0_profile";
    }

    @DeleteMapping(value = "/deleteMessage")    // удалить сообщение
    public ResponseEntity<String>  deleteMessage (HttpSession session, @RequestParam("id") long idMessage) throws UnauthorizedException, LimitationException {
            getSessionId(session);
            messageService.deleteMessage(idMessage);
        return new ResponseEntity<String> ("Message deleted", HttpStatus.OK);
    }


    //------------------------------------------------ lesson12 --------------------------------------------------------
    @GetMapping(value = "/chattingHistory")        // получение по умолчанию 20 сообщений (удаленные сообщения не учитываются)
    public String  chattingHistory (HttpSession session, @RequestParam("userTo") String userTo, Model model) throws UnauthorizedException {
            model.addAttribute("chattingHistory", messageService.getChattingHistory(getSessionId(session),userTo));
        return "4.0_message";
    }

    @GetMapping(value = "/moreСhattingHistory")        // полуение сообщений 20 + 20 (удаленные сообщения не учитываются)
    public String moreChattingHistory(HttpSession session, @RequestParam("userTo") String userTo, Model model) throws UnauthorizedException {
        model.addAttribute("chattingHistory", messageService.getMoreChattingHistory(getSessionId(session),userTo));
        return "4.0_message";
    }

    @PutMapping(value = "/updateСhattingHistory")     // удалить одно сообщение, но не более 10 за один раз
    public ResponseEntity<String>  updateСhattingHistory (HttpSession session, @RequestParam("IdMessage") String[] idMessage) throws UnauthorizedException, LimitationException {
            getSessionId(session);
            messageService.deleteHistory(idMessage);
        return new ResponseEntity<String> ("Chatting message deleted", HttpStatus.OK);
    }


    @PutMapping(value = "/updateAllСhattingHistory")     // удалить всю переписку
    public ResponseEntity<String>  updateAllСhattingHistory (HttpSession session, @RequestParam("userTo") String userTo) throws UnauthorizedException {
            messageService.deleteAllHistory(getSessionId(session),userTo);
        return new ResponseEntity<String> ("Chatting history deleted", HttpStatus.OK);
    }

    //=============================================== vault ============================================================
    private String getSessionId(HttpSession session)  throws UnauthorizedException {
        return userController.getSessionId(session);
    }
}
