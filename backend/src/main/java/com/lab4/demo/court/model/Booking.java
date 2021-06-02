package com.lab4.demo.court.model;

import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player", nullable = false)
    private User player;

    @ManyToOne
    @JoinColumn(name = "court", nullable = false)
    private Court court;

    @Column(nullable = false)
    //private Date startDate;
    private LocalDateTime startDate;

    @Column(nullable = false)
    //private Date endDate;
    private LocalDateTime endDate;

    @Column(nullable = true)
    private String details;
}
