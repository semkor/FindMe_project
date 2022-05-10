package com.findme.D_service;

import com.findme.B_models.Relationship;
import com.findme.B_models.User;
import com.findme.E_dao.PostDAO;
import com.findme.E_dao.RelationshipDAO;
import com.findme.E_dao.UserDAO;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerException;
import com.findme.B_models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

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

    //------------------------------------------------------------------------------------------------------------------
    @Value("${sqlExecute.sqlChainUserFriends}")
    private String sqlChainUserFriends;
    @Value("${sqlExecute.sqlAllUserFriends}")
    private String sqlAllUserFriends;

    //-------------------------------------------- lesson 7.1 ----------------------------------------------------------
    // проверка наявности ссылки в тексте
    public boolean validMessagePosted(String message) throws BadRequestException {
        if (message.contains("http:") || message.contains("https:") || message.contains("www."))
            throw new BadRequestException("Message cannot contain a link");
        return true;
    }

    //проверка на своей странице или нет, друзья или нет
    public boolean validLocationPosted(String userFromId, String userToId) throws BadRequestException {
        if (!userFromId.equals(userToId) && relDAO.findBySQL(sqlChainUserFriends, userFromId, userToId) == null)
            throw new BadRequestException("You can post only to yourself and friends");
        return true;
    }

    //добавление всех данных и сохранение
    public Post createPost(String message, String location, String idUserPosted, String userPagePosted) throws BadRequestException, InternalServerException {
        Post post = new Post();
        post.setMessage(message);
        post.setLocation(location);
        post.setDatePosted(new Date());
        post.setUserPosted(userDAO.findById(Long.parseLong(idUserPosted)));
        post.setUserPagePosted(userDAO.findById(Long.parseLong(userPagePosted)));
        return postDAO.save(post);
    }

    //cохранение like
    public void saveLike(String id, String postId) {
        Post post = postDAO.findById(Long.parseLong(postId));
        List<User> list = post.getUserTagged();
        list.add(userDAO.findById(Long.parseLong(id)));
        post.setUserTagged(list);
        postDAO.update(post);
    }

    //-------------------------------------------- lesson 7.2 ----------------------------------------------------------
    // все посты + посты друзей
    public TreeSet<Post> allPost(String userPageId) {
        TreeSet<Post> posts = new TreeSet<>();
        posts.addAll(allMyPost(userPageId));
        posts.addAll(allFriendsPost(userPageId));
        return posts;
    }

    // все мои посты
    public TreeSet<Post> allMyPost(String userPageId) {
        TreeSet<Post> posts = new TreeSet<>();
        posts.addAll(userDAO.findById(Long.parseLong(userPageId)).getPostList());
        return posts;
    }

    // все посты моих друзей
    public TreeSet<Post> allFriendsPost(String userPageId) {
        TreeSet<Post> posts = new TreeSet<>();
        List<Relationship> list = relDAO.findBySQLFriends(sqlAllUserFriends, userPageId);
        if (list != null) {
            for (Relationship el : list) {
                if (el.getUserFromId().equals(userPageId))
                    posts.addAll(userDAO.findById(Long.parseLong(el.getUserToId())).getPostList());
                else
                    posts.addAll(userDAO.findById(Long.parseLong(el.getUserFromId())).getPostList());
            }
        }
        return posts;
    }

    // все посты по IdUser
    public TreeSet<Post> allIdUserPost(String idUserPost) {
        TreeSet<Post> posts = new TreeSet<>();
        posts.addAll(userDAO.findById(Long.parseLong(idUserPost)).getPostList());
        return posts;
    }

    //---------------------------------------------- lesson 8 ----------------------------------------------------------
    // все посты друзей (первые 10)
    private final int limit = 5;
    public int count = 0;

    public TreeSet<Post> feedLimit(String userPageId) {
        System.out.println(count);
        TreeSet<Post> posts = new TreeSet<>();
        List<Relationship> list = relDAO.findBySQLFriends(sqlAllUserFriends, userPageId);

        if (list == null)
            return null;

        for (Relationship el : list) {
            if (posts.size() == limit + count)
                break;

            List<Post> postList;
            if (el.getUserFromId().equals(userPageId))
                 postList = userDAO.findById(Long.parseLong(el.getUserToId())).getPostList();
            else
                 postList = userDAO.findById(Long.parseLong(el.getUserFromId())).getPostList();

            if (postList != null) {
                 for (Post els : postList) {
                    if (posts.size() == limit + count)
                        break;
                    posts.add(els);
                 }
            }
        }
        count = posts.size();
        return posts;
    }
}
