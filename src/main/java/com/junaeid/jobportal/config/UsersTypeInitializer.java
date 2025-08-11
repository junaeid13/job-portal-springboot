package com.junaeid.jobportal.config;

import com.junaeid.jobportal.entity.UsersType;
import com.junaeid.jobportal.repository.UsersRepository;
import com.junaeid.jobportal.repository.UsersTypeRepository;
import org.hibernate.usertype.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersTypeInitializer {

    @Bean
    public CommandLineRunner init(UsersTypeRepository usersTypeRepository) {
        return args -> {
            long count = usersTypeRepository.count();

            if (count == 0) {
                UsersType recruiter = new UsersType("Recruiter");
                UsersType jobSeeker = new UsersType("JobSeeker");

                usersTypeRepository.save(recruiter);
                usersTypeRepository.save(jobSeeker);

            } else
                System.out.println("User type already initialized");
        };
    }
}
