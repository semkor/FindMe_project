package com.findme.E_dao;

import com.findme.B_models.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
@Transactional
public class PostDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //---------------------------------------------------------------------------------------------------------
    public Post findById(long id) {
        return entityManager.find(Post.class, new Long(id));
    }

    public Post save(Post post) {
        entityManager.persist(post);
        entityManager.flush();
        return post;
    }

    public Post update(Post post) {
        entityManager.merge(post);
        return post;
    }

    public void delete(long id) {
        entityManager.remove(findById(id));
    }

    //---------------------------------------------------------------------------------------------------------
}
