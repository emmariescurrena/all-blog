package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.emmariescurrena.my_blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByUrl(String url);
}
