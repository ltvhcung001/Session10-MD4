package com.rikkeiedu.appointmentservice.controller;

import com.rikkeiedu.appointmentservice.entity.Appointment;
import com.rikkeiedu.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private com.rikkeiedu.appointmentservice.service.AppointmentService appointmentService;

    @PostMapping({"/api/v1/appointments", "/api/v1/appointment"})
    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name = "doctorRateLimiter", fallbackMethod = "getDoctorFallback")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "doctorServiceCB", fallbackMethod = "getDoctorFallback")
    @io.github.resilience4j.retry.annotation.Retry(name = "doctorRetry", fallbackMethod = "getDoctorFallback")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        // 1. Check if Patient exists via patient-service
        try {
            boolean patientExists = appointmentService.checkPatientExists(appointment.getPatientId());
            if (!patientExists) {
                return new ResponseEntity<>("Patient with ID " + appointment.getPatientId() + " not found.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to verify patient: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 2. Check if Doctor exists via doctor-service
        ResponseEntity<Object> response = restTemplate.getForEntity(
            "http://doctor-service/api/v1/doctors/" + appointment.getDoctorId(), 
            Object.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>("Doctor with ID " + appointment.getDoctorId() + " not found.", HttpStatus.BAD_REQUEST);
        }

        // 3. Save appointment
        if (appointment.getStatus() == null) {
            appointment.setStatus("PENDING");
        }
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getDoctorFallback(Appointment appointment, Exception ex) {
        // Đảm bảo dữ liệu đơn hàng vẫn được lưu vào PostgreSQL ở trạng thái "PENDING"
        if (appointment.getStatus() == null) {
            appointment.setStatus("PENDING");
        }
        appointmentRepository.save(appointment);

        com.rikkeiedu.appointmentservice.dto.ApiResponseError error = new com.rikkeiedu.appointmentservice.dto.ApiResponseError(
                "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây",
                503,
                "Doctor Service Error"
        );
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
