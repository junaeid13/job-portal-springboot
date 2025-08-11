package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.RecruiterProfile;
import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.repository.RecruiterProfileRepository;
import com.junaeid.jobportal.repository.UsersRepository;
import com.junaeid.jobportal.services.RecruiterProfileService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(
            UsersRepository usersRepository,
            RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new
                    UsernameNotFoundException("Username not found"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService
                    .getOne(users.getUserId());
            if (recruiterProfile.isPresent()) {
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_Profile";
    }
}
