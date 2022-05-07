package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.D_service.RelationshipService;
import com.findme.E_dao.RelationshipDAO;
import com.findme.F_exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subsequence {
    private RequestHandler requestHandler;
    private FriendsHandler friendsHandler;
    private FormerFriends formerFriends;
    private RequestDeniedHandler requestDeniedHandler;
        private RelationshipDAO relationshipDAO;

    @Autowired
    public Subsequence(RequestHandler requestHandler, FriendsHandler friendsHandler, FormerFriends formerFriends, RequestDeniedHandler requestDeniedHandler, RelationshipDAO relationshipDAO) {
        this.requestHandler = requestHandler;
        this.friendsHandler = friendsHandler;
        this.formerFriends = formerFriends;
        this.requestDeniedHandler = requestDeniedHandler;
        this.relationshipDAO = relationshipDAO;
    }

    public void sequence(Relationship relationship, Status status) throws BadRequestException {
            requestHandler.setNextHandler(friendsHandler, relationshipDAO);
            friendsHandler.setNextHandler(formerFriends, relationshipDAO);
            formerFriends.setNextHandler(requestDeniedHandler, relationshipDAO);
            requestDeniedHandler.setNextHandler(null, relationshipDAO);
        requestHandler.setStatus(relationship, status);
    }
}
