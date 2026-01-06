package com.contacts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人实体类
 * 用于存储通讯录中的联系人基本信息
 * 
 * @author Team
 * @version 1.0
 */
@Entity
@Table(name = "contacts")
public class Contact {
    
    /**
     * 联系人唯一标识ID，自动生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 联系人姓名，必填字段
     */
    @NotBlank(message = "姓名不能为空")
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 是否收藏标记
     * true: 已收藏，false: 未收藏
     */
    @Column(name = "is_favorite")
    private Boolean favorite = false;
    
    /**
     * 联系人所属公司
     */
    @Column(name = "company", length = 200)
    private String company;
    
    /**
     * 备注信息
     */
    @Column(name = "notes", length = 500)
    private String notes;
    
    /**
     * 创建时间，记录联系人添加时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    /**
     * 更新时间，记录最后修改时间
     */
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    /**
     * 联系方式列表
     * 一个联系人可以有多种联系方式（电话、邮箱、微信等）
     * 使用级联操作，删除联系人时自动删除其所有联系方式
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ContactMethod> contactMethods = new ArrayList<>();
    
    /**
     * 默认构造函数
     * 初始化创建时间和更新时间为当前时间
     */
    public Contact() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        this.favorite = false;
    }
    
    /**
     * 带姓名的构造函数
     * @param name 联系人姓名
     */
    public Contact(String name) {
        this();
        this.name = name;
    }
    
    // ==================== Getter 和 Setter 方法 ====================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name; 
        this.updatedTime = LocalDateTime.now();
    }
    
    public Boolean getFavorite() { return favorite; }
    public void setFavorite(Boolean favorite) { 
        this.favorite = favorite; 
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { 
        this.company = company; 
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { 
        this.notes = notes; 
        this.updatedTime = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
    
    public List<ContactMethod> getContactMethods() { return contactMethods; }
    public void setContactMethods(List<ContactMethod> contactMethods) { this.contactMethods = contactMethods; }
    
    // ==================== 辅助方法 ====================
    
    /**
     * 添加联系方式
     * @param method 要添加的联系方式
     */
    public void addContactMethod(ContactMethod method) {
        contactMethods.add(method);
        method.setContact(this);
    }
    
    /**
     * 移除联系方式
     * @param method 要移除的联系方式
     */
    public void removeContactMethod(ContactMethod method) {
        contactMethods.remove(method);
        method.setContact(null);
    }
    
    /**
     * 获取主要电话号码
     * @return 第一个电话号码，如果没有则返回空字符串
     */
    public String getPrimaryPhone() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.PHONE)
            .findFirst()
            .map(ContactMethod::getMethodValue)
            .orElse("");
    }
    
    /**
     * 获取主要邮箱地址
     * @return 第一个邮箱地址，如果没有则返回空字符串
     */
    public String getPrimaryEmail() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.EMAIL)
            .findFirst()
            .map(ContactMethod::getMethodValue)
            .orElse("");
    }
    
    /**
     * 获取地址
     * @return 第一个地址，如果没有则返回空字符串
     */
    public String getAddress() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.ADDRESS)
            .findFirst()
            .map(ContactMethod::getMethodValue)
            .orElse("");
    }
    
    /**
     * 更新前自动设置更新时间
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", favorite=" + favorite +
                ", company='" + company + '\'' +
                '}';
    }
}
