package com.findme.HW;

import com.findme.C_controller.UserController;
import com.findme.HW.PostService;
import com.findme.F_exception.BadRequestException;
import com.findme.F_exception.InternalServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.valueOf;


@Controller
public class PostController {
    private PostService postService;
    private UserController userController;

    @Autowired
    public PostController(PostService postService, UserController userController) {
        this.postService = postService;
        this.userController = userController;
    }

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

    //=============================================== vault ============================================================
    private String getSessionId(){
        return userController.getSessionId();
    }
}
