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

/**
 * 联系人业务逻辑层
 * 提供联系人的增删改查和收藏等业务功能
 * 
 * @author Team
 * @version 1.0
 */
@Service
@Transactional
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private ContactMethodRepository contactMethodRepository;
    
    /**
     * 获取所有联系人
     * 收藏的联系人排在前面，按姓名升序排列
     * @return 联系人列表
     */
    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByFavoriteDescNameAsc();
    }
    
    /**
     * 获取所有已收藏的联系人
     * @return 收藏的联系人列表
     */
    public List<Contact> getFavoriteContacts() {
        return contactRepository.findByFavoriteTrue();
    }
    
    /**
     * 根据ID获取联系人
     * @param id 联系人ID
     * @return 联系人Optional对象
     */
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
    
    /**
     * 创建新联系人
     * @param contact 联系人对象
     * @return 保存后的联系人对象
     */
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }
    
    /**
     * 更新联系人信息
     * @param id 联系人ID
     * @param contactDetails 更新的联系人信息
     * @return 更新后的联系人对象
     * @throws RuntimeException 如果联系人不存在
     */
    public Contact updateContact(Long id, Contact contactDetails) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + id));
        
        contact.setName(contactDetails.getName());
        contact.setCompany(contactDetails.getCompany());
        contact.setNotes(contactDetails.getNotes());
        contact.setFavorite(contactDetails.getFavorite());
        
        return contactRepository.save(contact);
    }
    
    /**
     * 切换联系人的收藏状态
     * @param id 联系人ID
     * @return 更新后的联系人对象
     * @throws RuntimeException 如果联系人不存在
     */
    public Contact toggleFavorite(Long id) {
        Contact contact = contactRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + id));
        
        contact.setFavorite(!contact.getFavorite());
        return contactRepository.save(contact);
    }
    
    /**
     * 删除联系人
     * 同时会删除该联系人的所有联系方式
     * @param id 联系人ID
     * @throws RuntimeException 如果联系人不存在
     */
    public void deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new RuntimeException("联系人不存在，ID: " + id);
        }
    }
    
    /**
     * 搜索联系人
     * 在姓名、公司、联系方式中搜索关键词
     * @param keyword 搜索关键词
     * @return 匹配的联系人列表
     */
    public List<Contact> searchContacts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllContacts();
        }
        return contactRepository.searchContacts(keyword.trim());
    }
    
    /**
     * 为联系人添加联系方式
     * @param contactId 联系人ID
     * @param type 联系方式类型
     * @param value 联系方式值
     * @param label 标签
     * @return 添加的联系方式对象
     * @throws RuntimeException 如果联系人不存在
     */
    public ContactMethod addContactMethod(Long contactId, ContactMethodType type, String value, String label) {
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new RuntimeException("联系人不存在，ID: " + contactId));
        
        ContactMethod method = new ContactMethod(type, value, label);
        contact.addContactMethod(method);
        contactRepository.save(contact);
        
        return method;
    }
    
    /**
     * 删除联系方式
     * @param methodId 联系方式ID
     */
    public void removeContactMethod(Long methodId) {
        contactMethodRepository.deleteById(methodId);
    }
    
    /**
     * 保存联系人及其所有联系方式
     * 会先清除旧的联系方式，再添加新的
     * @param contact 联系人对象
     * @param methods 联系方式列表
     * @return 保存后的联系人对象
     */
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
