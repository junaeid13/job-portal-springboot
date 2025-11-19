package com.junaeid.jobportal.repository;

import com.junaeid.jobportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
    Optional<JobSeekerProfile> findByUserAccountId(int userAccountId);
}
