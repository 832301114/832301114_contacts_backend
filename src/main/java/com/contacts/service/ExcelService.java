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

@Service
public class ExcelService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    // 导出联系人到Excel
    public byte[] exportToExcel() throws IOException {
        List<Contact> contacts = contactRepository.findAll();
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("通讯录");
            
            // 创建标题样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            
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
                
                row.createCell(0).setCellValue(contact.getName());
                row.createCell(1).setCellValue(contact.getCompany() != null ? contact.getCompany() : "");
                row.createCell(2).setCellValue(contact.getFavorite() != null && contact.getFavorite() ? "是" : "否");
                
                // 获取各类联系方式
                row.createCell(3).setCellValue(getMethodValues(contact, ContactMethodType.PHONE));
                row.createCell(4).setCellValue(getMethodValues(contact, ContactMethodType.EMAIL));
                row.createCell(5).setCellValue(getMethodValues(contact, ContactMethodType.ADDRESS));
                row.createCell(6).setCellValue(getMethodValues(contact, ContactMethodType.WECHAT));
                row.createCell(7).setCellValue(getMethodValues(contact, ContactMethodType.QQ));
                row.createCell(8).setCellValue(contact.getNotes() != null ? contact.getNotes() : "");
            }
            
            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    // 获取某类型的所有联系方式值（用分号分隔）
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
    
    // 从Excel导入联系人
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
                contact.setFavorite("是".equals(favoriteStr) || "true".equalsIgnoreCase(favoriteStr));
                
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
    
    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }
    
    private void addMethodsFromCell(Contact contact, Cell cell, ContactMethodType type) {
        String value = getCellStringValue(cell);
        if (value != null && !value.trim().isEmpty()) {
            // 支持分号分隔的多个值
            String[] values = value.split("[;；]");
            for (String v : values) {
                if (!v.trim().isEmpty()) {
                    contact.addContactMethod(new ContactMethod(type, v.trim()));
                }
            }
        }
    }
}
