package com.findme.HHHHHOMEWORKKKKK;

import com.findme.B_models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //---------------------------------------------------------------------------------------------------------
    public User findById(long id) {
        return entityManager.find(User.class, new Long(id));
    }

    public User save(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    public User update(User user) {
        entityManager.merge(user);
        return user;
    }

    public void delete(long id) {
        entityManager.remove(findById(id));
    }

    //--------------------------------------------- lesson3 hw ---------------------------------------------------------
    public List<User> findBySql(String sql, String firstName, String secondName) {
        return entityManager.createNativeQuery(sql,User.class)
                .setParameter(1,firstName)
                .setParameter(2,secondName)
                .getResultList();
    }

    //--------------------------------------------- lesson4 hw ---------------------------------------------------------
    public User findBySqlLogin(String sql, String login, String password) {
        User user = null;
        try {
            user = (User) entityManager.createNativeQuery(sql, User.class)
                    .setParameter(1, login)
                    .setParameter(2, password)
                    .getSingleResult();
        } catch (NoResultException e) {}
        return user;
    }
}

