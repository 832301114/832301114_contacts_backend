# 通讯录管理系统

基于 Spring Boot + JPA + Thymeleaf 的通讯录管理系统。

## 功能特性

### 1. 收藏联系人 ⭐
- 支持将联系人标记为收藏
- 收藏的联系人在列表中优先显示
- 可以筛选只显示收藏的联系人

### 2. 多种联系方式 📱
支持为每个联系人添加多种联系方式：
- 电话
- 邮箱
- 地址
- 微信
- QQ
- 微博
- Twitter
- Facebook
- LinkedIn
- 其他

### 3. Excel导入导出 📊
- **导出**：将所有联系人导出为Excel文件
- **导入**：从Excel文件批量导入联系人

## 技术栈

- Spring Boot 2.7.0
- Spring Data JPA
- Thymeleaf
- H2 Database
- Apache POI (Excel处理)
- Bootstrap 5

## 快速开始

### 运行项目
```bash
mvn spring-boot:run
```

### 访问地址
- 应用首页: http://localhost:8080
- H2控制台: http://localhost:8080/h2-console

## 项目结构

```
src/main/java/com/contacts/
├── ContactsApplication.java    # 启动类
├── controller/
│   └── ContactController.java  # 控制器
├── entity/
│   ├── Contact.java           # 联系人实体
│   ├── ContactMethod.java     # 联系方式实体
│   └── ContactMethodType.java # 联系方式类型枚举
├── repository/
│   ├── ContactRepository.java
│   └── ContactMethodRepository.java
└── service/
    ├── ContactService.java    # 联系人服务
    └── ExcelService.java      # Excel导入导出服务

src/main/resources/
├── templates/
│   ├── index.html            # 首页
│   ├── contact-form.html     # 联系人表单
│   ├── contact-detail.html   # 联系人详情
│   ├── import.html           # 导入页面
│   └── error.html            # 错误页面
└── application.properties    # 配置文件
```

## 团队成员

- 成员1: 2442093051@qq.com
- 成员2: 2755325937@qq.com
