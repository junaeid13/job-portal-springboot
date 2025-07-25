package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.entity.UsersType;
import com.junaeid.jobportal.repository.UsersTypeRepository;
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

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService) {
        this.usersTypeService = usersTypeService;
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
    public String userRegistration(@Valid Users users) {
        System.out.println("User::" + users);
        return "dashboard";

    }
}
