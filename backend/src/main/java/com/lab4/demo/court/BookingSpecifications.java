package com.lab4.demo.court;

import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.dto.BookingFilterDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Optional.ofNullable;

public class BookingSpecifications {

    public static Specification<Booking> allBookingsSelectedDate(LocalDateTime date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return (root, query, cb) -> cb.between(root.get("startDate"),date, date.plusDays(1));

        /*return (root, query, cb) ->{
            final Root<Booking> booking = query.from(Booking.class);
            return cb.b

        }*/
    }

    public static Specification<Booking> specificationsFromFilter(BookingFilterDTO filter){
        final Specification<Booking> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getStartDate()).ifPresent(localDateTime -> spec.and(allBookingsSelectedDate(localDateTime)));

        return spec;
    }
}
