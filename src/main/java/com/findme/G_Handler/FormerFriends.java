package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import org.springframework.stereotype.Component;


@Component
public class FormerFriends extends Handler {

    @Override
    // FORMER_FRIENDS - REQUEST
    void status(Relationship relationship, Status status) {
        if(relationship.getStatus().equals(Status.FORMER_FRIENDS) && status.equals(Status.REQUEST)){
            relationship.setStatus(Status.REQUEST);
            finishHandler(relationship);
        }
    }
}
