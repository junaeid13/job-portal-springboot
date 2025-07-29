package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.entity.UsersType;
import com.junaeid.jobportal.repository.UsersTypeRepository;
import com.junaeid.jobportal.services.UsersService;
import com.junaeid.jobportal.services.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService userService) {
        this.usersTypeService = usersTypeService;
        this.usersService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        //List<UsersType> usersTypes = usersTypeService.getAll();
        List<UsersType> usersTypes = Collections.singletonList(new UsersType(1l, "Recruter"));
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
        System.out.println("User::" + users);
        Optional<Users> optionalUsers = usersService.getByEmail(users.getEmail());

        if (optionalUsers.isPresent()) {
            model.addAttribute("error", "Email already exists");
            List<UsersType> usersTypes = Collections.singletonList(new UsersType(1l, "Recruter"));
            model.addAttribute("getAllTypes", usersTypes);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(users);
        return "dashboard";

    }
}
