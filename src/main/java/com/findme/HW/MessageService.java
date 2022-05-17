package com.findme.HW;

import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.LimitationException;
import com.findme.B_models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Value("${sqlExecute.sqlChatHistory}")
    private String sqlChatHistory;
    @Value("${sqlExecute.sqlAllUpdate}")
    private String sqlAllUpdate;




    //------------------------------------------------ lesson11 --------------------------------------------------------
    public Message sentMessage(String userIdFrom, String userIdTo, String message) throws LimitationException{
            if(relDAO.findBySQL(sqlFriends, userIdFrom, userIdTo) == null)
                throw new LimitationException("Messages can only be sent to friends");

            Message messageSent = new Message();
                messageSent.setDateSent(new Date());
                messageSent.setUserFrom(userDAO.findById(Long.parseLong(userIdFrom)));
                messageSent.setUserTo(userDAO.findById(Long.parseLong(userIdTo)));
                messageSent.setStatusMessage(StatusMessage.UNREAD);
        return messageDAO.save(messageSent);
    }

    public Message updateMessage(long id, String message) throws LimitationException{
            Message messageUpdate = messageDAO.findById(id);

            if(!messageUpdate.getStatusMessage().equals(StatusMessage.UNREAD))
                throw new LimitationException("Post can be edited until read");

            messageUpdate.setDateEdited(new Date());
            messageUpdate.setText(message);
            messageUpdate.setStatusMessage(StatusMessage.EDITED);
        return messageDAO.update(messageUpdate);
    }

    public Message getMessage(long id){
            Message messageGet = messageDAO.findById(id);
                messageGet.setDateRead(new Date());
                messageGet.setStatusMessage(StatusMessage.READ);
            messageDAO.update(messageGet);
        return messageGet;
    }

    public void deleteMessage(long id) throws LimitationException{
        Message messageDel = messageDAO.findById(id);

        if(!messageDel.getStatusMessage().equals(StatusMessage.UNREAD))
            throw new LimitationException("Post can be deleted until read");

            messageDel.setDateDeleted(new Date());
        messageDAO.update(messageDel);
    }

    //------------------------------------------------ lesson12 --------------------------------------------------------
    private final int limit = 5;
    public int count = 0;

    public List<Message> getChattingHistory(String UserFrom, String UserTo){
            List<Message> list = messageDAO.findBySQL(sqlChatHistory, UserFrom, UserTo, limit);
            count = limit;
        return list;
    }

    public List<Message> getMoreChattingHistory(String UserFrom, String UserTo){
            List<Message> list = messageDAO.findBySQL(sqlChatHistory, UserFrom, UserTo, limit+count);
            count += limit;
        return list;
    }

    public void deleteHistory(String[] IdMessage) throws LimitationException{
        if(IdMessage.length > 10)
            throw new LimitationException("You can't delete more than 10 messages at a time");
        for(String el : IdMessage){
            Message messageDel = messageDAO.findById(Long.parseLong(el));
                messageDel.setDateDeleted(new Date());
            messageDAO.update(messageDel);
        }
    }

    public void deleteAllHistory(String UserFrom, String UserTo){
        messageDAO.updateSQL(sqlAllUpdate, UserFrom, UserTo);
    }
}
