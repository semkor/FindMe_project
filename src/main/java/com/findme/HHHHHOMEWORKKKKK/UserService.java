package com.findme.HHHHHOMEWORKKKKK;

import com.findme.B_models.User;
import com.findme.HHHHHOMEWORKKKKK.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {
    private UserDAO userDao;

    @Autowired
    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }
    //--------------------------------------------------------------------------------------------------------
    @Value("${sqlExecute.sqlPhoneEmail}")
    private String sqlPhoneEmail;

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

    //---------------------------------------------------------------------------------------------------------

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
}
