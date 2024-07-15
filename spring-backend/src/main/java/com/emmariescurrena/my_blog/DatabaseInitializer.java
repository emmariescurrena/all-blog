package com.emmariescurrena.my_blog;

import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {

        Stream.of("emmanuel", "roberto", "john", "ricardo").forEach(name -> {
            User user = new User(name, passwordEncoder.encode(name + "pass"));
            userDAO.save(user);
        });
        userDAO.findAll().forEach(System.out::println);

        Stream.of("1", "2", "3", "4").forEach(n -> {
            Post post = new Post("title" + n, "Title" + n, "body" + n, userDAO.findById(Long.valueOf(n)).get());
            postDAO.save(post);
        });
        postDAO.findAll().forEach(System.out::println);

        System.out.println("Database initialized!");

    }
}