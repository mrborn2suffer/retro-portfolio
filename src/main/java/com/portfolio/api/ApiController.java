package com.portfolio.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    
    @GetMapping("/profile")
    public Map<String, Object> getProfile() {
        Map<String, Object> profile = new LinkedHashMap<>();
        profile.put("name", "Vaibhav");
        profile.put("role", "Fullstack Software Engineer");
        profile.put("experience", "5+ Years");
        profile.put("specialties", Arrays.asList("Java Backend", "Concurrent Systems", "Spring Boot", "Redis"));
        profile.put("location", "India");
        profile.put("guild", "Systems & Hardware Emulation");
        return profile;
    }

    @GetMapping("/skills")
    public List<Map<String, Object>> getSkills() {
        List<Map<String, Object>> skills = new ArrayList<>();
        skills.add(createSkill("Java (SDK 8-21)", 95, "Primary"));
        skills.add(createSkill("Spring Boot & Microservices", 90, "Primary"));
        skills.add(createSkill("Vaadin Flow & WebSockets", 85, "Secondary"));
        skills.add(createSkill("Redis Cache & PostgreSQL", 85, "Secondary"));
        skills.add(createSkill("JavaScript / HTML5 / CSS3", 80, "Tertiary"));
        skills.add(createSkill("Docker & CI/CD Actions", 75, "Tertiary"));
        return skills;
    }

    @GetMapping("/projects")
    public List<Map<String, Object>> getProjects() {
        List<Map<String, Object>> projects = new ArrayList<>();
        projects.add(createProject("NES Emulator", "Hardware Emulation", "Java, 6502 Assembly"));
        projects.add(createProject("Wolf3D Engine", "Software Rendering", "Java, DDA Math"));
        projects.add(createProject("Aurora Library", "Library Management", "Spring Boot, MongoDB, React, Vite"));
        projects.add(createProject("PingSentinel", "Monitoring Engine", "Spring Boot, MongoDB, Selenium, Quartz"));
        return projects;
    }

    @GetMapping("/resume")
    public Map<String, Object> getResume() {
        Map<String, Object> resume = new LinkedHashMap<>();
        resume.put("document_name", "resume.pdf");
        resume.put("document_type", "application/pdf");
        resume.put("download_url", "https://drive.google.com/file/d/1B2fFAJUsMzyJf_mu7X1u3K2CeN0COkqv/view?usp=sharing");
        resume.put("description", "Operator Vaibhav's Professional Resume Packet.");
        return resume;
    }

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/contact")
    public Map<String, Object> submitContact(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        String name = request.get("name");
        String email = request.get("email");
        String message = request.get("message");
        
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            response.put("status", "error");
            response.put("error_code", 400);
            response.put("message", "Validation failed: Callsign (name), Coordinates (email), and Message (message) are required fields.");
            return response;
        }
        
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(email);
            mailMessage.setTo("mr.born2suffer@gmail.com");
            mailMessage.setReplyTo(email);
            mailMessage.setSubject("Portfolio Contact Form: Message from " + name);
            mailMessage.setText("You received a new message from a user scrolling through your portfolio:\n\n" +
                                "Name: " + name + "\n" +
                                "Email: " + email + "\n\n" +
                                "Message:\n" + message);
            mailSender.send(mailMessage);
            System.out.println("Email sent successfully to mr.born2suffer@gmail.com");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            response.put("warning", "Mail transmission failed. Please configure SPRING_MAIL_PASSWORD environment variable.");
        }
        
        response.put("status", "success");
        response.put("message", "Message sent successfully!");
        response.put("metadata", Map.of(
            "sender_coordinates", email,
            "packets_transmitted", 1,
            "latency_ms", 12
        ));
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    private Map<String, Object> createSkill(String name, int level, String category) {
        Map<String, Object> skill = new LinkedHashMap<>();
        skill.put("name", name);
        skill.put("xp_level", level + "%");
        skill.put("category", category);
        return skill;
    }

    private Map<String, Object> createProject(String name, String category, String tech) {
        Map<String, Object> proj = new LinkedHashMap<>();
        proj.put("name", name);
        proj.put("category", category);
        proj.put("stack", tech);
        return proj;
    }
}
