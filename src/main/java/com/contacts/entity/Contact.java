package com.contacts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "姓名不能为空")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "电话号码不能为空")
    @Column(nullable = false)
    private String phone;
    
    private String email;
    
    private String address;
    
    private String company;
    
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
    
    // 构造方法
    public Contact() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
    
    public Contact(String name, String phone, String email, String address, String company) {
        this();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
        this.updatedTime = LocalDateTime.now();
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
        this.updatedTime = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
    
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
