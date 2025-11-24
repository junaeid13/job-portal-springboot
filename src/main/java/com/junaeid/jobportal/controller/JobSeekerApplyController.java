package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.JobPostActivity;
import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.services.JobPostActivityService;
import com.junaeid.jobportal.services.UsersService;
import com.junaeid.jobportal.util.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;

    public JobSeekerApplyController(
            JobPostActivityService jobPostActivityService,
            UsersService usersService
    ) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;

    }

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Users currentUser = userDetails.getUser();

        JobPostActivity jobDetails = jobPostActivityService.getOne(id);


        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", currentUser);
        return "job-details";
    }
}
