package com.emmariescurrena.all_blog.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emmariescurrena.all_blog.dtos.PostDto;
import com.emmariescurrena.all_blog.dtos.RegisterUserDto;
import com.emmariescurrena.all_blog.models.Post;
import com.emmariescurrena.all_blog.models.Role;
import com.emmariescurrena.all_blog.models.RoleEnum;
import com.emmariescurrena.all_blog.models.User;
import com.emmariescurrena.all_blog.repositories.PostRepository;
import com.emmariescurrena.all_blog.repositories.RoleRepository;
import com.emmariescurrena.all_blog.repositories.UserRepository;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Value("${super-admin.name}")
    private String superAdminName;

    @Value("${super-admin.surname}")
    private String superAdminSurname;

    @Value("${super-admin.email}")
    private String superAdminEmail;

    @Value("${super-admin.password}")
    private String superAdminPassword;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
        this.createSuperAdministrator();
        this.createUsers();
        this.createPosts();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {
            RoleEnum.USER,
            RoleEnum.ADMIN,
            RoleEnum.SUPER_ADMIN
        };
        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                
                roleToCreate.setName(roleName);

                roleRepository.save(roleToCreate);
            });
        });
    }

    private void createSuperAdministrator() {

        RegisterUserDto userDto = new RegisterUserDto(
            superAdminEmail,
            superAdminPassword,
            superAdminPassword,
            superAdminName,
            superAdminSurname
        );

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);

    }

    private void createUsers() {

        RegisterUserDto[] usersDtos = new RegisterUserDto[] {
            new RegisterUserDto(
            "robertomembiel@seguro.com",
            "$2a$10$xpcWXBuYlXcVTSK5ezvXdOitgURaV5a/2YzMyfM0jThg7Sji0Yk8e",
            "$2a$10$xpcWXBuYlXcVTSK5ezvXdOitgURaV5a/2YzMyfM0jThg7Sji0Yk8e",
            "Roberto",
            "Membiel"
            ), new RegisterUserDto(
            "catalinaperez@bamboo.com",
            "$2a$10$79hS1p5o.j4wO/1cm3NICekV9ELms1396y9TRA5vCRUyXvNhi1A1y",
            "$2a$10$79hS1p5o.j4wO/1cm3NICekV9ELms1396y9TRA5vCRUyXvNhi1A1y",
            "Catalina",
            "Pérez"
            )
        };


        Arrays.stream(usersDtos).forEach((userDto) -> {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

            if (optionalRole.isEmpty()) {
                return;
            }

            User user = new User();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setRole(optionalRole.get());

            userRepository.save(user);
        });

    }

    private void createPosts() {
        PostDto[] postsDtos = new PostDto[] {
            new PostDto(
                "This is my first post",
                """
                This is a test body. It needs to be large to match the
                minimum length and that is causing me to write so much    
                """
            ),
            new PostDto(
                "This is my second post",
                """
                This is a test body. It needs to be large to match the
                minimum length and that is causing me to write so much    
                """
            )
        };

        Arrays.stream(postsDtos).forEach((postDto) -> {
            Post post = new Post();

            post.setBody(postDto.getBody());
            post.setTitle(postDto.getTitle());
            post.setUser(userRepository.findById(Long.valueOf(1)).get());

            postRepository.save(post);
        });

    }

}
