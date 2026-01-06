package com.contacts.entity;

/**
 * 联系方式类型枚举
 * 定义了系统支持的所有联系方式类型
 * 
 * @author Team
 * @version 1.0
 */
public enum ContactMethodType {
    
    /** 电话号码 */
    PHONE("电话"),
    
    /** 电子邮箱 */
    EMAIL("邮箱"),
    
    /** 物理地址 */
    ADDRESS("地址"),
    
    /** 微信号 */
    WECHAT("微信"),
    
    /** QQ号码 */
    QQ("QQ"),
    
    /** 微博账号 */
    WEIBO("微博"),
    
    /** Twitter账号 */
    TWITTER("Twitter"),
    
    /** Facebook账号 */
    FACEBOOK("Facebook"),
    
    /** LinkedIn账号 */
    LINKEDIN("LinkedIn"),
    
    /** 其他联系方式 */
    OTHER("其他");
    
    /**
     * 联系方式类型的中文显示名称
     */
    private final String displayName;
    
    /**
     * 构造函数
     * @param displayName 显示名称
     */
    ContactMethodType(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * 获取显示名称
     * @return 中文显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
}
