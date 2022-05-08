package com.findme.D_service;

import com.findme.B_models.User;
import com.findme.E_dao.PostDAO;
import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerError;
import com.findme.B_models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    private PostDAO postDAO;
    private RelationshipDAO relDAO;
    private UserDAO userDAO;

    @Autowired
    public PostService(PostDAO postDAO, RelationshipDAO relDAO, UserDAO userDAO) {
        this.postDAO = postDAO;
        this.relDAO = relDAO;
        this.userDAO = userDAO;
    }

    //---------------------------------------------------------------------------------------------------------
    @Value("${sqlExecute.sqlChainUserFriends}")
    private String sqlChainUserFriends;

    //---------------------------------------------------------------------------------------------------------
    // проверка наявности ссылки в тексте
    public boolean validMessagePosted(String message) throws BadRequestException{
        if(message.contains("http:") || message.contains("https:") || message.contains("www.") )
            throw new BadRequestException ("Message cannot contain a link");
        return true;
    }

    //проверка на своей странице или нет, друзья или нет
    public boolean validLocationPosted(String userFromId, String userToId) throws BadRequestException{
        if(!userFromId.equals(userToId) && relDAO.findBySQL(sqlChainUserFriends, userFromId,userToId) == null)
            throw new BadRequestException ("You can post only to yourself and friends");
        return true;
    }

    //добавление всех данных и сохранение
    public Post createPost(String message, String location, String idUserPosted, String userPagePosted) throws BadRequestException, InternalServerError {
            Post post = new Post();
                post.setMessage(message);
                post.setLocation(location);
                post.setDatePosted(new Date());
                post.setUserPosted(userDAO.findById(Long.parseLong(idUserPosted)));
                post.setUserPagePosted(userDAO.findById(Long.parseLong(userPagePosted)));
        return postDAO.save(post);
    }

    //cохранение like
    public void saveLike(String id, String postId){
       Post post = postDAO.findById(Long.parseLong(postId));
            List<User> list=post.getUserTagged();
                list.add(userDAO.findById(Long.parseLong(id)));
            post.setUserTagged(list);
       postDAO.update(post);
    }
}
