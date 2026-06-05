package com.turkcell.phisingproject.controller;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/history")
    public ResponseEntity<List<AccessCheckResult>> getNotificationHistory() {
        return ResponseEntity.ok(notificationRepository.findAll());
    }
}

