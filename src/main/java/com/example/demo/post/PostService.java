package com.example.demo.post;

import com.example.demo.demo.DemoService;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final DemoService demoService;

    @Autowired
    public PostService(PostRepository postRepository, DemoService demoService) {
        this.postRepository = postRepository;
        this.demoService = demoService;
    }


    public boolean postExits(Long postId) {
        return postRepository.findById(postId).isPresent();
    }

    public boolean isPostOwner(Long userId, Long postId) {
        Long postUserId = postRepository.getById(postId).getUserId();
        return Objects.equals(postUserId, userId);
    }

    public Post createPost(Post post, String authHeader) {
        UserDTO postUser = demoService.getUserId(authHeader);
        post.setUserId(postUser.getId());
        return postRepository.save(post);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserPosts(String authHeader) {
        UserDTO postUser = demoService.getUserId(authHeader);
        return postRepository.findByUserId(postUser.getId());
    }

    public void deletePost(Long postId, String authHeader) {
        UserDTO postUser = demoService.getUserId(authHeader);
        if (!postExits(postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post does not exits");
        }

        if (!isPostOwner(postUser.getId(), postId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        postRepository.deleteById(postId);

    }

}
