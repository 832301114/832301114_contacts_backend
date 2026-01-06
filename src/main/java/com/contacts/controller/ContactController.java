package com.contacts.controller;

import com.contacts.entity.Contact;
import com.contacts.entity.ContactMethod;
import com.contacts.entity.ContactMethodType;
import com.contacts.service.ContactService;
import com.contacts.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ExcelService excelService;
    
    // 首页 - 联系人列表
    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String keyword,
                        @RequestParam(required = false) Boolean favoritesOnly) {
        List<Contact> contacts;
        
        if (Boolean.TRUE.equals(favoritesOnly)) {
            contacts = contactService.getFavoriteContacts();
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            contacts = contactService.searchContacts(keyword);
        } else {
            contacts = contactService.getAllContacts();
        }
        
        model.addAttribute("contacts", contacts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("favoritesOnly", favoritesOnly);
        model.addAttribute("contactMethodTypes", ContactMethodType.values());
        return "index";
    }
    
    // 新建联系人页面
    @GetMapping("/contact/new")
    public String newContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("contactMethodTypes", ContactMethodType.values());
        model.addAttribute("isNew", true);
        return "contact-form";
    }
    
    // 编辑联系人页面
    @GetMapping("/contact/edit/{id}")
    public String editContactForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在"));
        
        model.addAttribute("contact", contact);
        model.addAttribute("contactMethodTypes", ContactMethodType.values());
        model.addAttribute("isNew", false);
        return "contact-form";
    }
    
    // 保存联系人
    @PostMapping("/contact/save")
    public String saveContact(@ModelAttribute Contact contact,
                              @RequestParam(value = "methodTypes", required = false) List<String> methodTypes,
                              @RequestParam(value = "methodValues", required = false) List<String> methodValues,
                              @RequestParam(value = "methodLabels", required = false) List<String> methodLabels,
                              RedirectAttributes redirectAttributes) {
        
        List<ContactMethod> methods = new ArrayList<>();
        if (methodTypes != null && methodValues != null) {
            for (int i = 0; i < methodTypes.size(); i++) {
                String value = methodValues.get(i);
                if (value != null && !value.trim().isEmpty()) {
                    ContactMethodType type = ContactMethodType.valueOf(methodTypes.get(i));
                    String label = (methodLabels != null && i < methodLabels.size()) ? methodLabels.get(i) : null;
                    methods.add(new ContactMethod(type, value.trim(), label));
                }
            }
        }
        
        if (contact.getId() == null) {
            // 新建
            Contact savedContact = contactService.createContact(contact);
            contactService.saveContactWithMethods(savedContact, methods);
            redirectAttributes.addFlashAttribute("message", "联系人创建成功！");
        } else {
            // 更新
            Contact existingContact = contactService.getContactById(contact.getId())
                .orElseThrow(() -> new RuntimeException("联系人不存在"));
            existingContact.setName(contact.getName());
            existingContact.setCompany(contact.getCompany());
            existingContact.setNotes(contact.getNotes());
            existingContact.setFavorite(contact.getFavorite());
            contactService.saveContactWithMethods(existingContact, methods);
            redirectAttributes.addFlashAttribute("message", "联系人更新成功！");
        }
        
        return "redirect:/";
    }
    
    // 切换收藏状态
    @PostMapping("/contact/favorite/{id}")
    public String toggleFavorite(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Contact contact = contactService.toggleFavorite(id);
        String message = contact.getFavorite() ? "已添加到收藏" : "已取消收藏";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }
    
    // 删除联系人
    @PostMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactService.deleteContact(id);
        redirectAttributes.addFlashAttribute("message", "联系人已删除");
        return "redirect:/";
    }
    
    // 导出Excel
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        byte[] excelData = excelService.exportToExcel();
        
        String filename = URLEncoder.encode("通讯录.xlsx", StandardCharsets.UTF_8.toString());
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(excelData);
    }
    
    // 导入Excel页面
    @GetMapping("/import")
    public String importPage() {
        return "import";
    }
    
    // 处理导入
    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "请选择要导入的文件");
            return "redirect:/import";
        }
        
        try {
            List<Contact> imported = excelService.importFromExcel(file);
            redirectAttributes.addFlashAttribute("message", "成功导入 " + imported.size() + " 个联系人");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "导入失败: " + e.getMessage());
        }
        
        return "redirect:/";
    }
    
    // 查看联系人详情
    @GetMapping("/contact/{id}")
    public String viewContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id)
            .orElseThrow(() -> new RuntimeException("联系人不存在"));
        
        model.addAttribute("contact", contact);
        return "contact-detail";
    }
}
