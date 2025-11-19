package com.junaeid.jobportal.entity;

public interface IRecruiterJobs {
    Long getTotalCandidates();

    Integer getJob_post_id();

    String getJob_title();

    Integer getLocation_id();

    String getCity();

    String getState();

    String getCountry();

    Integer getCompany_id();

    String getCompanyName();
}
