package com.lab4.demo;

import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.ECourt;
import com.lab4.demo.court.repository.BookingRepository;
import com.lab4.demo.court.repository.CourtRepository;
import com.lab4.demo.item.ItemRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    private final CourtRepository courtRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookingRepository.deleteAll();
            courtRepository.deleteAll();
            itemRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("iulia@email.com")
                    .name("Iulia")
                    .username("iulia")
                    .password("iulia")
                    //.roles(Set.of("ADMIN"))
                    .role("ADMIN")
                    .build());

            authService.register(SignupRequest.builder()
                    .email("iulia2@email.com")
                    .name("Iulia2")
                    .username("iulia2")
                    .password("iulia2")
                    .role("ADMIN")
                    .build());

            authService.register(SignupRequest.builder()
                    .email("iulia.m.farcas@gmail.com")
                    .name("Iulia1")
                    .username("iulia1")
                    .password("iulia1")
                    //.roles(Set.of("PLAYER"))
                    .role("PLAYER")
                    .build());

            Court court = Court.builder()
                    .court(ECourt.T1)
                    .build();
            courtRepository.save(court);
            court = Court.builder()
                    .court(ECourt.T2)
                    .build();
            courtRepository.save(court);
            court = Court.builder()
                    .court(ECourt.T3)
                    .build();
            courtRepository.save(court);

            User player = userRepository.findByUsername("iulia1").orElseThrow();

            //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            Booking booking = Booking.builder()
                    .court(court)
                    .player(player)
                    .startDate(LocalDateTime.parse("2021-06-02 11:00", format))
                    .endDate(LocalDateTime.parse("2021-06-02 12:00", format))
                    .details("IDK")
                    .build();

            bookingRepository.save(booking);

            Booking booking1 = Booking.builder()
                    .court(court)
                    .player(player)
                    .startDate(LocalDateTime.parse("2021-06-02 20:00", format))
                    .endDate(LocalDateTime.parse("2021-06-02 21:00", format))
                    .details("IDK")
                    .build();

            bookingRepository.save(booking1);
        }
    }
}
