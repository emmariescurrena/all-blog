package com.emmariescurrena.all_blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.all_blog.models.Role;
import com.emmariescurrena.all_blog.models.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleEnum name);
}
