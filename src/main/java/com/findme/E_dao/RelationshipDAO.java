package com.findme.E_dao;

import com.findme.B_models.Relationship;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    //------------------------------------------------ lesson 5 --------------------------------------------------------
    public List<Relationship> findBySQL(String sql, String id) {
        return entityManager.createNativeQuery(sql,Relationship.class)
                .setParameter(1, id)
                .getResultList();
    }

    public Relationship findBySQL(String sql, String firstParameter, String secondParameter) {
        Relationship relationship = null;
            try {
                relationship = (Relationship) entityManager.createNativeQuery(sql, Relationship.class)
                    .setParameter(1, firstParameter)
                    .setParameter(2, secondParameter)
                    .setParameter(3, secondParameter)
                    .setParameter(4, firstParameter)
                    .getSingleResult();
            } catch (NoResultException e) {}
        return relationship;
    }

    //------------------------------------------------ lesson 6 --------------------------------------------------------
    public int findByIntRequest(String sql, String argument) {
        int count = 0;
        try {
            count = Integer.parseInt(entityManager.createNativeQuery(sql).setParameter(1, argument).getSingleResult().toString());
        } catch (NoResultException | NullPointerException e) {}
        return count;
    }

    public int findByIntFriends(String sql, String argument) {
        int count = 0;
        try{
            count = Integer.parseInt(entityManager.createNativeQuery(sql)
                    .setParameter(1, argument)
                    .setParameter(2, argument)
                    .getSingleResult().toString());
        }catch(NoResultException | NullPointerException e){}
        return count;
    }

    //---------------------------------------------- lesson 7.1 --------------------------------------------------------
    public List<Relationship> findBySQLFriends(String sql, String firstArgument) {
        return entityManager.createNativeQuery(sql, Relationship.class)
                .setParameter(1, firstArgument)
                .setParameter(2, firstArgument)
                .getResultList();
    }




}
