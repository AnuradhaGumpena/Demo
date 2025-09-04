package com.example.employee.controller;

import com.example.employee.beans.Organisation;
import com.example.employee.service.OrganisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {
    private final OrganisationService service;

    public OrganisationController(OrganisationService service) {
        this.service = service;
    }

    @PostMapping
    public String createOrganisation(@RequestBody Organisation organisation) {
        return service.addOrganisation(organisation);
    }

    @GetMapping("/{id}")
    public Organisation getOrganisationById(@PathVariable Long id) {
        return service.getOrganisationById(id);
    }

    @GetMapping
    public List<Organisation> getAllOrganisations() {
        return service.getAllOrganisations();
    }

    @PostMapping("/{orgId}")
    public ResponseEntity<String> softDelete(@PathVariable Long orgId) {
        boolean deleted = service.softDeleteOrganisation(orgId);
        if (deleted) {
            return ResponseEntity.ok("Organisation soft deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Organisation not found");
        }
    }
}
