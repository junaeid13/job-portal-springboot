package com.junaeid.jobportal.services;

import com.junaeid.jobportal.entity.JobSeekerProfile;
import com.junaeid.jobportal.entity.RecruiterProfile;
import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.repository.JobSeekerProfileRepository;
import com.junaeid.jobportal.repository.RecruiterProfileRepository;
import com.junaeid.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(
            UsersRepository usersRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);

        int userTypeID = users.getUserTypeId().getUserTypeId();

        if (userTypeID == 1)
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        else
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        return savedUser;
    }

    public Optional<Users> getByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new
                    UsernameNotFoundException("Username not found"));
            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                return recruiterProfileRepository.findById(userId).orElse(new
                        RecruiterProfile());
            } else {
                return jobSeekerProfileRepository.findById(userId).orElse(new
                        JobSeekerProfile());
            }
        }
        return null;
    }
}
