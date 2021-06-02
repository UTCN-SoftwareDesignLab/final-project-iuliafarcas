package com.lab4.demo;

import com.lab4.demo.court.model.Booking;
import com.lab4.demo.court.model.Court;
import com.lab4.demo.court.model.ECourt;
import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.review.model.dto.ReviewDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Item.class)) {
            supplier = TestCreationFactory::newItem;
        } else if (cls.equals(ItemDTO.class)) {
            supplier = TestCreationFactory::newItemDTO;
        } else if (cls.equals(ReviewDTO.class)) {
            supplier = TestCreationFactory::newReviewDTO;
        } else if (cls.equals(Booking.class)) {
            supplier = TestCreationFactory::newBooking;
        } else  if (cls.equals(BookingDTO.class)) {
            supplier = TestCreationFactory::newBookingDTO;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newPlayer;
        } else if (cls.equals(Court.class)) {
            supplier = TestCreationFactory::newCourt;
        } else
        {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static Item newItem() {
        return Item.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ItemDTO newItemDTO() {
        return ItemDTO.builder()
                .id(randomLong())
                .name(randomString())
                .description(randomString())
                .build();
    }

    private static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .id(randomLong())
                .text(randomString())
                .reviewer(randomString())
                .build();
    }

    public static Court newCourt(){
        return Court.builder()
                .court(ECourt.T1)
                .build();
    }

    public static User newPlayer(){
        Set<Role> roles = new HashSet<>();
        Role role = Role.builder()
                .name(ERole.PLAYER)
                .build();
        roles.add(role);

        return User.builder()
                .id(randomLong())
                .name("Iulia")
                .username("iulia")
                .email("iulia@x.com")
                .password(randomString())
                //.roles(roles)
                .role(role)
                .build();
    }

    public static Booking newBooking(){
        Court court = newCourt();
        //User player = newPlayer();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return Booking.builder()
                .court(court)
                //.player(player)
                .startDate(LocalDateTime.parse("11-11-1111 11:11", format))
                .endDate(LocalDateTime.parse("11-11-1111 12:11", format))
                .details(randomString())
                .build();
    }

    public static BookingDTO newBookingDTO(){
        Court court = newCourt();
        User player = newPlayer();

        return BookingDTO.builder()
                .court(court.getCourt().toString())
                .player_name(player.getName())
                .player_username(player.getUsername())
                .startDate("11-11-1111 11:11")
                .endDate("11-11-1111 12:11")
                .details(randomString())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
