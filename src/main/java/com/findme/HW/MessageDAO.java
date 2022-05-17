package com.findme.HW;

import com.findme.B_models.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


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

    //----------------------------------------------- lesson12 ---------------------------------------------------------
    public List<Message> findBySQL(String sql, String firstArg, String secondArg, int limit) {
        return entityManager.createNativeQuery(sql, Message.class)
                .setParameter(1, firstArg)
                .setParameter(2, secondArg)
                .setParameter(3, firstArg)
                .setParameter(4, secondArg)
                .setParameter(5,limit)
                .getResultList();
    }


    public void updateSQL(String sql, String firstArg, String secondArg) {
       entityManager.createNativeQuery(sql)
                .setParameter(1, firstArg)
                .setParameter(2, secondArg)
                .setParameter(3, firstArg)
                .setParameter(4, secondArg);
    }
}
