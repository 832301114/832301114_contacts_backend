package com.contacts.repository;

import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联系方式数据访问层接口
 * 提供联系方式的CRUD操作和自定义查询方法
 * 
 * @author Team
 * @version 1.0
 */
@Repository
public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {
    
    /**
     * 根据联系人ID查找所有联系方式
     * @param contactId 联系人ID
     * @return 该联系人的所有联系方式列表
     */
    List<ContactMethod> findByContactId(Long contactId);
    
    /**
     * 根据联系方式类型查找
     * @param type 联系方式类型
     * @return 该类型的所有联系方式列表
     */
    List<ContactMethod> findByType(ContactMethodType type);
    
    /**
     * 删除指定联系人的所有联系方式
     * @param contactId 联系人ID
     */
    void deleteByContactId(Long contactId);
}
