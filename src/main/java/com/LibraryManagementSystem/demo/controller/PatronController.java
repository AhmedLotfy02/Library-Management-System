package com.LibraryManagementSystem.demo.controller;

import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable(value = "id") Long patronId) {
        Patron patron = patronService.getPatronById(patronId);
        return ResponseEntity.ok().body(patron);
    }

    @PostMapping
    public Patron addPatron(@Valid @RequestBody Patron patron) {
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable(value = "id") Long patronId, @Valid @RequestBody Patron patronDetails) {
        Patron updatedPatron = patronService.updatePatron(patronId, patronDetails);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable(value = "id") Long patronId) {
        patronService.deletePatron(patronId);
        return ResponseEntity.noContent().build();
    }
}