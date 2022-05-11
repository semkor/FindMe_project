package com.findme.HW;

import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.LimitationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {
    private MessageDAO messageDAO;
    private RelationshipDAO relDAO;
    private UserDAO userDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO, RelationshipDAO relDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.relDAO = relDAO;
        this.userDAO = userDAO;
    }

    @Value("${sqlExecute.sqlChainUserFriends}")             // запрос также используется в PostService
    private String sqlFriends;

    //------------------------------------------------ lesson11 --------------------------------------------------------
    public Message sentMessage(String userIdFrom, String userIdTo,  String message) throws LimitationException{
            if(relDAO.findBySQL(sqlFriends, userIdFrom, userIdTo) == null)
                throw new LimitationException("Messages can only be sent to friends");

            Message messageSent = new Message();
                messageSent.setDateSent(new Date());
                messageSent.setUserFrom(userDAO.findById(Long.parseLong(userIdFrom)));
                messageSent.setUserTo(userDAO.findById(Long.parseLong(userIdTo)));
                messageSent.setConditionMessage(false);
        return messageDAO.save(messageSent);
    }

    public Message updateMessage(long id, String message) throws LimitationException{
            Message messageUpdate = messageDAO.findById(id);

            if(messageUpdate.isConditionMessage())
                throw new LimitationException("Post can be edited until read");

            messageUpdate.setDateEdited(new Date());
            messageUpdate.setText(message);
        return messageDAO.update(messageUpdate);
    }

    public Message getMessage(long id){
            Message messageGet = messageDAO.findById(id);
                messageGet.setDateRead(new Date());
            messageDAO.update(messageGet);
        return messageGet;
    }

    public void deleteMessage(long id) throws LimitationException{
        Message messageDel = messageDAO.findById(id);

        if(messageDel.isConditionMessage())
            throw new LimitationException("Post can be deleted until read");

            messageDel.setDateDeleted(new Date());
        messageDAO.update(messageDel);
    }
}
