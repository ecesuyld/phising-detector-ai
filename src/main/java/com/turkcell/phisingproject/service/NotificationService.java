package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final KafkaTemplate<String, AccessCheckResult> kafkaTemplate;
    private final NotificationRepository notificationRepository;

    @Value("${access-check.kafka.topic}")
    private String topic;

    public void notifyAccessibleUrl(AccessCheckResult result) {
        log.info("Publishing alert for accessible phishing URL: {}", result.getUrl());
        kafkaTemplate.send(topic, result.getUrl(), result);
        notificationRepository.saveNotification(result);
    }
}

