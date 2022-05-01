package com.findme.D_service;

import com.findme.B_models.Message;
import com.findme.E_dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    //---------------------------------------------------------------------------------------------------------
    public Message findById(long id){
        return messageDAO.findById(id);
    }

    public Message save(Message message){
        return messageDAO.save(message);
    }

    public Message update(Message message){
        return messageDAO.update(message);
    }

    public void delete(long id){
        messageDAO.delete(id);
    }

    //---------------------------------------------------------------------------------------------------------
}
