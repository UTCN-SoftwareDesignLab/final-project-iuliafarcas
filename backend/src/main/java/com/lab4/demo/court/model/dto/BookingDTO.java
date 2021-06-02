package com.lab4.demo.court.model.dto;

import com.lab4.demo.court.model.Court;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long id;
    //private Long playerId;
    //private Long courtId;
    private String player_name;
    private String player_username;
    private String court;
    //private Date startDate;
    //private Date endDate;
    private String startDate;
    private String endDate;
    private String details;

    public String prettyString(){
        String prettyString = "Booking Confirmation: \n" +
                "Name: " + getPlayer_name() + " \n"+
                "Username: " + getPlayer_username() + "\n"+
                "Start Date: " + getStartDate() + " \n"+
                "End Date: " + getEndDate() + " \n"+
                "Court: " + getCourt() + " \n"+
                "Detals: " + getDetails();
        return  prettyString;
    }
}
