package com.findme.D_service;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.BadRequestException;
import com.findme.G_Handler.Subsequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RelationshipService {
    private Subsequence subsequence;
    private RelationshipDAO relDAO;
    private UserDAO userDAO;

    @Autowired
    public RelationshipService(Subsequence subsequence, RelationshipDAO relDAO, UserDAO userDAO) {
        this.subsequence = subsequence;
        this.relDAO = relDAO;
        this.userDAO = userDAO;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Value("${sqlExecute.sqlFriends}")
    private String sqlFriends;
    @Value("${sqlExecute.sqlIncomeRequests}")
    private String sqlIncomeRequests;
    @Value("${sqlExecute.sqlOutcomeRequests}")
    private String sqlOutcomeRequests;

    @Value("${sqlExecute.sqlRequestCount}")
    private String sqlRequestCount;
    @Value("${sqlExecute.sqlChainUser}")
    private String sqlChainUser;


    //---------------------------------------------- lesson5/6 hw ------------------------------------------------------
    // cписок моих друзей
    public List<User> friends(String id) {
        return listUser(id, relDAO.findBySQL(sqlFriends,id));
    }

    // cписок кому я отправил запрос
    public List<User> outcomeRequests(String id) {
        return listUser(id, relDAO.findBySQL(sqlOutcomeRequests,id));
    }

    // список кто мне отправил запрос
    public List<User> incomeRequests(String id) {
        return listUser(id, relDAO.findBySQL(sqlIncomeRequests, id));
    }

    // достаем из базы Useroв
    private List<User> listUser(String id, List<Relationship> tmp){
        List<User> list = new ArrayList<>();
        for(Relationship el : tmp) {
            list.add(userDAO.findById(Long.parseLong(el.getUserToId())));
        }
        return list;
    }

    // добавление связи между User (из NOT FRIENDS - REQUEST, для FORMER_FRIENDS,REQUESR_DENIED  - REQUEST - на updateRelationship)
                            // по сути метод можно выкинуть и все добавить  в updateRelationship c помощью дополнительного Handler
    public void addRelationoship (String userIdFrom, String userIdTo) throws BadRequestException {
                                        //проверка количества оптравленных запросов (допустимо - 10)
        if(relDAO.findByIntRequest(sqlRequestCount, userIdFrom) >= 10)
            throw new BadRequestException("more than 10 applications");
                                        //проверка на наличие связи userIdFrom - userIdTo или userIdTo - userIdFrom
        if (relDAO.findBySQL(sqlChainUser, userIdFrom,userIdTo) != null)
            updateRelationship(userIdFrom, userIdTo, Status.REQUEST);
        else
            relDAO.save(new Relationship(userIdFrom, userIdTo, Status.REQUEST,new Date()));
    }

    // обновление статусов, кроме NOT FRIENDS - REQUEST
                // в Handler    -  обновляем Relationship в базе (заменив статус + дату изменения)
    public void updateRelationship (String userIdFrom, String userIdTo, Status status) throws BadRequestException {
        Relationship relationship = relDAO.findBySQL(sqlChainUser, userIdFrom,userIdTo);
        subsequence.sequence(relationship, status);
    }
}
