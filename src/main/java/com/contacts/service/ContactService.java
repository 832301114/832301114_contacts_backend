package com.contacts.service;

import com.contacts.entity.Contact;
import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import com.contacts.repository.ContactRepository;
import com.contacts.repository.ContactMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private ContactMethodRepository contactMethodRepository;
    
    // 获取所有联系人（收藏的排在前面）
    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByFavoriteDescNameAsc();
    }
    
    // 获取收藏的联系人
    public List<Contact> getFavoriteContacts() {
        return contactRepository.findByFavoriteTrue();
    }
    
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
    
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }
    
    public Contact updateContact(Long id, Contact contactDetails) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + id));
        
        contact.setName(contactDetails.getName());
        contact.setCompany(contactDetails.getCompany());
        contact.setNotes(contactDetails.getNotes());
        contact.setFavorite(contactDetails.getFavorite());
        
        return contactRepository.save(contact);
    }
    
    // 切换收藏状态
    public Contact toggleFavorite(Long id) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + id));
        
        contact.setFavorite(!contact.getFavorite());
        return contactRepository.save(contact);
    }
    
    public void deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new RuntimeException("联系人不存在，ID: " + id);
        }
    }
    
    public List<Contact> searchContacts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllContacts();
        }
        return contactRepository.searchContacts(keyword.trim());
    }
    
    // 添加联系方式
    public ContactMethod addContactMethod(Long contactId, ContactMethodType type, String value, String label) {
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + contactId));
        
        ContactMethod method = new ContactMethod(type, value, label);
        contact.addContactMethod(method);
        contactRepository.save(contact);
        
        return method;
    }
    
    // 删除联系方式
    public void removeContactMethod(Long methodId) {
        contactMethodRepository.deleteById(methodId);
    }
    
    // 保存联系人及其所有联系方式
    public Contact saveContactWithMethods(Contact contact, List<ContactMethod> methods) {
        // 清除旧的联系方式
        contact.getContactMethods().clear();
        
        // 添加新的联系方式
        for (ContactMethod method : methods) {
            if (method.getValue() != null && !method.getValue().trim().isEmpty()) {
                contact.addContactMethod(method);
            }
        }
        
        return contactRepository.save(contact);
    }
}
