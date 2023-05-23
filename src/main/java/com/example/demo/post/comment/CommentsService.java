package com.example.demo.post.comment;

import com.example.demo.demo.DemoService;
import com.example.demo.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostService postService;
    private final DemoService demoService;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository, PostService postService, DemoService demoService) {
        this.commentsRepository = commentsRepository;
        this.postService = postService;
        this.demoService = demoService;
    }

    public boolean commentExits(Long commentId) {
        return commentsRepository.findById(commentId).isPresent();
    }

    public Long getCommentUserId(String authHeader) {
        return demoService.getUserId(authHeader).getId();
    }

    public boolean isCommentOwner(Long userId, Long commentId) {
        Long commentUserId = commentsRepository.getById(commentId).getUserId();
        return Objects.equals(userId, commentUserId);
    }

    public List<Comments> getAllComments(Long postId) {
        return commentsRepository.findAllByPostId(postId);
    }

    public List<Comments> getAllCommentsByUserId(String authHeader) {
        Long userId = getCommentUserId(authHeader);
        return commentsRepository.findByUserId(userId);
    }

    public Comments createComment(Comments comment, Long postId, String authHeader) {
        if (!postService.postExits(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post does not exits");
        }
        comment.setUserId(getCommentUserId(authHeader));
        comment.setPostId(postId);
        System.out.println(comment);
        return commentsRepository.save(comment);
    }

    public void deleteComment(Long commentId, String authHeader) {
        if (!commentExits(commentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment not found");
        }
        if (!isCommentOwner(getCommentUserId(authHeader), commentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access denied");
        }

        commentsRepository.deleteById(commentId);
    }

}
