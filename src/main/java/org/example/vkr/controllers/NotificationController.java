package org.example.vkr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.vkr.models.Notification;
import org.example.vkr.service.NotificationService;
import org.example.vkr.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final TokenService tokenService;

    @GetMapping
    public List<Notification> getAllNotificationsByUserId(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        return notificationService.getAllNotificationsByUserId(tokenService.getIdByToken(token));
    }
}
