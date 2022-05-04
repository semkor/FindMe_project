package com.findme.E_dao;

import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class RelationshipDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //---------------------------------------------------------------------------------------------------------
    public Relationship findById(long id) {
        return entityManager.find(Relationship.class, new Long(id));
    }

    public Relationship save(Relationship relationship) {
        entityManager.persist(relationship);
        entityManager.flush();
        return relationship;
    }

    public Relationship update(Relationship relationship) {
        entityManager.merge(relationship);
        return relationship;
    }

    public void delete(long id) {
        entityManager.remove(findById(id));
    }

    //---------------------------------------------------------------------------------------------------------
    public List<Relationship> findBySQL(String sql, String id) {
        return entityManager.createNativeQuery(sql,Relationship.class)
                .setParameter(1, id)
                .getResultList();
    }

    public List<Relationship> findBySQL(String sql, String first, String second) {
        return entityManager.createNativeQuery(sql,Relationship.class)
                .setParameter(1, first)
                .setParameter(2, second)
                .setParameter(3, second)
                .setParameter(4, first)
                .getResultList();
    }
}
