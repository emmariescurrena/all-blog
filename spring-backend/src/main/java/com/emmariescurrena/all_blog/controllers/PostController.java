package com.emmariescurrena.all_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.emmariescurrena.all_blog.dtos.PostDto;
import com.emmariescurrena.all_blog.exceptions.NotFoundException;
import com.emmariescurrena.all_blog.models.Post;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.services.PostService;

import jakarta.validation.Valid;

import static com.emmariescurrena.all_blog.util.ControllerHelperFunctions.getCurrentUser;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/posts")
@PreAuthorize("isAuthenticated()")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto) {
        User currentUser = getCurrentUser();

        Post createdPost = postService.save(postDto, currentUser);

        return ResponseEntity
            .created(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/posts/{id}")
                    .buildAndExpand(createdPost.getId())
                    .toUri())
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.of(postService.getPostById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        Optional<Post> optionalPost = postService.getPostById(id);
        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post for update not found");
        }

        Post postToUpdate = optionalPost.get();

        User currentUser = getCurrentUser();

        if (!postToUpdate.getUser().getId().equals(currentUser.getId())
        && !currentUser.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")))) {
            throw new AccessDeniedException("You don't have the permission to update this post");
        }

        postService.updatePost(postToUpdate, postDto, currentUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.getPostById(id);
        if (optionalPost.isEmpty()) {
            throw new NotFoundException("Post for delete not found");
        }

        Post postToDelete = optionalPost.get();

        User currentUser = getCurrentUser();

        if (!postToDelete.getUser().getId().equals(currentUser.getId())
        && !currentUser.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN")))) {
            throw new AccessDeniedException("You don't have the permission to delete this post");
        }

        postService.deletePost(postToDelete);

        return ResponseEntity.ok().build();
    }

}
