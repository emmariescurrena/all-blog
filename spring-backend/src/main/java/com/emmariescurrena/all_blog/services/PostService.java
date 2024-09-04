package com.emmariescurrena.all_blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emmariescurrena.all_blog.dtos.PostDto;
import com.emmariescurrena.all_blog.models.Post;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.repositories.PostRepository;
import com.emmariescurrena.all_blog.repositories.UserRepository;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public Post save(PostDto postDto, User user) {
        Post post = new Post();

        post.setBody(postDto.getBody());
        post.setTitle(postDto.getTitle());
        post.setUser(userRepository.findById(user.getId()).get());

        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> allPosts() {
        List<Post> posts = new ArrayList<>();

        postRepository.findAll().forEach(posts::addFirst);

        return posts;
    }

    public void updatePost(Post postToUpdate, PostDto postDto, User user) {
        postToUpdate.setTitle(postDto.getTitle());
        postToUpdate.setBody(postDto.getBody());

        postRepository.save(postToUpdate);
    }

    public void deletePost(Post postToDelete) {
        postRepository.delete(postToDelete);
    }

}
