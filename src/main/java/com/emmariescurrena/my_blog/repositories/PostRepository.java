package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.my_blog.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUrl(String url);
}
