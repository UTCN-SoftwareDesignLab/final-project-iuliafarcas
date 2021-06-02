package com.lab4.demo.court.mapper;

import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mappings({
            @Mapping(target = "player", ignore = true),
            @Mapping(target = "court", ignore = true),
            @Mapping(target = "startDate", expression = "java(stringToDate(bookingDTO.getStartDate()))"),
            @Mapping(target = "endDate", expression = "java(stringToDate(bookingDTO.getEndDate()))")
    })
    Booking bookingFromDTO(BookingDTO bookingDTO);

    @Mappings({
            //@Mapping(source = "booking.player", target = "player"),
            //@Mapping(source = "booking.court", target = "court"),
            @Mapping(target = "player_name", expression = "java(playerToString(booking.getPlayer()))"),
            @Mapping(target = "player_username", expression = "java(playerUsernameToString(booking.getPlayer()))"),
            @Mapping(target = "court", expression = "java(courtToString(booking.getCourt()))"),
            @Mapping(target = "startDate", expression = "java(dateToString(booking.getStartDate()))"),
            @Mapping(target = "endDate", expression = "java(dateToString(booking.getEndDate()))")
    })
    BookingDTO bookingToDTO(Booking booking);

    default String dateToString(LocalDateTime date){
        //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(format);
    }

    default LocalDateTime stringToDate(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(date, format);
    }

    default String playerToString(User player){
        return player.getName();
    }

    default String playerUsernameToString(User player){
        return player.getUsername();
    }

    default String courtToString(Court court){
        return court.getCourt().toString();
    }
}
