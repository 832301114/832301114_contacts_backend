package com.contacts.service;

import com.contacts.entity.Contact;
import com.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
    
    public Contact createContact(Contact contact) {
        // 检查电话号码是否已存在
        if (contactRepository.existsByPhone(contact.getPhone())) {
            throw new RuntimeException("电话号码已存在: " + contact.getPhone());
        }
        return contactRepository.save(contact);
    }
    
    public Contact updateContact(Long id, Contact contactDetails) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            
            // 检查电话号码是否被其他联系人使用
            if (contactRepository.existsByPhoneAndIdNot(contactDetails.getPhone(), id)) {
                throw new RuntimeException("电话号码已被其他联系人使用: " + contactDetails.getPhone());
            }
            
            contact.setName(contactDetails.getName());
            contact.setPhone(contactDetails.getPhone());
            contact.setEmail(contactDetails.getEmail());
            contact.setAddress(contactDetails.getAddress());
            contact.setCompany(contactDetails.getCompany());
            
            return contactRepository.save(contact);
        } else {
            throw new RuntimeException("联系人不存在，ID: " + id);
        }
    }
    
    public void deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new RuntimeException("联系人不存在，ID: " + id);
        }
    }
    
    public List<Contact> searchContacts(String keyword) {
        return contactRepository.searchContacts(keyword);
    }
    
    public List<Contact> getContactsByName(String name) {
        return contactRepository.findByNameContainingIgnoreCase(name);
    }
}
