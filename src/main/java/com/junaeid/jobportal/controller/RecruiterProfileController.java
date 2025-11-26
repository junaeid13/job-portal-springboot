package com.junaeid.jobportal.controller;

import com.junaeid.jobportal.entity.RecruiterProfile;
import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.repository.RecruiterProfileRepository;
import com.junaeid.jobportal.repository.UsersRepository;
import com.junaeid.jobportal.services.RecruiterProfileService;
import com.junaeid.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Objects;
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
            Users users = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            Optional<RecruiterProfile> recruiterProfileOpt =
                    recruiterProfileService.getOne(users.getUserId());

            // Always add a profile object
            RecruiterProfile profile = recruiterProfileOpt.orElse(new RecruiterProfile());
            model.addAttribute("profile", profile);
        }

        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNew(
            RecruiterProfile recruiterProfile,
            @RequestParam("image") MultipartFile multipartFile,
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            recruiterProfile.setUserAccountId(users.getUserId());
            recruiterProfile.setUser(users);
        }

        model.addAttribute("recruiterProfile", recruiterProfile);


        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils
                    .cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);

            RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

            String uploadDir = "/photos/recruiter/" + savedUser.getUserAccountId();
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            recruiterProfileService.addNew(recruiterProfile);
        }

        return "redirect:/dashboard";
    }
}
