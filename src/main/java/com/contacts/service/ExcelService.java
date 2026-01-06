package com.contacts.service;

import com.contacts.entity.Contact;
import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import com.contacts.repository.ContactRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel导入导出服务
 * 提供联系人数据的Excel导入和导出功能
 * 
 * @author Team
 * @version 1.0
 */
@Service
public class ExcelService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    /**
     * 导出所有联系人到Excel文件
     * 
     * Excel格式：
     * 列A: 姓名
     * 列B: 公司
     * 列C: 是否收藏
     * 列D: 电话
     * 列E: 邮箱
     * 列F: 地址
     * 列G: 微信
     * 列H: QQ
     * 列I: 备注
     * 
     * @return Excel文件的字节数组
     * @throws IOException 如果导出过程中发生IO错误
     */
    public byte[] exportToExcel() throws IOException {
        List<Contact> contacts = contactRepository.findAll();
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("通讯录");
            
            // 创建标题样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            
            // 创建数据单元格样式
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setWrapText(true);
            
            // 创建表头
            Row headerRow = sheet.createRow(0);
            String[] headers = {"姓名", "公司", "是否收藏", "电话", "邮箱", "地址", "微信", "QQ", "备注"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // 填充数据
            int rowNum = 1;
            for (Contact contact : contacts) {
                Row row = sheet.createRow(rowNum++);
                
                createCell(row, 0, contact.getName(), dataStyle);
                createCell(row, 1, contact.getCompany() != null ? contact.getCompany() : "", dataStyle);
                createCell(row, 2, contact.getFavorite() != null && contact.getFavorite() ? "是" : "否", dataStyle);
                createCell(row, 3, getMethodValues(contact, ContactMethodType.PHONE), dataStyle);
                createCell(row, 4, getMethodValues(contact, ContactMethodType.EMAIL), dataStyle);
                createCell(row, 5, getMethodValues(contact, ContactMethodType.ADDRESS), dataStyle);
                createCell(row, 6, getMethodValues(contact, ContactMethodType.WECHAT), dataStyle);
                createCell(row, 7, getMethodValues(contact, ContactMethodType.QQ), dataStyle);
                createCell(row, 8, contact.getNotes() != null ? contact.getNotes() : "", dataStyle);
            }
            
            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                // 设置最小宽度
                if (sheet.getColumnWidth(i) < 3000) {
                    sheet.setColumnWidth(i, 3000);
                }
                // 设置最大宽度
                if (sheet.getColumnWidth(i) > 15000) {
                    sheet.setColumnWidth(i, 15000);
                }
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    /**
     * 创建单元格并设置值和样式
     */
    private void createCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    
    /**
     * 获取联系人某类型的所有联系方式值
     * 多个值用分号分隔
     * 
     * @param contact 联系人对象
     * @param type 联系方式类型
     * @return 联系方式值字符串，多个用分号分隔
     */
    private String getMethodValues(Contact contact, ContactMethodType type) {
        StringBuilder sb = new StringBuilder();
        for (ContactMethod method : contact.getContactMethods()) {
            if (method.getType() == type) {
                if (sb.length() > 0) sb.append("; ");
                sb.append(method.getValue());
            }
        }
        return sb.toString();
    }
    
    /**
     * 从Excel文件导入联系人
     * 
     * @param file 上传的Excel文件
     * @return 导入的联系人列表
     * @throws IOException 如果导入过程中发生IO错误
     */
    public List<Contact> importFromExcel(MultipartFile file) throws IOException {
        List<Contact> importedContacts = new ArrayList<>();
        
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            
            // 跳过表头
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                
                String name = getCellStringValue(row.getCell(0));
                if (name == null || name.trim().isEmpty()) {
                    continue; // 跳过没有姓名的行
                }
                
                Contact contact = new Contact(name.trim());
                contact.setCompany(getCellStringValue(row.getCell(1)));
                
                String favoriteStr = getCellStringValue(row.getCell(2));
                contact.setFavorite("是".equals(favoriteStr) || "true".equalsIgnoreCase(favoriteStr) || "1".equals(favoriteStr));
                
                // 导入各类联系方式
                addMethodsFromCell(contact, row.getCell(3), ContactMethodType.PHONE);
                addMethodsFromCell(contact, row.getCell(4), ContactMethodType.EMAIL);
                addMethodsFromCell(contact, row.getCell(5), ContactMethodType.ADDRESS);
                addMethodsFromCell(contact, row.getCell(6), ContactMethodType.WECHAT);
                addMethodsFromCell(contact, row.getCell(7), ContactMethodType.QQ);
                
                contact.setNotes(getCellStringValue(row.getCell(8)));
                
                Contact savedContact = contactRepository.save(contact);
                importedContacts.add(savedContact);
            }
        }
        
        return importedContacts;
    }
    
    /**
     * 获取单元格的字符串值
     * 支持字符串、数字、布尔类型的单元格
     * 
     * @param cell 单元格对象
     * @return 单元格的字符串值，如果为空则返回null
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // 处理数字类型，避免科学计数法
                double numValue = cell.getNumericCellValue();
                if (numValue == Math.floor(numValue)) {
                    return String.valueOf((long) numValue);
                }
                return String.valueOf(numValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return null;
        }
    }
    
    /**
     * 从单元格值中解析并添加联系方式
     * 支持分号分隔的多个值
     * 
     * @param contact 联系人对象
     * @param cell 单元格对象
     * @param type 联系方式类型
     */
    private void addMethodsFromCell(Contact contact, Cell cell, ContactMethodType type) {
        String value = getCellStringValue(cell);
        if (value != null && !value.trim().isEmpty()) {
            // 支持分号分隔的多个值（中英文分号都支持）
            String[] values = value.split("[;；]");
            for (String v : values) {
                if (!v.trim().isEmpty()) {
                    contact.addContactMethod(new ContactMethod(type, v.trim()));
                }
            }
        }
    }
}
