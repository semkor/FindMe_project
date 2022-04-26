package com.findme.E_dao;

import com.findme.B_models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
