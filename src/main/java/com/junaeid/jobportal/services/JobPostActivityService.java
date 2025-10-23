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
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        for (IRecruiterJobs iRecruiterJobs : recruiterJobsDto) {
            JobLocation loc = new JobLocation(
                    iRecruiterJobs.getLocation_id(),
                    iRecruiterJobs.getCity(),
                    iRecruiterJobs.getState(),
                    iRecruiterJobs.getCountry()
            );
            JobCompany company = new JobCompany(iRecruiterJobs.getCompany_id(), iRecruiterJobs.getCompanyName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(
                    iRecruiterJobs.getTotalCandidates(),
                    iRecruiterJobs.getJob_post_id(),
                    iRecruiterJobs.getJob_title(),
                    loc,
                    company
            ));
        }
        return recruiterJobsDtoList;
    }
}
