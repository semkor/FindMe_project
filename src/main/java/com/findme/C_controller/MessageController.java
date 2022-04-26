package com.findme.C_controller;

import com.findme.D_service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController (MessageService messageService) {
        this.messageService = messageService;
    }

    //------------------------------------------------------------------------------------------------------------------



}
