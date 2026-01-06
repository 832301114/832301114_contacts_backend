package com.contacts.config;

import com.contacts.entity.Contact;
import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import com.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 * 在应用启动时自动加载示例数据
 * 
 * @author Team
 * @version 1.0
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 如果数据库中已有数据，则不再初始化
        if (contactRepository.count() > 0) {
            return;
        }
        
        // 创建示例联系人数据
        createSampleContacts();
    }
    
    /**
     * 创建示例联系人数据
     */
    private void createSampleContacts() {
        // 联系人1 - 张三（收藏）
        Contact zhangsan = new Contact("张三");
        zhangsan.setCompany("阿里巴巴集团");
        zhangsan.setFavorite(true);
        zhangsan.setNotes("大学同学，目前在杭州工作");
        zhangsan.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138001", "工作"));
        zhangsan.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13900139001", "个人"));
        zhangsan.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhangsan@alibaba.com", "工作"));
        zhangsan.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "zhangsan_wx", null));
        zhangsan.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "浙江省杭州市西湖区文三路123号", "公司"));
        contactRepository.save(zhangsan);
        
        // 联系人2 - 李四（收藏）
        Contact lisi = new Contact("李四");
        lisi.setCompany("腾讯科技");
        lisi.setFavorite(true);
        lisi.setNotes("高中同学，经常联系");
        lisi.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138002", "手机"));
        lisi.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "lisi@tencent.com", "工作"));
        lisi.addContactMethod(new ContactMethod(ContactMethodType.QQ, "123456789", null));
        lisi.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "lisi2024", null));
        contactRepository.save(lisi);
        
        // 联系人3 - 王五
        Contact wangwu = new Contact("王五");
        wangwu.setCompany("华为技术有限公司");
        wangwu.setFavorite(false);
        wangwu.setNotes("技术交流群认识的朋友");
        wangwu.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138003", null));
        wangwu.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "wangwu@huawei.com", null));
        wangwu.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "广东省深圳市龙岗区坂田街道", null));
        contactRepository.save(wangwu);
        
        // 联系人4 - 赵六
        Contact zhaoliu = new Contact("赵六");
        zhaoliu.setCompany("字节跳动");
        zhaoliu.setFavorite(false);
        zhaoliu.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138004", null));
        zhaoliu.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhaoliu@bytedance.com", null));
        zhaoliu.addContactMethod(new ContactMethod(ContactMethodType.WEIBO, "@赵六official", null));
        contactRepository.save(zhaoliu);
        
        // 联系人5 - 孙七（收藏）
        Contact sunqi = new Contact("孙七");
        sunqi.setCompany("百度在线");
        sunqi.setFavorite(true);
        sunqi.setNotes("项目合作伙伴");
        sunqi.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138005", "工作"));
        sunqi.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "010-12345678", "座机"));
        sunqi.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "sunqi@baidu.com", null));
        sunqi.addContactMethod(new ContactMethod(ContactMethodType.LINKEDIN, "sunqi-baidu", null));
        contactRepository.save(sunqi);
        
        // 联系人6 - 周八
        Contact zhouba = new Contact("周八");
        zhouba.setCompany("小米科技");
        zhouba.setFavorite(false);
        zhouba.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138006", null));
        zhouba.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhouba@xiaomi.com", null));
        zhouba.addContactMethod(new ContactMethod(ContactMethodType.QQ, "987654321", null));
        contactRepository.save(zhouba);
        
        // 联系人7 - 吴九
        Contact wujiu = new Contact("吴九");
        wujiu.setCompany("京东集团");
        wujiu.setFavorite(false);
        wujiu.setNotes("电商行业交流");
        wujiu.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138007", null));
        wujiu.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "wujiu@jd.com", null));
        wujiu.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "北京市亦庄经济开发区", null));
        contactRepository.save(wujiu);
        
        // 联系人8 - 郑十
        Contact zhengshi = new Contact("郑十");
        zhengshi.setCompany("网易公司");
        zhengshi.setFavorite(false);
        zhengshi.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138008", null));
        zhengshi.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhengshi@netease.com", null));
        zhengshi.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "zhengshi_163", null));
        contactRepository.save(zhengshi);
        
        System.out.println("✅ 示例数据初始化完成，共创建 8 个联系人");
    }
}
