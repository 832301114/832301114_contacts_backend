package com.contacts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "姓名不能为空")
    @Column(nullable = false)
    private String name;
    
    // 收藏功能
    @Column(name = "is_favorite")
    private Boolean favorite = false;
    
    private String company;
    
    private String notes;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    // 多种联系方式 - 一对多关系
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ContactMethod> contactMethods = new ArrayList<>();
    
    public Contact() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
        this.favorite = false;
    }
    
    public Contact(String name) {
        this();
        this.name = name;
    }
    
    // Getter和Setter
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
    
    // 辅助方法
    public void addContactMethod(ContactMethod method) {
        contactMethods.add(method);
        method.setContact(this);
    }
    
    public void removeContactMethod(ContactMethod method) {
        contactMethods.remove(method);
        method.setContact(null);
    }
    
    // 获取主要电话
    public String getPrimaryPhone() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.PHONE)
            .findFirst()
            .map(ContactMethod::getValue)
            .orElse("");
    }
    
    // 获取主要邮箱
    public String getPrimaryEmail() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.EMAIL)
            .findFirst()
            .map(ContactMethod::getValue)
            .orElse("");
    }
    
    // 获取地址
    public String getAddress() {
        return contactMethods.stream()
            .filter(m -> m.getType() == ContactMethodType.ADDRESS)
            .findFirst()
            .map(ContactMethod::getValue)
            .orElse("");
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
