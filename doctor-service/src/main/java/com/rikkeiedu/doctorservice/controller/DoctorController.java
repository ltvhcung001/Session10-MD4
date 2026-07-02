package com.rikkeiedu.doctorservice.controller;

import com.rikkeiedu.doctorservice.dto.DoctorDTO;
import com.rikkeiedu.doctorservice.entity.Doctor;
import com.rikkeiedu.doctorservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorRepository.findAllProjected();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorRepository.save(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> new ResponseEntity<>(doctor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name = "searchDoctorLimit", fallbackMethod = "searchDoctorFallback")
    public ResponseEntity<?> searchDoctors(@RequestParam String name) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    public ResponseEntity<?> searchDoctorFallback(String name, Exception ex) {
        java.util.Map<String, Object> errorResponse = new java.util.HashMap<>();
        errorResponse.put("data", "Bạn đã gửi quá 5 request mỗi 10 giây");
        errorResponse.put("message", "Error");
        errorResponse.put("status", 429);
        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }
}
