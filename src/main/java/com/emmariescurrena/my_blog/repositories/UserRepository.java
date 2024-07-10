package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.emmariescurrena.my_blog.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
