package com.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EE308FZ联系人 - 通讯录管理系统主应用类
 * 
 * 项目名称：EE308FZ联系人
 * 团队成员：832301329黎研、832301114孙煦航
 * 
 * 功能特性：
 * 1. 联系人管理（增删改查）
 * 2. 收藏联系人功能
 * 3. 多种联系方式支持（电话、邮箱、微信、QQ等）
 * 4. Excel导入导出功能
 * 5. Excel模板下载
 * 
 * 技术栈：
 * - Spring Boot 2.7.0
 * - Spring Data JPA
 * - Thymeleaf模板引擎
 * - H2数据库
 * - Apache POI（Excel处理）
 * - Bootstrap 5（前端UI）
 * 
 * @author 832301329黎研, 832301114孙煦航
 * @version 1.0
 */
@SpringBootApplication
public class ContactsApplication {
    
    /**
     * 应用程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     EE308FZ联系人 - 通讯录管理系统         ║");
        System.out.println("║                                            ║");
        System.out.println("║  团队成员：                                ║");
        System.out.println("║    - 832301329 黎研                        ║");
        System.out.println("║    - 832301114 孙煦航                      ║");
        System.out.println("║                                            ║");
        System.out.println("║  访问地址: http://localhost:8080           ║");
        System.out.println("║  H2控制台: http://localhost:8080/h2-console║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
