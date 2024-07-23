package com.emmariescurrena.all_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.all_blog.dtos.CreatePostDto;
import com.emmariescurrena.all_blog.dtos.UpdatePostDto;
import com.emmariescurrena.all_blog.models.Post;
import com.emmariescurrena.all_blog.repositories.PostRepository;
import com.emmariescurrena.all_blog.repositories.UserRepository;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public Post save(CreatePostDto createPostDto) {
        Post post = new Post();

        post.setBody(createPostDto.getBody());
        post.setTitle(createPostDto.getTitle());
        post.setUser(userRepository.findById(createPostDto.getUserId()).get());

        return postRepository.save(post);
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> allPosts() {
        List<Post> posts = new ArrayList<>();

        postRepository.findAll().forEach(posts::add);

        return posts;
    }

    public Post updatePost(Long id, UpdatePostDto updatePostDto) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return null;
        }

        Post post = optionalPost.get();
        post.setTitle(updatePostDto.getTitle());
        post.setBody(updatePostDto.getBody());

        return postRepository.save(post);
    }

    public Post deletePost(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return null;
        }

        Post post = optionalPost.get();

        if (post.getUser().getId() != userId) {
            return null;
        }

        postRepository.delete(post);

        return post;
    }

}
