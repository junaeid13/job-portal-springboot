package com.junaeid.jobportal.repository;

import com.junaeid.jobportal.entity.IRecruiterJobs;
import com.junaeid.jobportal.entity.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {
    @Query(value = """
    SELECT 
        COUNT(s.user_account_id) AS totalCandidates,
        j.job_post_id AS jobPostId,
        j.job_title AS jobTitle,
        l.id AS locationId,
        l.city AS city,
        l.state AS state,
        l.country AS country,
        c.id AS companyId,
        c.name AS name
    FROM job_post_activity j
    INNER JOIN job_location l ON j.job_location_id = l.id
    INNER JOIN job_company c ON j.job_company_id = c.id
    LEFT JOIN job_seeker_profile s ON s.applied_job_id = j.job_post_id
    WHERE j.posted_by_id = :recruiter
    GROUP BY 
        j.job_post_id,
        j.job_title,
        l.id,
        l.city,
        l.state,
        l.country,
        c.id,
        c.name
""", nativeQuery = true)
    List<IRecruiterJobs> getRecruiterJobs(@Param("recruiter") int recruiter);
}
