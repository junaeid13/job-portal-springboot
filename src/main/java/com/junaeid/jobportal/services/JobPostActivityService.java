package com.junaeid.jobportal.services;

import com.junaeid.jobportal.entity.JobPostActivity;
import com.junaeid.jobportal.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
