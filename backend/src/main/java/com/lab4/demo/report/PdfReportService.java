package com.lab4.demo.report;

import com.lab4.demo.court.model.dto.BookingDTO;
import com.lab4.demo.court.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService {

   private final BookingService bookingService;

   //o fost String inainte
    @Override
    public ByteArrayOutputStream export(String date) {

        List<BookingDTO> bookings = bookingService.findAllPerDay(date);
        //String filename = "";
        ByteArrayOutputStream file = new ByteArrayOutputStream();

        try{
            PDDocument pdf = new PDDocument();
            PDPage page = new PDPage();
            pdf.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdf, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            contentStream.showText("Bookings: " + date);
            contentStream.newLine();

            for(BookingDTO bookingDTO : bookings)
            {
                String bookingDetails = "Court: " + bookingDTO.getCourt() + ", Player: " + bookingDTO.getPlayer_name()  + ", "
                        + bookingDTO.getPlayer_username() + ", Date and hour: " + bookingDTO.getStartDate() + " - "
                        + bookingDTO.getEndDate() + ", Details: " + bookingDTO.getDetails();
                contentStream.showText(bookingDetails);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            //filename = "Bookings " + date + ".pdf";
            //pdf.save("Bookings " + date + ".pdf");
            pdf.save(file);
            pdf.close();
            //System.out.println(filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
