package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import org.springframework.stereotype.Component;


@Component
public class RequestDeniedHandler extends Handler {

    @Override
    // REQUEST_DENIED - REQUEST
    void status(Relationship relationship, Status status) {
        if(relationship.getStatus().equals(Status.REQUEST_DENIED) && status.equals(Status.REQUEST)){
            relationship.setStatus(Status.REQUEST);
            finishHandler(relationship);
        }
    }
}
