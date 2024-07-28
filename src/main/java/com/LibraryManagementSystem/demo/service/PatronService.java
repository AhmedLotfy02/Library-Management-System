package com.LibraryManagementSystem.demo.service;

import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.exception.ResourceNotFoundException;
import com.LibraryManagementSystem.demo.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;
    @Cacheable(value = "patrons")

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
    @Cacheable(value = "patrons", key = "#id")

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron not found with id " + id));
    }
    @CachePut(value = "patrons", key = "#patron.id")

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }
    @CachePut(value = "patrons", key = "#id")

    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id " + id));

        patron.setName(patronDetails.getName());
        patron.setContactInformation(patronDetails.getContactInformation());

        return patronRepository.save(patron);
    }
    @CacheEvict(value = "patrons", key = "#id")

    public void deletePatron(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id " + id));

        patronRepository.delete(patron);
    }
}