# EE308FZ联系人 - 通讯录管理系统

基于 Spring Boot + JPA + Thymeleaf 的通讯录管理系统。

## 团队信息

| 项目 | 信息 |
|------|------|
| 课程名称 | EE308FZ |
| 团队名称 | EE308FZ联系人 |
| 成员1 | 832301329 黎研 |
| 成员2 | 832301114 孙煦航 |
| 仓库地址 | https://github.com/832301114/832301114_contacts_backend |

## 功能特性

### 1. 收藏联系人 ⭐ (25分)
- 支持将联系人标记为收藏/取消收藏
- 收藏的联系人在列表中优先显示
- 可以筛选只显示收藏的联系人
- 一键切换收藏状态

### 2. 多种联系方式 📱 (10分)
支持为每个联系人添加多种联系方式：
- 电话（支持多个）
- 邮箱（支持多个）
- 地址
- 微信
- QQ
- 微博
- Twitter
- Facebook
- LinkedIn
- 其他

### 3. Excel导入导出 📊 (25分)
- **导出**：将所有联系人导出为Excel文件，格式规范
- **导入**：从Excel文件批量导入联系人
- **模板下载**：提供标准导入模板，方便用户填写

### 4. 完整的增删改查功能
- **新增**：创建新联系人，支持添加多种联系方式
- **查看**：查看联系人详细信息
- **编辑**：修改联系人信息和联系方式
- **删除**：删除联系人及其所有联系方式
- **搜索**：按姓名、公司、联系方式搜索

### 5. 现代化UI设计 🎨 (10分)
- 响应式设计，支持移动端
- 渐变色主题
- 卡片式布局
- 流畅的动画效果

## 技术栈

- **后端框架**：Spring Boot 2.7.0
- **数据访问**：Spring Data JPA
- **模板引擎**：Thymeleaf
- **数据库**：H2 Database（文件存储）
- **Excel处理**：Apache POI 5.2.3
- **前端框架**：Bootstrap 5
- **图标库**：Bootstrap Icons

## 快速开始

### 环境要求
- JDK 11+
- Maven 3.6+

### 运行项目
```bash
# 克隆项目
git clone https://github.com/832301114/832301114_contacts_backend.git

# 进入项目目录
cd 832301114_contacts_backend

# 运行项目
mvn spring-boot:run
```

### 访问地址
- 应用首页: http://localhost:8080
- H2控制台: http://localhost:8080/h2-console

## 项目结构

```
src/main/java/com/contacts/
├── ContactsApplication.java       # 启动类
├── config/
│   └── DataInitializer.java       # 数据初始化器
├── controller/
│   └── ContactController.java     # 控制器（增删改查）
├── entity/
│   ├── Contact.java               # 联系人实体
│   ├── ContactMethod.java         # 联系方式实体
│   └── ContactMethodType.java     # 联系方式类型枚举
├── repository/
│   ├── ContactRepository.java     # 联系人数据访问
│   └── ContactMethodRepository.java
└── service/
    ├── ContactService.java        # 联系人业务逻辑
    └── ExcelService.java          # Excel导入导出服务

src/main/resources/
├── templates/
│   ├── index.html                 # 首页（联系人列表）
│   ├── contact-form.html          # 新增/编辑表单
│   ├── contact-detail.html        # 联系人详情
│   ├── import.html                # 导入页面
│   └── error.html                 # 错误页面
└── application.properties         # 配置文件
```

## API接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | / | 首页，联系人列表 |
| GET | /contact/new | 新建联系人页面 |
| GET | /contact/edit/{id} | 编辑联系人页面 |
| GET | /contact/{id} | 查看联系人详情 |
| POST | /contact/save | 保存联系人 |
| POST | /contact/delete/{id} | 删除联系人 |
| POST | /contact/favorite/{id} | 切换收藏状态 |
| GET | /export | 导出Excel |
| GET | /import | 导入页面 |
| POST | /import | 处理导入 |
| GET | /template | 下载导入模板 |

## 代码规范

详见 [codestyle.md](codestyle.md)

## 许可证

MIT License
