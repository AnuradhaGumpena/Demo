package com.example.employee.service;

import com.example.employee.beans.Organisation;
import com.example.employee.repository.OrganisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisationService {
    private final OrganisationRepository repository;

    public OrganisationService (OrganisationRepository repository){
        this.repository=repository;
    }

    public String addOrganisation(Organisation organisation){
//        repository.save(organisation);
        if (organisation.getOrgId() == null) {
            // Insert case
            repository.insertOrUpdateOrganisation(organisation);
            return "Organisation inserted successfully";
        } else {
            // Update case
            repository.insertOrUpdateOrganisation(organisation);
            return "Organisation updated successfully";
        }
    }
    public Organisation getOrganisationById(Long orgId) {
        return repository.getOrganisationById(orgId);
    }

    public List<Organisation> getAllOrganisations() {
        return repository.getAllOrganisations();
    }

    public boolean softDeleteOrganisation(Long orgId) {
        int rowsAffected = repository.softDeleteOrganisation(orgId);
        return rowsAffected > 0;
    }
}
