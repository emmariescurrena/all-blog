package com.emmariescurrena.my_blog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emmariescurrena.my_blog.models.Post;
import com.emmariescurrena.my_blog.repositories.PostRepository;
import com.emmariescurrena.my_blog.util.NotFoundException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    PostRepository postDAO;

    @GetMapping
    public List<Post> findAllPosts() {
        return postDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> findPost = postDAO.findById(id);

        if (findPost.isPresent()) {
            return ResponseEntity.ok(findPost.get());
        } else {
            throw new NotFoundException("Not found Post by id: " + id);
        }
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postDAO.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestBody Post postUpdateData) {

        Optional<Post> findPost = postDAO.findById(id);

        if (findPost.isPresent()) {
            Post postToUpdate = findPost.get();
            postToUpdate.copyDataFromPost(postUpdateData);
            Post postSaved = postDAO.save(postToUpdate);
            return ResponseEntity.ok(postSaved);
        } else {
            throw new NotFoundException("Not found Post by id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        Optional<Post> findPost = postDAO.findById(id);

        if (findPost.isPresent()) {
            postDAO.delete(findPost.get());
            return ResponseEntity.ok(true);
        } else {
            throw new NotFoundException("Not found Post by id: " + id);

        }
    }

}
