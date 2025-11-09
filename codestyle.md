# 代码规范

*本文档基于Google Java Style Guide和Spring Framework代码规范*

## 源文件规范

### 文件编码
- 使用UTF-8编码
- 使用Unix换行符(LF)

### 文件结构
- 许可证或版权信息（如有）
- package语句
- import语句
- 类声明

### import语句顺序
1. 所有静态import
2. 第三方库import
3. Java标准库import
4. 项目内部import

## 命名规范

### 包名
- 全部小写
- 使用公司域名倒序，如：`com.contacts`

### 类名
- 使用大驼峰命名法，如：`ContactController`
- 接口名使用形容词或名词，如：`Runnable`

### 方法名
- 使用小驼峰命名法，如：`getContactById`
- 方法名应为动词或动词短语

### 变量名
- 使用小驼峰命名法，如：`contactService`
- 避免使用单个字符（除循环变量外）

### 常量名
- 全部大写，用下划线分隔，如：`MAX_SIZE`

## 格式规范

### 缩进
- 使用4个空格，禁止使用Tab

### 行长度
- 每行不超过120个字符

### 大括号
- 使用K&R风格
```java
if (condition) {
    // ...
} else {
    // ...
}
