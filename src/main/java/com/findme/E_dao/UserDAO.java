package com.findme.E_dao;

import com.findme.B_models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //---------------------------------------------------------------------------------------------------------
    public User findById(Long id) {
        return entityManager.find(User.class, id);
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
        entityManager.remove(findById(new Long(id)));
    }

    //--------------------------------------------- lesson3 hw ---------------------------------------------------------
    public List<User> findBySql(String sql, String firstName) {
        return entityManager.createNativeQuery(sql,User.class)
                .setParameter(1,firstName)
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

