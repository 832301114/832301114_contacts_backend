package com.contacts.repository;

import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {
    
    List<ContactMethod> findByContactId(Long contactId);
    
    List<ContactMethod> findByType(ContactMethodType type);
    
    void deleteByContactId(Long contactId);
}
