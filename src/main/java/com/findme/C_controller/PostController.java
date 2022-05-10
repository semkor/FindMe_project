package com.findme.C_controller;

import com.findme.B_models.Post;

import com.findme.D_service.PostService;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerException;
import com.findme.F_exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.TreeSet;


@Controller
public class PostController {
    private PostService postService;
    private UserController userController;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService, UserController userController) {
        this.postService = postService;
        this.userController = userController;
    }

    //-------------------------------------------- lesson 7.1 ----------------------------------------------------------
    // добавление Post
    @PostMapping (value="/createPost")
    public ResponseEntity<String> addPost(@RequestParam("message") String message, @RequestParam("location") String location,
                                          @RequestParam("userPagePosted") String userPagePosted, HttpSession session)
                                          throws UnauthorizedException {
    log.info("Post add: " + " message = " + message + " location = "+ location + "userPagePosted = "+  userPagePosted);
        try {
            if (message == null && location == null && userPagePosted == null)
                throw new BadRequestException("Post is Empty");
            postService.validMessagePosted(message);
            postService.validLocationPosted(getSessionId(session), userPagePosted);

            postService.createPost(message, location, getSessionId(session), userPagePosted);
            return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
        }catch (BadRequestException e){
            log.error("Error /createPost_BadRequestException ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (InternalServerException e){
            log.error("Error /createPost_InternalServerError ", e);
            return new ResponseEntity<>("Server issues", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // лайки под постом
    @GetMapping (value="/lovePost")
    public ResponseEntity<String> lovePost(HttpSession session, @RequestParam("post") String postId) throws UnauthorizedException {
    log.info("Like add. Argument: " + " location = "+ postId);
            postService.saveLike(getSessionId(session), postId);
        return new ResponseEntity<String>("Like successfully",HttpStatus.OK);
    }


    //-------------------------------------------- lesson 7.2 ----------------------------------------------------------
    // все мои посты
    @GetMapping(value = "/allMyPost")
    public ResponseEntity<TreeSet<Post>> allMyPost(HttpSession session)  throws UnauthorizedException {
        return new ResponseEntity<TreeSet<Post>>(postService.allMyPost(getSessionId(session)), HttpStatus.OK);
    }

    // все посты моих друзей
    @GetMapping(value = "/allFriendsPost")
    public ResponseEntity<TreeSet<Post>> allFriendsPost(HttpSession session)  throws UnauthorizedException {
        return new ResponseEntity<TreeSet<Post>>(postService.allFriendsPost(getSessionId(session)), HttpStatus.OK);
    }

    // все посты по Id User
    @GetMapping(value = "/allIdUserPost")
    public ResponseEntity<TreeSet<Post>> allIdUserPost(HttpSession session, @RequestParam String idUserPost)  throws UnauthorizedException {
        getSessionId(session);
        return new ResponseEntity<TreeSet<Post>>(postService.allIdUserPost(idUserPost), HttpStatus.OK);
    }

    //-------------------------------------------- lesson 8 ------------------------------------------------------------
    // посты всех друзей (от самых новых до самых старых) - первые 10
    @GetMapping(value = "/feed")
    public String feed(HttpSession session, Model model) throws UnauthorizedException {
        model.addAttribute("postList",postService.feedLimit(getSessionId(session)));
        return "3.0_feed";
    }

    // посты всех друзей (от самых новых до самых старых) - первые 5 + 10 новых
    @GetMapping(value = "/moreFeed")
    public String moreFeed(HttpSession session, Model model) throws UnauthorizedException {
        model.addAttribute("postList",postService.feedLimit(getSessionId(session)));
        return "3.0_feed";
    }

    //=============================================== vault ============================================================
    private String getSessionId(HttpSession session)  throws UnauthorizedException {
        return userController.getSessionId(session);
    }
}
