package com.junaeid.jobportal.config;

import com.junaeid.jobportal.entity.Users;
import com.junaeid.jobportal.entity.UsersType;
import com.junaeid.jobportal.repository.UsersRepository;
import com.junaeid.jobportal.repository.UsersTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsersTypeInitializer {

    @Bean
    public CommandLineRunner init(UsersTypeRepository usersTypeRepository,
                                  UsersRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {

            // ---- Initialize UsersType ----
            if (usersTypeRepository.count() == 0) {
                usersTypeRepository.save(new UsersType("Recruiter"));
                usersTypeRepository.save(new UsersType("JobSeeker"));
            }

            // ---- Initialize Users ----
            if (userRepository.count() == 0) {
                UsersType recruiterType = usersTypeRepository.findByUserTypeName("Recruiter");
                UsersType jobSeekerType = usersTypeRepository.findByUserTypeName("JobSeeker");

                Users recruiter = new Users();
                recruiter.setEmail("recruiter@test.com");
                recruiter.setPassword(passwordEncoder.encode("123456"));
                recruiter.setUserTypeId(recruiterType);
                userRepository.save(recruiter);

                Users jobSeeker = new Users();
                jobSeeker.setEmail("jobseeker@test.com");
                jobSeeker.setPassword(passwordEncoder.encode("123456"));
                jobSeeker.setUserTypeId(jobSeekerType);
                userRepository.save(jobSeeker);
            }
        };
    }
}

