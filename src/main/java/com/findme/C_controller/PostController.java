package com.findme.C_controller;

import com.findme.B_models.Post;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerError;
import com.findme.D_service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.TreeSet;


@Controller
public class PostController {
    private PostService postService;
    private UserController userController;

    @Autowired
    public PostController(PostService postService, UserController userController) {
        this.postService = postService;
        this.userController = userController;
    }

    //-------------------------------------------- lesson 7.1 ----------------------------------------------------------
    // добавление Post
    @PostMapping (value="/createPost")
    public ResponseEntity<String> addPost(@RequestParam("message") String message, @RequestParam("location") String location,
                                          @RequestParam("userPagePosted") String userPagePosted){
        try {
            if (message == null && location == null && userPagePosted == null)
                throw new BadRequestException("Post is Empty");
            postService.validMessagePosted(message);
            postService.validLocationPosted(getSessionId(), userPagePosted);

            postService.createPost(message, location, getSessionId(), userPagePosted);
            return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (InternalServerError e){
            return new ResponseEntity<>("Server issues", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // лайки под постом
    @GetMapping (value="/lovePost")
    public ResponseEntity<String> lovePost(@RequestParam("post") String postId){
            postService.saveLike(getSessionId(), postId);
        return new ResponseEntity<String>("Like successfully",HttpStatus.OK);
    }


    //-------------------------------------------- lesson 7.2 ----------------------------------------------------------
    // все мои посты
    @GetMapping(value = "/allMyPost")
    public ResponseEntity<TreeSet<Post>> allMyPost(){
        return new ResponseEntity<TreeSet<Post>>(postService.allMyPost(getSessionId()), HttpStatus.OK);
    }

    // все посты моих друзей
    @GetMapping(value = "/allFriendsPost")
    public ResponseEntity<TreeSet<Post>> allFriendsPost(){
        return new ResponseEntity<TreeSet<Post>>(postService.allFriendsPost(getSessionId()), HttpStatus.OK);
    }

    // все посты по Id User
    @GetMapping(value = "/allIdUserPost")
    public ResponseEntity<TreeSet<Post>> allIdUserPost(@RequestParam String idUserPost){
        getSessionId();
        return new ResponseEntity<TreeSet<Post>>(postService.allIdUserPost(idUserPost), HttpStatus.OK);
    }

    //=============================================== vault ============================================================
    private String getSessionId(){
        return userController.getSessionId();
    }
}
