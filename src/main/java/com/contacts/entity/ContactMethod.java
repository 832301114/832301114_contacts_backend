package com.contacts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 联系方式实体类
 * 用于存储联系人的各种联系方式（电话、邮箱、社交媒体等）
 * 与Contact实体是多对一关系
 * 
 * @author Team
 * @version 1.0
 */
@Entity
@Table(name = "contact_methods")
public class ContactMethod {
    
    /**
     * 联系方式唯一标识ID，自动生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 联系方式类型
     * 如：PHONE(电话)、EMAIL(邮箱)、WECHAT(微信)等
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ContactMethodType type;
    
    /**
     * 联系方式的值
     * 如：具体的电话号码、邮箱地址等
     */
    @NotBlank(message = "联系方式值不能为空")
    @Column(name = "method_value", nullable = false, length = 200)
    private String methodValue;
    
    /**
     * 联系方式标签
     * 用于区分同类型的不同联系方式，如：工作、家庭、个人等
     */
    @Column(name = "label", length = 50)
    private String label;
    
    /**
     * 所属联系人
     * 多对一关系，多个联系方式属于一个联系人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;
    
    /**
     * 默认构造函数
     */
    public ContactMethod() {}
    
    /**
     * 带类型和值的构造函数
     * @param type 联系方式类型
     * @param methodValue 联系方式值
     */
    public ContactMethod(ContactMethodType type, String methodValue) {
        this.type = type;
        this.methodValue = methodValue;
    }
    
    /**
     * 完整构造函数
     * @param type 联系方式类型
     * @param methodValue 联系方式值
     * @param label 标签
     */
    public ContactMethod(ContactMethodType type, String methodValue, String label) {
        this.type = type;
        this.methodValue = methodValue;
        this.label = label;
    }
    
    // ==================== Getter 和 Setter 方法 ====================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ContactMethodType getType() { return type; }
    public void setType(ContactMethodType type) { this.type = type; }
    
    public String getMethodValue() { return methodValue; }
    public void setMethodValue(String methodValue) { this.methodValue = methodValue; }
    
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
    
    /**
     * 获取联系方式类型的显示名称
     * @return 类型的中文显示名称
     */
    public String getTypeDisplayName() {
        return type != null ? type.getDisplayName() : "";
    }
    
    @Override
    public String toString() {
        return "ContactMethod{" +
                "id=" + id +
                ", type=" + type +
                ", methodValue='" + methodValue + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
