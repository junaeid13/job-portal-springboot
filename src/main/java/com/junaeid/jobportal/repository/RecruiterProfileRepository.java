package com.junaeid.jobportal.repository;

import com.junaeid.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer> {
    Optional<RecruiterProfile> findByUserAccountId(int userAccountId);
}
