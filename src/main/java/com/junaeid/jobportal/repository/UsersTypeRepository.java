package com.junaeid.jobportal.repository;

import com.junaeid.jobportal.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
    UsersType findByUserTypeName(String userTypeName);
}
