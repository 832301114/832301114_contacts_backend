package com.contacts.controller;

import com.contacts.entity.Contact;
import com.contacts.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:8080") // 允许前端访问
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contacts = contactService.getAllContacts();
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        
        if (contact.isPresent()) {
            return new ResponseEntity<>(contact.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createContact(@Valid @RequestBody Contact contact) {
        try {
            Contact savedContact = contactService.createContact(contact);
            return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contactDetails) {
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Contact>> searchContacts(@RequestParam String keyword) {
        try {
            List<Contact> contacts = contactService.searchContacts(keyword);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Contact>> getContactsByName(@PathVariable String name) {
        try {
            List<Contact> contacts = contactService.getContactsByName(name);
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
