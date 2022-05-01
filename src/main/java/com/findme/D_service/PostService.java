package com.findme.D_service;

import com.findme.B_models.Post;
import com.findme.E_dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostDAO postDao;

    @Autowired
    public PostService(PostDAO postDao) {
        this.postDao = postDao;
    }

    //---------------------------------------------------------------------------------------------------------
    public Post findById(long id){
        return postDao.findById(id);
    }

    public Post save(Post post){
        return postDao.save(post);
    }

    public Post update(Post post){
        return postDao.update(post);
    }

    public void delete(long id){
        postDao.delete(id);
    }
    //---------------------------------------------------------------------------------------------------------

}
