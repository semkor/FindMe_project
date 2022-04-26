package com.findme.C_controller;

import com.findme.D_service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public PostController (PostService postService) {
        this.postService = postService;
    }

    //------------------------------------------------------------------------------------------------------------------

}
