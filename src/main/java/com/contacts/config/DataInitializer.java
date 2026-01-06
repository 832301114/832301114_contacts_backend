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
 * 项目：EE308FZ联系人
 * 团队成员：832301329黎研、832301114孙煦航
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
        // 团队成员1 - 黎研（收藏）
        Contact liyan = new Contact("黎研");
        liyan.setCompany("福州市");
        liyan.setFavorite(true);
        liyan.setNotes("EE308FZ团队成员，学号：832301329");
        liyan.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138001", "手机"));
        liyan.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "832301329@fzu.edu.cn", "学校邮箱"));
        liyan.addContactMethod(new ContactMethod(ContactMethodType.QQ, "2442093051", null));
        liyan.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "liyan_fzu", null));
        liyan.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "福建省福州市闽侯县福州大学", "学校"));
        contactRepository.save(liyan);
        
        // 团队成员2 - 孙煦航（收藏）
        Contact sunxuhang = new Contact("孙煦航");
        sunxuhang.setCompany("福州市");
        sunxuhang.setFavorite(true);
        sunxuhang.setNotes("EE308FZ团队成员，学号：832301114");
        sunxuhang.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13800138002", "手机"));
        sunxuhang.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "832301114@fzu.edu.cn", "学校邮箱"));
        sunxuhang.addContactMethod(new ContactMethod(ContactMethodType.QQ, "2755325937", null));
        sunxuhang.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "sunxuhang_fzu", null));
        sunxuhang.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "福建省福州市闽侯县福州大学", "学校"));
        contactRepository.save(sunxuhang);
        
        // 联系人3 - 张老师（收藏）
        Contact zhanglaoshi = new Contact("张老师");
        zhanglaoshi.setCompany("福州市");
        zhanglaoshi.setFavorite(true);
        zhanglaoshi.setNotes("软件工程课程指导老师");
        zhanglaoshi.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "0591-22866001", "办公室"));
        zhanglaoshi.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhanglaoshi@fzu.edu.cn", "工作"));
        zhanglaoshi.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "福建省福州市闽侯县福州大学计算机楼", "办公室"));
        contactRepository.save(zhanglaoshi);
        
        // 联系人4 - 王同学
        Contact wangtongxue = new Contact("王同学");
        wangtongxue.setCompany("福州市");
        wangtongxue.setFavorite(false);
        wangtongxue.setNotes("同班同学");
        wangtongxue.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13900139001", null));
        wangtongxue.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "wangtongxue@fzu.edu.cn", null));
        wangtongxue.addContactMethod(new ContactMethod(ContactMethodType.QQ, "123456789", null));
        contactRepository.save(wangtongxue);
        
        // 联系人5 - 李经理
        Contact lijingli = new Contact("李经理");
        lijingli.setCompany("福州软件园科技公司");
        lijingli.setFavorite(false);
        lijingli.setNotes("实习面试联系人");
        lijingli.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13600136001", "工作"));
        lijingli.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "0591-88888888", "公司"));
        lijingli.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "lijingli@fztech.com", null));
        lijingli.addContactMethod(new ContactMethod(ContactMethodType.ADDRESS, "福建省福州市鼓楼区软件园A区", "公司"));
        contactRepository.save(lijingli);
        
        // 联系人6 - 陈师兄
        Contact chenshixiong = new Contact("陈师兄");
        chenshixiong.setCompany("阿里巴巴");
        chenshixiong.setFavorite(false);
        chenshixiong.setNotes("学长，已毕业在杭州工作");
        chenshixiong.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13700137001", null));
        chenshixiong.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "chenshixiong@alibaba.com", null));
        chenshixiong.addContactMethod(new ContactMethod(ContactMethodType.WECHAT, "chen_alibaba", null));
        contactRepository.save(chenshixiong);
        
        // 联系人7 - 刘同学
        Contact liutongxue = new Contact("刘同学");
        liutongxue.setCompany("福州大学");
        liutongxue.setFavorite(false);
        liutongxue.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13500135001", null));
        liutongxue.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "liutongxue@fzu.edu.cn", null));
        liutongxue.addContactMethod(new ContactMethod(ContactMethodType.QQ, "987654321", null));
        contactRepository.save(liutongxue);
        
        // 联系人8 - 赵助教
        Contact zhaozhujiao = new Contact("赵助教");
        zhaozhujiao.setCompany("福州市");
        zhaozhujiao.setFavorite(false);
        zhaozhujiao.setNotes("课程助教");
        zhaozhujiao.addContactMethod(new ContactMethod(ContactMethodType.PHONE, "13400134001", null));
        zhaozhujiao.addContactMethod(new ContactMethod(ContactMethodType.EMAIL, "zhaozhujiao@fzu.edu.cn", null));
        contactRepository.save(zhaozhujiao);
        
        System.out.println("========================================");
        System.out.println("  EE308FZ联系人 - 示例数据初始化完成");
        System.out.println("  团队成员：832301329黎研、832301114孙煦航");
        System.out.println("  共创建 8 个联系人");
        System.out.println("========================================");
    }
}
