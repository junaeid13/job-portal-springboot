package com.junaeid.jobportal.repository;

import com.junaeid.jobportal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository< Users, Integer> {
}
