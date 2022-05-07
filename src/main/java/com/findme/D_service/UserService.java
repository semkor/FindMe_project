package com.findme.D_service;

import com.findme.B_models.User;
import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    @Value("${sqlExecute.sqlLogin}")
    private String sqlLogin;
    @Value("${sqlExecute.sqlPhone}")
    private String sqlPhone;
    @Value("${sqlExecute.sqlEmail}")
    private String sqlEmail;

    @Value("${sqlExecute.sqlLoginPassword}")
    private String sqlLoginPassword;

    //---------------------------------------------------------------------------------------------------------
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
    public List<User> validationLogin(String login) {
        return userDao.findBySql(sqlLogin, login);
    }

    public List<User> validationPhone(String phone) {
        return userDao.findBySql(sqlPhone, phone);
    }

    public List<User> validationEmail(String email) {
        return userDao.findBySql(sqlEmail, email);
    }

    //---------------------------------------------- lesson4 hw --------------------------------------------------------
    public User validationLoginPassword(String login, String password) {
        return  userDao.findBySqlLogin(sqlLoginPassword,login,password);
    }

    //---------------------------------------------- lesson2 hw --------------------------------------------------------
    public User findById(long id) throws NotFoundException {
        User user = userDao.findById(id);
        if(user == null)
            throw new NotFoundException("Not profile");
        return user;
    }
}
