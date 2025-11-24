package com.junaeid.jobportal.services;

import com.junaeid.jobportal.entity.*;
import com.junaeid.jobportal.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
        List<IRecruiterJobs> recruiterJobsDto = jobPostActivityRepository.getRecruiterJobs(recruiter);
        System.out.println("From Service: recruiterJobsDto = " + recruiterJobsDto);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for (IRecruiterJobs iRecruiterJobs : recruiterJobsDto) {
            JobLocation loc = new JobLocation(
                    iRecruiterJobs.getLocationId(),
                    iRecruiterJobs.getCity(),
                    iRecruiterJobs.getState(),
                    iRecruiterJobs.getCountry()
            );
            JobCompany company = new JobCompany(iRecruiterJobs.getCompanyId(), iRecruiterJobs.getCompanyName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(
                    iRecruiterJobs.getTotalCandidates(),
                    iRecruiterJobs.getJobPostId(),
                    iRecruiterJobs.getJobTitle(),
                    loc,
                    company
            ));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Job not Found"));
    }
}
