package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.my_blog.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
