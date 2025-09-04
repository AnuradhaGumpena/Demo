package com.example.employee.controller;
import com.example.employee.beans.Designation;
import com.example.employee.service.DesignationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designation")
public class DesignationController {
    private final DesignationService service;

    public DesignationController(DesignationService service) {
        this.service = service;
    }

    @PostMapping
    public String createDesignation(@RequestBody Designation designation) {
        return service.addDesignation(designation);
    }

    @GetMapping("/{id}")
    public Designation getDesignationById(@PathVariable Long id) {
        return service.getDesignationById(id);
    }

    @GetMapping
    public List<Designation> getAllDesignations() {
        return service.getAllDesignations();
    }

    @PostMapping("/{desgId}")
    public ResponseEntity<String> softDelete(@PathVariable Long desgId) {
        boolean deleted = service.softDeleteDesignation(desgId);
        if (deleted) {
            return ResponseEntity.ok("Designation soft deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Designation not found");
        }
    }
}
