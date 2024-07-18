package com.emmariescurrena.my_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emmariescurrena.my_blog.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
