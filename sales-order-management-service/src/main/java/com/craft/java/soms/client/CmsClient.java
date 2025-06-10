package com.craft.java.soms.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class CmsClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cms.service.url}")
    private String cmsServiceUrl; // e.g., http://localhost:9090/customers/get/{customerId}

    public void verifyCustomer(String customerId) {
        String url = cmsServiceUrl + "/" + customerId;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Customer not found: " + customerId);
            }

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Customer not found: " + customerId);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying customer: " + e.getMessage());
        }
    }
}

