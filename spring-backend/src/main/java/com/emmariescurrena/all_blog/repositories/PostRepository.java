package com.emmariescurrena.all_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.all_blog.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
