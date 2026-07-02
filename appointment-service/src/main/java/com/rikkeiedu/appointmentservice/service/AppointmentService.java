package com.rikkeiedu.appointmentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class AppointmentService {

    @Autowired
    private RestTemplate restTemplate;

    @Retry(name = "patientRetry")
    public boolean checkPatientExists(Long patientId) {
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(
                "http://patient-service/api/v1/patients/" + patientId, 
                Object.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
