package com.findme.D_service;

import com.findme.AA_ENUM.Status;
import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private UserDAO userDao;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public UserService(UserDAO userDao, RelationshipDAO relationshipDAO) {
        this.userDao = userDao;
        this.relationshipDAO = relationshipDAO;
    }
    //--------------------------------------------------------------------------------------------------------
    @Value("${sqlExecute.sqlPhoneEmail}")
    private String sqlPhoneEmail;
    @Value("${sqlExecute.sqlLoginPassword}")
    private String sqlLoginPassword;
    @Value("${sqlExecute.sqlFriends}")
    private String sqlFriends;
    @Value("${sqlExecute.sqlIncomeRequests}")
    private String sqlIncomeRequests;
    @Value("${sqlExecute.sqlOutcomeRequests}")
    private String sqlOutcomeRequests;
    @Value("${sqlExecute.sqlHaveIsApplication}")
    private String sqlHaveIsApplication;





    //---------------------------------------------------------------------------------------------------------
    public User findById(long id){
        return userDao.findById(id);
    }

    public User save(User user){
        return userDao.save(user);
    }

    public User update(User user){
        return userDao.update(user);
    }

    public void delete(long id){
        userDao.delete(id);
    }

    //---------------------------------------------- lesson3 hw --------------------------------------------------------
    public String validation(String phone, String email) {
          String result = "ok";
          List<User> list = userDao.findBySql(sqlPhoneEmail, phone, email);
              if (!list.isEmpty()) {
                    if(list.size() == 2)
                        result = "This phone & email exists in the database";

                    if (list.get(0).getPhone().equals(phone) && list.get(0).getEmail().equals(email))
                        result = "This phone & email exists in the database";
                    else if (list.get(0).getPhone().equals(phone))
                        result = "This phone exists in the database";
                    else
                        result = "This email exists in the database";
              }
          return  result;
    }

    //---------------------------------------------- lesson4 hw --------------------------------------------------------
    public User validationLogin(String login, String password) {
        return  userDao.findBySqlLogin(sqlLoginPassword,login,password);
    }

    //---------------------------------------------- lesson5 hw --------------------------------------------------------
    // cписок моих друзей
    public List<Relationship> friends(String id) {
        return resultRelationship(id, relationshipDAO.findBySQL(sqlFriends,id));
    }

    // cписок кому я отправил запрос
    public List<Relationship> outcomeRequests(String id) {
        return resultRelationship(id, relationshipDAO.findBySQL(sqlOutcomeRequests,id));
    }

    // список кто мне отправил запрос
    public List<Relationship> incomeRequests(String id) {
        List<Relationship> list = new ArrayList<>();

            for(Relationship el : relationshipDAO.findBySQL(sqlIncomeRequests,id)) {
                String name = userDao.findById(Long.parseLong(el.getUserFromId())).getFirstName();
                list.add(new Relationship(name, id, el.getStatus()));
        }
        return list;
    }


    // отправка заявки на дружбу
    public void addRelationoship (String userIdFrom, String userIdTo) throws Exception {
        if(relationshipDAO.findBySQL(sqlHaveIsApplication,userIdFrom, userIdTo) != null)
            throw new Exception();
        relationshipDAO.save(new Relationship(userIdFrom, userIdTo,Status.REQUEST));
    }

    // обновления статуса дружбы
    public void updateRelationship (String userIdFrom, String userIdTo, Status status) {
//        Status status = Status.NOT_FRIENDS;
//        for(Status el: Status.values()){
//            if(nameStatus.equals(el))
//                status = el;
//        }
        System.out.println(userIdFrom +  "     " + userIdTo + "     "  + status );
        relationshipDAO.update(new Relationship(userIdFrom, userIdTo,status));
    }




    //=============================================== vault ============================================================
    //---------------------------------------------- lesson5 hw --------------------------------------------------------

    // создание нового Relationship c именем пользователя - который отображается
    private List<Relationship> resultRelationship(String id, List<Relationship> tmp){
        List<Relationship> list = new ArrayList<>();

        for(Relationship el : tmp) {
            String firstName = userDao.findById(Long.parseLong(el.getUserToId())).getFirstName();
            list.add(new Relationship(id, firstName, el.getStatus()));
        }
        return list;
    }






}
