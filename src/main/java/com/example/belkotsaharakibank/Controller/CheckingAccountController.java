package com.example.belkotsaharakibank.Controller;

import com.example.belkotsaharakibank.Entity.CheckingAccount;
import com.example.belkotsaharakibank.PdfGeneratorUtil;
import com.example.belkotsaharakibank.Service.CheckingAccountService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/checking-account")
public class CheckingAccountController {

    @Autowired
    CheckingAccountService checkingAccountService;


    @PostMapping
    public ResponseEntity<String> saveActivities(@RequestBody CheckingAccount checkingAccountActivities) {
        checkingAccountService.saveActivities(checkingAccountActivities);
        return ResponseEntity.ok("Checking account activities saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<CheckingAccount>> getAllActivities() {
        List<CheckingAccount> activities = checkingAccountService.getAllActivities();

        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadActivitiesReport() {
        List<CheckingAccount> activities = checkingAccountService.getAllActivities();

        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        byte[] contents;
        try {
            contents = PdfGeneratorUtil.generateCheckingAccountReport(activities);
        } catch (DocumentException e) {
            // Logging the error can help in debugging later
            e.printStackTrace(); // Consider using a logger like SLF4J in real-world applications
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while generating the PDF.".getBytes());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "activities_report.pdf";
        headers.setContentDispositionFormData("attachment", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}