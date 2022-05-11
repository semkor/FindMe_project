package com.findme.C_controller;

import com.findme.B_models.Post;

import com.findme.D_service.PostService;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerException;
import com.findme.F_exception.LimitationException;
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
    @PostMapping (value="/createPost")      // добавление Post
    public ResponseEntity<String> addPost(@RequestParam("message") String message, @RequestParam("location") String location, @RequestParam("userPagePosted") String userPagePosted, HttpSession session)
                                          throws UnauthorizedException, LimitationException {
            log.info("Post add: " + " message = " + message + " location = "+ location + "userPagePosted = "+  userPagePosted);
            if (message == null && location == null && userPagePosted == null)
                throw new LimitationException("Post is Empty");
            postService.validMessagePosted(message);
            postService.validLocationPosted(getSessionId(session), userPagePosted);
            postService.createPost(message, location, getSessionId(session), userPagePosted);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    @GetMapping (value="/lovePost")         // лайки под постом
    public ResponseEntity<String> lovePost(HttpSession session, @RequestParam("post") String postId) throws UnauthorizedException {
    log.info("Like add. Argument: " + " location = "+ postId);
            postService.saveLike(getSessionId(session), postId);
        return new ResponseEntity<String>("Like successfully",HttpStatus.OK);
    }

    //-------------------------------------------- lesson 7.2 ----------------------------------------------------------
    @GetMapping(value = "/allMyPost")       // все мои посты
    public ResponseEntity<TreeSet<Post>> allMyPost(HttpSession session)  throws UnauthorizedException {
        return new ResponseEntity<TreeSet<Post>>(postService.allMyPost(getSessionId(session)), HttpStatus.OK);
    }

    @GetMapping(value = "/allFriendsPost")  // все посты моих друзей
    public ResponseEntity<TreeSet<Post>> allFriendsPost(HttpSession session)  throws UnauthorizedException {
        return new ResponseEntity<TreeSet<Post>>(postService.allFriendsPost(getSessionId(session)), HttpStatus.OK);
    }

    @GetMapping(value = "/allIdUserPost")   // все посты по Id User
    public ResponseEntity<TreeSet<Post>> allIdUserPost(HttpSession session, @RequestParam String idUserPost)  throws UnauthorizedException {
        getSessionId(session);
        return new ResponseEntity<TreeSet<Post>>(postService.allIdUserPost(idUserPost), HttpStatus.OK);
    }

    //-------------------------------------------- lesson 8 ------------------------------------------------------------
    @GetMapping(value = "/feed")            // посты всех друзей (от самых новых до самых старых) - первые 10
    public String feed(HttpSession session, Model model) throws UnauthorizedException {
        model.addAttribute("postList",postService.feedLimit(getSessionId(session)));
        return "3.0_feed";
    }

    @GetMapping(value = "/moreFeed")        // посты всех друзей (от самых новых до самых старых) - первые 5 + 10 новых
    public String moreFeed(HttpSession session, Model model) throws UnauthorizedException {
        model.addAttribute("postList",postService.feedLimit(getSessionId(session)));
        return "3.0_feed";
    }

    //=============================================== vault ============================================================
    private String getSessionId(HttpSession session)  throws UnauthorizedException {
        return userController.getSessionId(session);
    }
}
