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
@Table(name = "contact_methods", indexes = {
    @Index(name = "idx_method_contact", columnList = "contact_id"),
    @Index(name = "idx_method_type", columnList = "type")
})
public class ContactMethod {
    
    /**
     * 联系方式唯一标识ID，自动生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT COMMENT '联系方式ID'")
    private Long id;
    
    /**
     * 联系方式类型
     * 如：PHONE(电话)、EMAIL(邮箱)、WECHAT(微信)等
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '联系方式类型'")
    private ContactMethodType type;
    
    /**
     * 联系方式的值
     * 如：具体的电话号码、邮箱地址等
     */
    @NotBlank(message = "联系方式值不能为空")
    @Column(name = "value", nullable = false, length = 200, columnDefinition = "VARCHAR(200) COMMENT '联系方式值'")
    private String value;
    
    /**
     * 联系方式标签
     * 用于区分同类型的不同联系方式，如：工作、家庭、个人等
     */
    @Column(name = "label", length = 50, columnDefinition = "VARCHAR(50) COMMENT '标签(如工作、家庭)'")
    private String label;
    
    /**
     * 所属联系人
     * 多对一关系，多个联系方式属于一个联系人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", columnDefinition = "BIGINT COMMENT '所属联系人ID'")
    private Contact contact;
    
    /**
     * 默认构造函数
     */
    public ContactMethod() {}
    
    /**
     * 带类型和值的构造函数
     * @param type 联系方式类型
     * @param value 联系方式值
     */
    public ContactMethod(ContactMethodType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    /**
     * 完整构造函数
     * @param type 联系方式类型
     * @param value 联系方式值
     * @param label 标签
     */
    public ContactMethod(ContactMethodType type, String value, String label) {
        this.type = type;
        this.value = value;
        this.label = label;
    }
    
    // ==================== Getter 和 Setter 方法 ====================
    
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
                ", value='" + value + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
