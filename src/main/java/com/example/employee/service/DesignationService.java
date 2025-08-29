package com.example.employee.service;

import com.example.employee.beans.Designation;
import com.example.employee.repository.DesignationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {
    private final DesignationRepository repository;

    public DesignationService(DesignationRepository repository) {
        this.repository = repository;
    }

    public String addDesignation(Designation designation) {
//        repository.save(designation);
        if (designation.getDesgId() == null) {
            // Insert case
            repository.insertOrUpdateDesignation(designation);
            return "Designation inserted successfully";
        } else {
            // Update case
            repository.insertOrUpdateDesignation(designation);
            return "Designation updated successfully";
        }
    }
    public Designation getDesignationById(Long desgId) {
        return repository.getDesignationById(desgId);
    }

    public List<Designation> getAllDesignations() {
        return repository.getAllDesignations();
    }

    public boolean softDeleteDesignation(Long desgId) {
        int rowsAffected = repository.softDeleteDesignation(desgId);
        return rowsAffected > 0;
    }
}
