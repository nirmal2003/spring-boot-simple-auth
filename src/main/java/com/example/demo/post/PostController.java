package com.example.demo.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/posts", "/api/posts/"})
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post newPost(@RequestBody Post post, @RequestHeader("Authorization") String authHeader) {
        return postService.createPost(post, authHeader);
    }

    @GetMapping
    public List<Post> allPosts() {
        return postService.getPosts();
    }

    @GetMapping(path = {"/user", "/user/"})
    public List<Post> userPosts(@RequestHeader("Authorization") String authHeader) {
        return postService.getUserPosts(authHeader);
    }

    @DeleteMapping(path = {"/{postId}", "/{postId}/"})
    public void removePost(@RequestHeader("Authorization") String authHeader, @PathVariable Long postId) {
        postService.deletePost(postId, authHeader);
    }

}
