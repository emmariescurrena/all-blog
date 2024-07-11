package com.emmariescurrena.my_blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.emmariescurrena.my_blog.models.Post;
import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.PostRepository;
import com.emmariescurrena.my_blog.repositories.UserRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userDAO;

    @Autowired
    PostRepository postDAO;

    @Override
    public void run(String... args) {

        userDAO.save(new User("emmanuel", "$2a$10$01YESQmWVy4Xv2CCUx08sO.M1Ge5RRlOqp7zFUC9xxzTZaxJvVPq2"));

        postDAO.save(
                new Post("this-is-the-title",
                        "This is the title",
                        "# Heading\n ## Sub-heading\nBody",
                        userDAO.findById(1L).get()));

        System.out.println("Database initialized!");

    }
}