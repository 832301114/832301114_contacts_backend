package com.contacts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "contact_methods")
public class ContactMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactMethodType type;
    
    @NotBlank(message = "联系方式值不能为空")
    @Column(nullable = false)
    private String value;
    
    private String label; // 如：家庭、工作、个人等
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;
    
    public ContactMethod() {}
    
    public ContactMethod(ContactMethodType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public ContactMethod(ContactMethodType type, String value, String label) {
        this.type = type;
        this.value = value;
        this.label = label;
    }
    
    // Getter和Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ContactMethodType getType() { return type; }
    public void setType(ContactMethodType type) { this.type = type; }
    
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
    
    public String getTypeDisplayName() {
        return type != null ? type.getDisplayName() : "";
    }
}
