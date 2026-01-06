package com.contacts.repository;

import com.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联系人数据访问层接口
 * 提供联系人的CRUD操作和自定义查询方法
 * 
 * @author Team
 * @version 1.0
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    /**
     * 查找所有已收藏的联系人
     * @return 收藏的联系人列表
     */
    List<Contact> findByFavoriteTrue();
    
    /**
     * 获取所有联系人，按收藏状态和姓名排序
     * 收藏的联系人排在前面，同状态按姓名升序排列
     * @return 排序后的联系人列表
     */
    List<Contact> findAllByOrderByFavoriteDescNameAsc();
    
    /**
     * 按姓名模糊搜索联系人（忽略大小写）
     * @param name 搜索关键词
     * @return 匹配的联系人列表
     */
    List<Contact> findByNameContainingIgnoreCase(String name);
    
    /**
     * 综合搜索联系人
     * 在姓名、公司、联系方式值中搜索关键词
     * @param keyword 搜索关键词
     * @return 匹配的联系人列表
     */
    @Query("SELECT DISTINCT c FROM Contact c LEFT JOIN c.contactMethods m " +
           "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.company) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(m.methodValue) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Contact> searchContacts(@Param("keyword") String keyword);
}
