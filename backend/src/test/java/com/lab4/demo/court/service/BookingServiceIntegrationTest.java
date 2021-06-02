package com.lab4.demo.court.service;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.court.repository.BookingRepository;
import com.lab4.demo.court.repository.CourtRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
        bookingRepository.deleteAll();
        courtRepository.deleteAll();
        roleRepository.deleteAll();
        userService.deleteAll();
    }

    @Test
    void findAll() {
        List<BookingDTO> bookings = bookingService.findAll();
        Assertions.assertEquals(bookings.size(), 0);
    }

    @Test
    void save() {
        Court court = (Court) TestCreationFactory.listOf(Court.class).get(0);
        court = courtRepository.save(court);

        roleRepository.save(Role.builder().name(ERole.PLAYER).build());

        UserListDTO player = (UserListDTO) TestCreationFactory.listOf(UserListDTO.class).get(0);
        player = userService.create(player);

        BookingDTO booking = (BookingDTO) TestCreationFactory.listOf(BookingDTO.class).get(0);
        booking.setPlayer_name(player.getName());
        booking.setPlayer_username(player.getUsername());

        booking = bookingService.save(booking);

        BookingDTO bookingDTO = bookingService.findById(booking.getId());

        Assertions.assertNotNull(bookingDTO);

        UserListDTO player2 = (UserListDTO) TestCreationFactory.listOf(UserListDTO.class).get(0);
        player2 = userService.create(player2);

        BookingDTO booking2 = (BookingDTO) TestCreationFactory.listOf(BookingDTO.class).get(0);
        booking2.setPlayer_name(player2.getName());
        booking2.setPlayer_username(player2.getUsername());

        try{
            bookingService.save(booking2);
        }catch (RuntimeException e){
            Assertions.assertEquals(e.getMessage(), "Not free!");
        }

    }

    @Test
    void update() {
        Court court = (Court) TestCreationFactory.listOf(Court.class).get(0);
        court = courtRepository.save(court);

        roleRepository.save(Role.builder().name(ERole.PLAYER).build());

        UserListDTO player = (UserListDTO) TestCreationFactory.listOf(UserListDTO.class).get(0);
        player = userService.create(player);

        BookingDTO booking = (BookingDTO) TestCreationFactory.listOf(BookingDTO.class).get(0);
        booking.setPlayer_name(player.getName());
        booking.setPlayer_username(player.getUsername());

        booking = bookingService.save(booking);
        booking.setDetails("DETAILS");

        BookingDTO bookingDTO = bookingService.update(booking.getId(), booking);
        Assertions.assertEquals(booking.getDetails(), bookingDTO.getDetails());

    }

    @Test
    void findAllPerDay() {

        Court court = (Court) TestCreationFactory.listOf(Court.class).get(0);
        court = courtRepository.save(court);

        roleRepository.save(Role.builder().name(ERole.PLAYER).build());

        UserListDTO player = (UserListDTO) TestCreationFactory.listOf(UserListDTO.class).get(0);
        player = userService.create(player);

        BookingDTO booking = (BookingDTO) TestCreationFactory.listOf(BookingDTO.class).get(0);
        booking.setPlayer_name(player.getName());
        booking.setPlayer_username(player.getUsername());

        booking = bookingService.save(booking);

        List<BookingDTO> bookings = bookingService.findAllPerDay("1111-11-11");
        //System.out.println(bookings);
        Assertions.assertEquals(bookings.size(), 1);
    }
}