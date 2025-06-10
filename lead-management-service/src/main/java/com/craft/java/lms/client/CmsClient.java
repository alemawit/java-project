package com.craft.java.lms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
public class CmsClient {

    private static final Logger logger = LoggerFactory.getLogger(CmsClient.class);

    private final RestTemplate restTemplate;

    @Value("${cms.service.url}")
    private String cmsUrl;

    @Autowired
    public CmsClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public boolean createCustomer(Map<String, Object> customerData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(customerData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(cmsUrl, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.error("Error calling CMS service", e);
            return false;
        }
    }
}
