package com.contacts.entity;

public enum ContactMethodType {
    PHONE("电话"),
    EMAIL("邮箱"),
    ADDRESS("地址"),
    WECHAT("微信"),
    QQ("QQ"),
    WEIBO("微博"),
    TWITTER("Twitter"),
    FACEBOOK("Facebook"),
    LINKEDIN("LinkedIn"),
    OTHER("其他");
    
    private final String displayName;
    
    ContactMethodType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
