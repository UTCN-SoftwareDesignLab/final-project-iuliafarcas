package com.lab4.demo.court.repository;

import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.ECourt;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourtRepository extends JpaRepository<Court, Long> {
    Optional<Court> findByCourt(ECourt court);
}
