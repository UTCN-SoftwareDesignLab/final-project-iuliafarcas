package com.lab4.demo.court.repository;

import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findBookingsByCourtAndStartDateAndEndDate(Court court, LocalDateTime start, LocalDateTime end);
    List<Booking> findBookingsByStartDateLike(LocalDateTime start);
    List<Booking> findBookingsByStartDateIsAfterAndStartDateIsBefore(LocalDateTime start, LocalDateTime end);

}
