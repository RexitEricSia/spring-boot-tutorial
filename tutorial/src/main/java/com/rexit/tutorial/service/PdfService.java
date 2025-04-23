package com.rexit.tutorial.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.HtmlConverter;
import com.rexit.tutorial.model.Campaign;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;
    private final CampaignService campaignService;

    public PdfService(TemplateEngine templateEngine, CampaignService campaignService) {
        this.templateEngine = templateEngine;
        this.campaignService = campaignService;
    }

    private String renderTemplate(String templateName, Map<String, Object> model) {
        return templateEngine.process(templateName, new Context(null, model));
    }
    
    public byte[] generateCampaignCatalogue(long campaignId) {
        Campaign campaign = campaignService.getCampaignById(campaignId);

        Map<String, Object> model = new HashMap<>();
        model.put("campaign", campaign);

        String renderedHtml = renderTemplate("campaign-catalogue", model);

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(renderedHtml, pdfStream);

        return pdfStream.toByteArray();
    }
}
