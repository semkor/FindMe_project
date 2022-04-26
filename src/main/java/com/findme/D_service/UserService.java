package com.findme.D_service;

import com.findme.B_models.User;
import com.findme.E_dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private UserDAO userDao;

    @Autowired
    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

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





}
