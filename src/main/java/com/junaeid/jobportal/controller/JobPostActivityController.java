package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.JobPostActivity;
import com.junaeid.jobportal.entity.RecruiterJobsDto;
import com.junaeid.jobportal.entity.RecruiterProfile;
import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.services.JobPostActivityService;
import com.junaeid.jobportal.services.UsersService;
import com.junaeid.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(
            UsersService usersService,
            JobPostActivityService jobPostActivityService
    ) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard")
    public String searchJobs(Model model) {
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Users user = userDetails.getUser();
        //System.out.println("UserType: " + user.getUserTypeId().getUserTypeName());
        //System.out.println("Profile class: " + user.getClass().getName());
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
            if (authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_Recruiter"))
                    && currentUserProfile instanceof RecruiterProfile recruiterProfile) {
                List<RecruiterJobsDto> recruiterJobs = jobPostActivityService
                        .getRecruiterJobs(recruiterProfile
                                .getUserAccountId());
                model.addAttribute("jobPost", recruiterJobs);
            }
        }
        model.addAttribute("user", currentUserProfile);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard";
    }
}
