package com.emmariescurrena.my_blog.bootstrap;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emmariescurrena.my_blog.dtos.RegisterUserDto;
import com.emmariescurrena.my_blog.models.Role;
import com.emmariescurrena.my_blog.models.RoleEnum;
import com.emmariescurrena.my_blog.models.User;
import com.emmariescurrena.my_blog.repositories.RoleRepository;
import com.emmariescurrena.my_blog.repositories.UserRepository;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

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

        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setName(superAdminName);
        userDto.setSurname(superAdminSurname);
        userDto.setEmail(superAdminEmail);
        userDto.setPassword(superAdminPassword);

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

}
