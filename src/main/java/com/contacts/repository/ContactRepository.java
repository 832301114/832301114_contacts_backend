package com.contacts.repository;

import com.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    List<Contact> findByNameContainingIgnoreCase(String name);
    
    List<Contact> findByPhoneContaining(String phone);
    
    @Query("SELECT c FROM Contact c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR c.phone LIKE CONCAT('%', :keyword, '%')")
    List<Contact> searchContacts(@Param("keyword") String keyword);
    
    boolean existsByPhone(String phone);
    
    boolean existsByPhoneAndIdNot(String phone, Long id);
}
