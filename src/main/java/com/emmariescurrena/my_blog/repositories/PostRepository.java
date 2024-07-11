package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.my_blog.models.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByUrl(String url);
}
