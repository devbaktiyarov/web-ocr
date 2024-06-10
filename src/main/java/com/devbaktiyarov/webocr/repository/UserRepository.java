package com.devbaktiyarov.webocr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devbaktiyarov.webocr.entity.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long>{
    Optional<UserProfile> findByEmail(String email);
    // boolean existByEmail(String email);
}
