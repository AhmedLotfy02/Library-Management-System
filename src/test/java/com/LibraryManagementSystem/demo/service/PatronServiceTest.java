package com.LibraryManagementSystem.demo.service;

import com.LibraryManagementSystem.demo.entity.Patron;
import com.LibraryManagementSystem.demo.exception.ResourceNotFoundException;
import com.LibraryManagementSystem.demo.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @Test
    public void shouldReturnAllPatrons() {
        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("John Doe");
        patron1.setContactInformation("john.doe@example.com");

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Jane Smith");
        patron2.setContactInformation("jane.smith@example.com");

        List<Patron> patrons = Arrays.asList(patron1, patron2);

        when(patronRepository.findAll()).thenReturn(patrons);

        List<Patron> result = patronService.getAllPatrons();

        assertEquals(2, result.size());
        assertEquals(patron1.getName(), result.get(0).getName());
        assertEquals(patron2.getName(), result.get(1).getName());
    }

    @Test
    public void shouldReturnPatronById() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        when(patronRepository.findById(patron.getId())).thenReturn(Optional.of(patron));

        Patron result = patronService.getPatronById(patron.getId());

        assertNotNull(result);
        assertEquals(patron.getName(), result.getName());
    }

    @Test
    public void shouldThrowExceptionWhenPatronNotFoundById() {
        Long patronId = 1L;

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(patronId));
    }

    @Test
    public void shouldAddPatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        Patron result = patronService.addPatron(patron);

        assertNotNull(result);
        assertEquals(patron.getName(), result.getName());
    }

    @Test
    public void shouldUpdatePatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        Patron updatedPatronDetails = new Patron();
        updatedPatronDetails.setName("Updated Name");
        updatedPatronDetails.setContactInformation("updated@example.com");

        when(patronRepository.findById(patron.getId())).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(updatedPatronDetails);

        Patron result = patronService.updatePatron(patron.getId(), updatedPatronDetails);

        assertNotNull(result);
        assertEquals(updatedPatronDetails.getName(), result.getName());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingNonExistingPatron() {
        Long patronId = 1L;
        Patron updatedPatronDetails = new Patron();
        updatedPatronDetails.setName("Updated Name");
        updatedPatronDetails.setContactInformation("updated@example.com");

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.updatePatron(patronId, updatedPatronDetails));
    }

    @Test
    public void shouldDeletePatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setContactInformation("john.doe@example.com");

        when(patronRepository.findById(patron.getId())).thenReturn(Optional.of(patron));
        doNothing().when(patronRepository).delete(patron);

        patronService.deletePatron(patron.getId());

        verify(patronRepository, times(1)).delete(patron);
    }

    @Test
    public void shouldThrowExceptionWhenDeletingNonExistingPatron() {
        Long patronId = 1L;

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(patronId));
    }
}
