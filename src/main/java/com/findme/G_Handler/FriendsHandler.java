package com.findme.G_Handler;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.LimitationException;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class FriendsHandler extends Handler{

    @Override
        // FRIENDS - FORMER_FRIENDS (+ проверка - можно убрат с друзей только если прошло 3дня)
    void status(Relationship relationship, Status status) throws LimitationException {
        if(relationship.getStatus().equals(Status.FRIENDS) && status.equals(Status.FORMER_FRIENDS)){
              if(timeSpanCheck(relationship.getDateChangeStatus()))
                      throw new LimitationException("You can only remove from friends after 3 days from the date of friendship");
                relationship.setStatus(Status.FORMER_FRIENDS);
                finishHandler(relationship);
        }
    }

    private boolean timeSpanCheck (Date date){
            if ((double) (new Date().getTime() - date.getTime() / (60 * 60 * 1000)) < 72.0)
                return true;
        return false;
    }
}
