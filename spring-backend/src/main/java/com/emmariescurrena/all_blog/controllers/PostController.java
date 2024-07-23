package com.emmariescurrena.all_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.emmariescurrena.all_blog.dtos.CreatePostDto;
import com.emmariescurrena.all_blog.dtos.UpdatePostDto;
import com.emmariescurrena.all_blog.models.Post;
import com.emmariescurrena.all_blog.services.PostService;
import static com.emmariescurrena.all_blog.util.ControllerHelperFunctions.getCurrentUser;


@RestController
@RequestMapping("/posts")
@PreAuthorize("isAuthenticated()")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody CreatePostDto postDto) {
        return ResponseEntity.ok(postService.save(postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.of(postService.getPost(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostDto updatePostDto) {
        Long currentUserId = getCurrentUser().getId();

        return ResponseEntity.ok(postService.updatePost(currentUserId, updatePostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable Long id) {
        Long currentUserId = getCurrentUser().getId();

        return ResponseEntity.ok(postService.deletePost(id, currentUserId));
    }

}
