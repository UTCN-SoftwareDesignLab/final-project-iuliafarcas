package com.lab4.demo.court.service;

import com.lab4.demo.court.BookingSpecifications;
import com.lab4.demo.court.mapper.BookingMapper;
import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.ECourt;
import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.court.model.dto.BookingFilterDTO;
import com.lab4.demo.court.repository.BookingRepository;
import com.lab4.demo.court.repository.CourtRepository;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    private final UserRepository userRepository;
    private final CourtRepository courtRepository;

    public List<BookingDTO> findAll(){
        return bookingRepository.findAll().stream()
                .map(bookingMapper::bookingToDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO findById(Long id){
        return bookingRepository.findById(id).map(bookingMapper::bookingToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Could not find booking: " + id));
    }

    public void deleteById(Long id){
        bookingRepository.deleteById(id);
    }

    public BookingDTO save(BookingDTO bookingDTO){
        User player = userRepository.findByUsername(bookingDTO.getPlayer_username())
                .orElseThrow(() -> new EntityNotFoundException("Could not find player with given username"));

        Court court = courtRepository.findByCourt(ECourt.valueOf(bookingDTO.getCourt()))
                .orElseThrow(() -> new EntityNotFoundException("Could not find court"));

        Booking booking = bookingMapper.bookingFromDTO(bookingDTO);
        booking.setCourt(court);
        booking.setPlayer(player);

        try{
            Booking check = bookingRepository.findBookingsByCourtAndStartDateAndEndDate(booking.getCourt(), booking.getStartDate(), booking.getEndDate())
                    .orElseThrow(() -> new EntityNotFoundException("Could not find booking"));
        }catch (EntityNotFoundException e) {
            return bookingMapper.bookingToDTO(bookingRepository.save(booking));
        }

        //throw  new RuntimeException("Not free!");
        return null;
    }

    public BookingDTO update(Long id, BookingDTO bookingDTO){
        User player = userRepository.findByUsername(bookingDTO.getPlayer_username())
                .orElseThrow(() -> new EntityNotFoundException("Could not find player with given username"));
        Court court = courtRepository.findByCourt(ECourt.valueOf(bookingDTO.getCourt()))
                .orElseThrow(() -> new EntityNotFoundException("Could not find court"));

        Booking booking = bookingMapper.bookingFromDTO(bookingDTO);
        booking.setCourt(court);
        booking.setPlayer(player);
        booking.setId(id);

        return bookingMapper.bookingToDTO(bookingRepository.save(booking));
    }

    public List<BookingDTO> findAllPerDay(String date){
        //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime d = LocalDateTime.parse(date + " 00:00", format);
        /*BookingFilterDTO filter = BookingFilterDTO.builder()
                .startDate(LocalDateTime.parse(date, format))
                .build();

        return bookingRepository.findAll(
                BookingSpecifications.specificationsFromFilter(filter)
        ).stream().map(bookingMapper::bookingToDTO).collect(Collectors.toList());*/
//        return bookingRepository.findBookingsByStartDateLike(LocalDateTime.parse(date + " 00:00", format))
//                .stream().map(bookingMapper::bookingToDTO)
//                .collect(Collectors.toList());
        return bookingRepository.findBookingsByStartDateIsAfterAndStartDateIsBefore(d, d.plusDays(1)).stream()
                .map(bookingMapper::bookingToDTO)
                .collect(Collectors.toList());
    }

}
