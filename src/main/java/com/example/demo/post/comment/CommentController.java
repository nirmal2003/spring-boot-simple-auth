package com.example.demo.post.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/posts/comments", "/api/posts/comments/"})
public class CommentController {

    private final CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/{postId}", "/{postId}/"})
    public List<Comments> getAllComments(@PathVariable Long postId) {
        return commentsService.getAllComments(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/user", "/user/"})
    public List<Comments> getAllCommentsByUserId(@RequestHeader("Authorization") String authHeader) {
        return commentsService.getAllCommentsByUserId(authHeader);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = {"/{postId}", "/{postId}/"})
    public Comments createComment(@RequestBody Comments comment, @RequestHeader("Authorization") String authHeader, @PathVariable Long postId) {
        return commentsService.createComment(comment, postId, authHeader);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = {"/{commentId}", "/{commentId}/"})
    public void removeComment(@RequestHeader("Authorization") String authHeader, @PathVariable Long commentId) {
        commentsService.deleteComment(commentId, authHeader);
    }

}
