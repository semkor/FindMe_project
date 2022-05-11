package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.LimitationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler extends Handler {
    @Value("${sqlExecute.sqlFriendsCount}")
    private String sqlFriendsCount;

    @Override
    // REQUEST - FRIENDS (+ проверка мах 100 друзей) | REQUEST_DENIED
    void status(Relationship relationship, Status status) throws LimitationException {
        if(relationship.getStatus().equals(Status.REQUEST) && (status.equals(Status.FRIENDS) || status.equals(Status.REQUEST_DENIED))){
                if(status.equals(Status.FRIENDS)){
                    if (friendsCount(relationship.getUserFromId()) >= 100 && friendsCount(relationship.getUserFromId()) >= 100)
                        throw new LimitationException("You can't have more than 100 friends");
                    relationship.setStatus(Status.FRIENDS);
                    finishHandler(relationship);
                }else {
                    relationship.setStatus(Status.REQUEST_DENIED);
                    finishHandler(relationship);
                }
        }
    }

    private Integer friendsCount(String userId){
        return relationshipDAO.findByIntFriends(sqlFriendsCount, userId);
    }
}
