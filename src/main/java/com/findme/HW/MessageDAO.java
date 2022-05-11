package com.findme.HW;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
@Transactional
public class MessageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //---------------------------------------------------------------------------------------------------------
    public Message findById(long id) {
        return entityManager.find(Message.class, new Long(id));
    }

    public Message save(Message message) {
        entityManager.persist(message);
        entityManager.flush();
        return message;
    }

    public Message update(Message message) {
        entityManager.merge(message);
        return message;
    }
}
