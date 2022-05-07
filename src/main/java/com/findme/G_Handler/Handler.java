package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.E_dao.RelationshipDAO;
import com.findme.F_exception.BadRequestException;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public abstract class Handler {
    public RelationshipDAO relationshipDAO;
    private Handler nextHandler;

    public void setNextHandler(Handler handler, RelationshipDAO relationshipDAO) {
        nextHandler = handler;
        this.relationshipDAO = relationshipDAO;
    }

    //------------------------------------------------------------------------------------------------------------------
    public void  setStatus (Relationship relationship, Status status) throws BadRequestException{
        status (relationship, status);
        if(nextHandler != null){
            nextHandler.setStatus(relationship, status);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    abstract void status (Relationship relationship, Status status) throws BadRequestException;

    public void finishHandler(Relationship relationship){
        relationship.setDateChangeStatus(new Date());
        relationshipDAO.update(relationship);
        setNextHandler(null, null);
    }
}
