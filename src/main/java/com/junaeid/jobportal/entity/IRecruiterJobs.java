package com.junaeid.jobportal.entity;

public interface IRecruiterJobs {
    Long getTotalCandidates();
    int getJob_post_id();
    String getJob_title();
    int getLocation_id();
    String getCity();
    String getState();
    String getCountry();
    int getCompany_id();
    String getCompanyName();
}
