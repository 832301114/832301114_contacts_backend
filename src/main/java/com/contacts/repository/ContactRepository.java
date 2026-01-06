package com.contacts.repository;

import com.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    // 查找收藏的联系人
    List<Contact> findByFavoriteTrue();
    
    // 按收藏状态排序，收藏的在前面
    List<Contact> findAllByOrderByFavoriteDescNameAsc();
    
    // 按名字搜索
    List<Contact> findByNameContainingIgnoreCase(String name);
    
    // 综合搜索
    @Query("SELECT DISTINCT c FROM Contact c LEFT JOIN c.contactMethods m " +
           "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.company) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(m.value) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Contact> searchContacts(@Param("keyword") String keyword);
}
