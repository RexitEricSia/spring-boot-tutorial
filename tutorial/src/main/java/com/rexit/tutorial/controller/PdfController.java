package com.rexit.tutorial.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.service.PdfService;

@RestController
@RequestMapping("/generate-pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/campaign/{id}")
    public ResponseEntity<byte[]> generateCampaignPdf(@PathVariable("id") Long campaignId) throws Exception {
        byte[] catalogue = pdfService.generateCampaignCatalogue(campaignId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename=campaign.pdf");

        return new ResponseEntity<>(catalogue, headers, HttpStatus.OK);
    }
}
