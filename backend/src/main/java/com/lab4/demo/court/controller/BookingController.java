package com.lab4.demo.court.controller;

import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.court.service.BookingService;
import com.lab4.demo.mail.EmailService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.user.model.User;
import com.lab4.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BOOKINGS)
public class BookingController {
    private final BookingService bookingService;
    private final ReportServiceFactory reportServiceFactory;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @GetMapping
    public List<BookingDTO> findAll(){
        return bookingService.findAll();
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO){
        BookingDTO booking = bookingService.save(bookingDTO);
        if(booking == null){
            return booking;
        }else
        {
            //String emailDetails = booking.toString();
            String emailDetails = booking.prettyString();
            User player = userRepository.findByUsername(booking.getPlayer_username()).orElseThrow();
            emailService.sendMail(player.getEmail(),"Booking Confirmation", emailDetails);
            return booking;
        }
        //return bookingService.save(bookingDTO);
    }

    @PatchMapping(ENTITY2)
    public BookingDTO editbooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO){
        return bookingService.update(id, bookingDTO);
    }

    @DeleteMapping(ENTITY2)
    public void deleteBooking(@PathVariable Long id){
        bookingService.deleteById(id);
    }

    @GetMapping(ENTITY)
    public List<BookingDTO> findAllPerDay(@PathVariable String date){
        return bookingService.findAllPerDay(date);
    }

    /*@GetMapping(EXPORT_REPORT)
    public @ResponseBody byte[] export(@PathVariable String date) throws IOException {

       String report = reportServiceFactory.getReportService(ReportType.PDF).export(date);
       return Files.readAllBytes(Paths.get(report));
        // return reportServiceFactory.getReportService(ReportType.PDF).export(date);
    }*/

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> export(@PathVariable String date) throws IOException {

        //String report = reportServiceFactory.getReportService(ReportType.PDF).export(date);
        ByteArrayOutputStream report = reportServiceFactory.getReportService(ReportType.PDF).export(date);
        //Path path = Paths.get(report);
        //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        ByteArrayResource resource = new ByteArrayResource(report.toByteArray());
        System.out.println(report.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Bookings.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);

        //return Files.readAllBytes(Paths.get(report));
        // return reportServiceFactory.getReportService(ReportType.PDF).export(date);
    }

}
