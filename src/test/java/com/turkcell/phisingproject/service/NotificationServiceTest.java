package com.turkcell.phisingproject.service;

import com.turkcell.phisingproject.data.AccessCheckResult;
import com.turkcell.phisingproject.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

class NotificationServiceTest {
    @Mock
    private KafkaTemplate<String, AccessCheckResult> kafkaTemplate;
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(notificationService, "topic", "test-topic");
    }

    @Test
    void testNotifyAccessibleUrl() {
        AccessCheckResult result = AccessCheckResult.builder()
                .url("http://test.com")
                .accessible(true)
                .httpStatusCode(200)
                .build();
        notificationService.notifyAccessibleUrl(result);
        verify(kafkaTemplate).send("test-topic", "http://test.com", result);
        verify(notificationRepository).saveNotification(result);
    }
}

